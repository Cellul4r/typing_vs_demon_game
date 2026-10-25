#!/usr/bin/env bash
set -euo pipefail

fail() {
    printf 'Error: %s\n' "$*" >&2
    exit 1
}

usage() {
    printf '%s\n' 'Usage: bash scripts/build-web.sh [--java-home PATH]' \
        'Build the Java 17 game and browser package. Uses JAVA_HOME or tools on PATH.'
}

build_java_home=${JAVA_HOME:-}
while (($#)); do
    case "$1" in
        --java-home)
            (($# >= 2)) || fail '--java-home requires a JDK directory.'
            build_java_home=$2
            shift 2
            ;;
        -h|--help) usage; exit 0 ;;
        *) usage >&2; fail "Unknown argument: $1" ;;
    esac
done

# Accept Windows JAVA_HOME values when running in Git Bash.
if [[ -n "$build_java_home" ]] && command -v cygpath >/dev/null 2>&1; then
    build_java_home=$(cygpath -u "$build_java_home")
fi

find_java_tool() {
    local tool=$1 candidate
    if [[ -n "$build_java_home" ]]; then
        candidate="$build_java_home/bin/$tool"
        if [[ -x "$candidate" ]]; then
            printf '%s\n' "$candidate"
        elif [[ -x "$candidate.exe" ]]; then
            printf '%s\n' "$candidate.exe"
        else
            fail "Cannot find $tool in '$build_java_home'. Select a JDK 17 or newer."
        fi
    else
        command -v "$tool" || fail "Cannot find $tool. Set JAVA_HOME or add a JDK 17 or newer to PATH."
    fi
}

javac_tool=$(find_java_tool javac)
jar_tool=$(find_java_tool jar)
script_directory=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd -P)
repo_root=$(cd "$script_directory/.." && pwd -P)
build_root="$repo_root/build/cheerpj"
classes_directory="$build_root/classes"
site_directory="$build_root/site"

# Arrays and null-delimited paths preserve spaces in checkout and resource names.
sources=()
while IFS= read -r -d '' source_file; do
    sources+=("$source_file")
done < <(find "$repo_root/src" -type f -name '*.java' -print0)
((${#sources[@]})) || fail 'No Java sources found.'

# A JPEG renamed to .png can trigger unsupported native color management in CheerpJ.
while IFS= read -r -d '' image_file; do
    signature=$(od -An -tx1 -N8 "$image_file" | tr -d '[:space:]')
    [[ "$signature" == 89504e470d0a1a0a ]] ||
        fail "Image '$image_file' is not a PNG. Convert its actual format before building."
done < <(find "$repo_root/src/resource" -type f -name '*.png' -print0)

# Verify the absolute cleanup targets and reject linked build paths before removing anything.
for build_path in "$repo_root/build" "$build_root" "$classes_directory" "$site_directory"; do
    [[ ! -L "$build_path" ]] || fail "Refusing to build through a linked directory: $build_path"
done
for clean_directory in "$classes_directory" "$site_directory"; do
    case "$clean_directory" in
        "$repo_root/build/cheerpj/classes"|"$repo_root/build/cheerpj/site") ;;
        *) fail "Refusing to clean a directory outside '$build_root'." ;;
    esac
done
rm -rf -- "$classes_directory" "$site_directory"
mkdir -p "$classes_directory" "$site_directory"

printf '%s\n' 'Compiling the game for Java 17...'
"$javac_tool" --release 17 -encoding UTF-8 -d "$classes_directory" "${sources[@]}"

jar_path="$site_directory/Game_2D.jar"
printf '%s\n' 'Packaging the game and resources...'
"$jar_tool" --create --file "$jar_path" --main-class game_2d.Game_2D \
    -C "$classes_directory" . -C "$repo_root/src" resource

archive_entries=$("$jar_tool" --list --file "$jar_path" | tr -d '\r')
for required in META-INF/MANIFEST.MF game_2d/Game_2D.class \
    resource/ui_res/main_menu_background.png resource/sound_res/main_menu.wav \
    resource/map_res/map01.txt resource/word_res/easy_word.txt \
    resource/word_res/medium_word.txt resource/word_res/hard_word.txt; do
    grep -Fxq "$required" <<< "$archive_entries" ||
        fail "The game JAR is missing a required entry: $required"
done

cp -R "$repo_root/web/." "$site_directory/"
mkdir -p "$site_directory/assets" "$site_directory/resource/sound_res"
for resource in tile_res/soil1.png player_res/player1.png \
    enemy_res/enemy3_1.png ui_res/main_menu_background.png; do
    cp "$repo_root/src/resource/$resource" "$site_directory/assets/"
done
cp "$repo_root/src/resource/sound_res/"*.wav "$site_directory/resource/sound_res/"
touch "$site_directory/.nojekyll"

printf 'Built %s\n' "$jar_path"
printf 'Run the desktop game: java -jar "%s"\n' "$jar_path"
printf 'Browser site: %s (serve over HTTP with byte-range support)\n' "$site_directory"
