![icon game](icon_game.png)
# Typing VS Demon 
**204114 Object Oriented Programming with Java Project**

[Computer Science, Chaing Mai University](https://www.cs.science.cmu.ac.th/), Thailand
## About The Game Project
  * This Project is designed to practice concepts of Object Oritend Programming (OOP)
  * If you want to improve your typing speed and accuracy, this game is for you!

## Screenshot
![main menu](screenshot/main_menu.png)
![tutorial menu](screenshot/tutorial_menu.png)
![difficulty selection menu](screenshot/difficulty_menu.png)
![play screen](screenshot/play_menu.png)
![pause screen](screenshot/pause_menu.png)
![game over screen](screenshot/game_over_menu.png)
## Installation
### Windows
1. download `.exe` file from github directory `/DownloadApp/Windows`.
2. double-click install the `.exe` file in the directory you saved.
3. Once Installed, search the app **Typing VS Demon** and lauch the game.
4. Enjoy!

### Linux

  1. download `.deb` file from github directory `/DownloadApp/Linux`.
  2. double-click install `.deb` file in the directory you saved.
  3. Once Installed, search the app **Typing VS Demon** and lauch the game.
  4. Enjoy!

### Run in Java IDE
  
  * Recommend **Java 17** or higher
  * Require Apache NetBeans IDE
  1. Download the repository file (Project) from the download section or clone this project by typing in the command prompt/terminal :
     
          git clone https://github.com/Cellul4r/typing_vs_demon_game.git
     
  2. Import the project file in Apache NetBeans
  3. compile and run from IDE and Enjoy!

## Play in a browser with CheerpJ Core

The browser version runs the existing Swing game with [CheerpJ Core 4.3](https://cheerpj.com/docs/overview)
and its Java 17 runtime. Players need a modern browser, an internet connection, and a keyboard;
they do not need a Java installation or browser extension. The first launch downloads the game
(approximately 43 MiB compressed) plus runtime resources, so allow time for it to load.

### Build and run locally

Install a JDK 17 or newer, Node.js (with npm), and PowerShell. Windows PowerShell 5.1 works on
Windows; PowerShell 7 (`pwsh`) is used on Linux and in GitHub Actions. Run from the repository root:

```powershell
./scripts/build-web.ps1
node --test test/web-launcher.test.cjs
npx http-server build/cheerpj/site -p 8080 -c-1
```

If your JDK is not on PATH, set `JAVA_HOME` to its installation directory or pass it explicitly:

```powershell
./scripts/build-web.ps1 -JavaHome 'C:/Program Files/Java/jdk-17'
```

Open <http://localhost:8080>, choose **Start game**, and click inside the game for keyboard input
and sound. The Start game button shows a spinner and Loading until the first game frame appears,
then changes to Game running. It stays disabled to prevent duplicate Java sessions.
Use the page's **Fullscreen** button for browser fullscreen. The browser may consume
the first Escape key to exit fullscreen; press it again to pause. After choosing Exit in the
Java game, use **Reload game** to start a new session.

The build creates a fresh compressed `Game_2D.jar`, including images, audio, maps, and word lists,
and copies the launcher into `build/cheerpj/site/`. Build output is ignored by Git.
It checks compilation, packaging, and required resource paths before succeeding.
Audio files are also copied to `resource/sound_res/` beside the JAR for browser playback.
Publish the complete generated site directory, not just the HTML and JAR.
The page reuses the game's pixel art for its theme. The locally bundled Press Start 2P font
is distributed with its SIL Open Font License in `web/assets/fonts/`.

CheerpJ requires an HTTP server with byte-range support. Do not open `index.html` directly from
disk or use Python's standard HTTP server. See the [CheerpJ server requirements](https://cheerpj.com/docs/guides/basic-server-setup).
To verify local hosting on Windows:

```powershell
curl.exe -sS -D - -o NUL -H 'Range: bytes=0-99' http://localhost:8080/Game_2D.jar
```

Expect `206 Partial Content` and `Content-Range: bytes 0-99/...`. On Linux/macOS use `curl` and
replace `NUL` with `/dev/null`. The launcher also checks range support before starting Java.

### Publish to GitHub Pages

1. In the repository, open **Settings > Pages** and select **GitHub Actions** as the source.
2. Merge the browser deployment changes into `master` and push them to GitHub.
3. Open **Actions > Build and deploy browser game** and wait for both jobs to succeed.
4. Open the URL shown by the deployment. With the default repository name it is
   <https://cellul4r.github.io/typing_vs_demon_game/>.
5. Repeat the range check against the public JAR URL and the gameplay checklist below.

The workflow builds and tests pull requests without publishing. Pushes to `master` build and
publish automatically; **Run workflow** on `master` also supports manual deployment. It uses
the standard GitHub token and `github-pages` environment; no personal access token is required.
The launcher resolves the JAR relative to the page, so project Pages subdirectories work.

To roll back, revert the problematic release commit on `master` and push the revert. GitHub
Actions rebuilds and redeploys that source. Re-running a workflow for an old commit does not
restore source history and is not the documented rollback process.

### Browser verification and troubleshooting

Before publishing, test Chrome/Edge and Firefox:

- Start a session, navigate the tutorial, and select Easy, Normal, and Hard.
- Type and submit words with Enter; verify deletion, movement, items, score, and health.
- Verify title music, gameplay music, effects, pause/resume, game over, and returning to the menu.
- Enter and leave browser fullscreen; click back inside the game and check keyboard focus.
- Finish a complete play session and check input latency, frame pacing, and browser CPU use.
- Choose Exit and verify that Reload game starts a fresh session.

Open the browser developer console and Network tab if startup fails. The page reports missing
runtime loading, a missing JAR, unsupported range responses, and nonzero Java exit codes.
CheerpJ debug logging is disabled for normal play because it can affect performance. To
temporarily troubleshoot startup, add `enableDebug: true` to the `cheerpjInit` options in
`web/game.js` and rebuild. Keep Console open to collect the first error and its surrounding
logs, then remove that option after troubleshooting. Append `?profile=1` to the page URL to
log measured game-loop FPS every five seconds without enabling advanced debug logging.
Check required game/runtime downloads and Java exceptions. Some filesystem probes returning
404 are normal for CheerpJ; investigate the actual failing JAR/resource or exception.
For silence, click inside the game and check the browser/site sound settings. A black display
or unresponsive controls must be resolved and retested before treating a deployment as ready.

If Chrome reports `net::ERR_CACHE_OPERATION_NOT_SUPPORTED` for a CheerpJ runtime resource
such as `17/lib/modules`, try **Network > Disable cache** in DevTools, keep DevTools open,
reload the page, and choose Start game again. This temporarily bypasses Chrome's HTTP cache
to diagnose a failed runtime range download; it is not a required production setting.
If the same error persists, compare with Firefox and retain the console error and failed
request details. See [Chrome's cache controls](https://developer.chrome.com/docs/devtools/network/reference#disable-cache).

The launcher passes `typingvsdemon.browser=true` as a Java system property. This keeps the
Swing window at 1152 x 672 and skips desktop exclusive fullscreen. The HTML display scales
to the available space. The browser loop sleeps between pending updates to allow input and rendering
to run while retaining the 30 FPS update target. Desktop launches without that property keep their fullscreen behavior.
Browser audio uses HTML audio through CheerpJ native methods because the tested Java 17 runtime
could not provide a Java Sound `Clip`. Sounds load when used, with playback retried on user input
if autoplay is blocked. Desktop audio keeps using Java Sound and safely skips unavailable clips.
All `.png` resources must contain actual PNG data; the build checks their signatures to avoid
triggering unsupported native JPEG color management for mislabeled files.
High scores remain in memory for the current game process, as in the desktop version.

The page credits CheerpJ Core and Leaning Technologies. The hosted runtime is used under the
[CheerpJ Community License](https://cheerpj.com/docs/licensing.html) for qualifying personal or
FOSS projects. Review the license for other uses or self-hosting the runtime.

## Team Contribution
   * [cellul4r](https://github.com/Cellul4r) - Coding and Documentation
   * [Not-Tan](https://github.com/Not-Tan) - Coding and Documentation
   * [JellyFrist](https://github.com/Jellyfrist) - Documentation and Sound Management
   * [Fres26](https://github.com/Fres26) - Art, Animation and User Interface
   * [q28jes](https://github.com/q28jes) - Art, Animation and User Interface
   

