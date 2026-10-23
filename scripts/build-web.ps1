[CmdletBinding()]
param(
    [string]$JavaHome = $env:JAVA_HOME
)

Set-StrictMode -Version Latest
$ErrorActionPreference = 'Stop'

$repoRoot = Split-Path -Parent $PSScriptRoot
$buildRoot = [System.IO.Path]::GetFullPath((Join-Path $repoRoot 'build/cheerpj'))
$classesDirectory = Join-Path $buildRoot 'classes'
$siteDirectory = Join-Path $buildRoot 'site'

function Find-JavaTool([string]$Name) {
    if ($JavaHome) {
        $suffix = if ($env:OS -eq 'Windows_NT') { '.exe' } else { '' }
        $tool = Join-Path $JavaHome "bin/$Name$suffix"
        if (-not (Test-Path -LiteralPath $tool -PathType Leaf)) {
            throw "Cannot find $Name in '$JavaHome'. Set JAVA_HOME to a JDK 17 or newer, or use -JavaHome."
        }
        return $tool
    }

    $command = Get-Command $Name -CommandType Application -ErrorAction SilentlyContinue
    if (-not $command) {
        throw "Cannot find $Name. Install a JDK 17 or newer and set JAVA_HOME or add its bin directory to PATH."
    }
    return $command.Source
}

function Invoke-JavaTool([string]$Tool, [string[]]$ToolArguments) {
    & $Tool @ToolArguments
    if ($LASTEXITCODE -ne 0) {
        throw "Java build command failed with exit code ${LASTEXITCODE}: $Tool"
    }
}

$javac = Find-JavaTool 'javac'
$jar = Find-JavaTool 'jar'
$sources = @(Get-ChildItem -LiteralPath (Join-Path $repoRoot 'src') -Recurse -Filter '*.java' -File |
    Sort-Object FullName | ForEach-Object { $_.FullName })
if ($sources.Count -eq 0) {
    throw 'No Java sources found.'
}

# JPEG files renamed to .png can invoke unsupported native color management in CheerpJ.
foreach ($image in Get-ChildItem -LiteralPath (Join-Path $repoRoot 'src/resource') -Recurse -Filter '*.png' -File) {
    $signature = [System.IO.File]::ReadAllBytes($image.FullName)
    if ($signature.Length -lt 8 -or
            [System.BitConverter]::ToString($signature, 0, 8) -ne '89-50-4E-47-0D-0A-1A-0A') {
        throw "Image '$($image.FullName)' is not a PNG. Convert its actual format to PNG before building."
    }
}

# Rebuild only these generated directories. Never reuse stale class files or assets.
foreach ($directory in @($classesDirectory, $siteDirectory)) {
    $absoluteDirectory = [System.IO.Path]::GetFullPath($directory)
    if (-not $absoluteDirectory.StartsWith($buildRoot + [System.IO.Path]::DirectorySeparatorChar,
            [System.StringComparison]::OrdinalIgnoreCase)) {
        throw "Refusing to clean a directory outside '$buildRoot'."
    }
    # Reject links in the generated path before removing anything recursively.
    $ancestor = $absoluteDirectory
    while ($ancestor -and $ancestor -ne $repoRoot) {
        if (Test-Path -LiteralPath $ancestor) {
            $item = Get-Item -LiteralPath $ancestor -Force
            if ($item.Attributes -band [System.IO.FileAttributes]::ReparsePoint) {
                throw "Refusing to build through a linked directory: $ancestor"
            }
        }
        $ancestor = Split-Path -Parent $ancestor
    }
    if (Test-Path -LiteralPath $absoluteDirectory) {
        Remove-Item -LiteralPath $absoluteDirectory -Recurse -Force
    }
    New-Item -ItemType Directory -Path $absoluteDirectory -Force | Out-Null
}

Write-Host 'Compiling the game for Java 17...'
Invoke-JavaTool $javac (@('--release', '17', '-encoding', 'UTF-8', '-d', $classesDirectory) + $sources)

$jarPath = Join-Path $siteDirectory 'Game_2D.jar'
Write-Host 'Packaging the game and resources...'
Invoke-JavaTool $jar @('--create', '--file', $jarPath, '--main-class', 'game_2d.Game_2D',
    '-C', $classesDirectory, '.', '-C', (Join-Path $repoRoot 'src'), 'resource')

Add-Type -AssemblyName System.IO.Compression.FileSystem
$archive = [System.IO.Compression.ZipFile]::OpenRead($jarPath)
try {
    $entries = @($archive.Entries | ForEach-Object { $_.FullName })
    foreach ($required in @('META-INF/MANIFEST.MF', 'game_2d/Game_2D.class',
            'resource/ui_res/main_menu_background.png', 'resource/sound_res/main_menu.wav',
            'resource/map_res/map01.txt', 'resource/word_res/easy_word.txt',
            'resource/word_res/medium_word.txt', 'resource/word_res/hard_word.txt')) {
        if ($entries -cnotcontains $required) {
            throw "The game JAR is missing a required entry: $required"
        }
    }
} finally {
    $archive.Dispose()
}

Copy-Item -Path (Join-Path $repoRoot 'web/*') -Destination $siteDirectory -Recurse -Force
$themeDirectory = Join-Path $siteDirectory 'assets'
New-Item -ItemType Directory -Path $themeDirectory -Force | Out-Null
foreach ($resource in @('tile_res/soil1.png', 'player_res/player1.png',
        'enemy_res/enemy3_1.png', 'ui_res/main_menu_background.png')) {
    Copy-Item -LiteralPath (Join-Path $repoRoot "src/resource/$resource") -Destination $themeDirectory -Force
}
$audioDirectory = Join-Path $siteDirectory 'resource/sound_res'
New-Item -ItemType Directory -Path $audioDirectory -Force | Out-Null
Copy-Item -Path (Join-Path $repoRoot 'src/resource/sound_res/*.wav') -Destination $audioDirectory -Force
New-Item -ItemType File -Path (Join-Path $siteDirectory '.nojekyll') -Force | Out-Null
$size = [math]::Round((Get-Item -LiteralPath $jarPath).Length / 1MB, 2)
Write-Host "Built $jarPath ($size MiB)"
Write-Host "Serve locally: npx http-server `"$siteDirectory`" -p 8080 -c-1"
