"use strict";

(() => {
  const width = 1152;
  const height = 672;
  const start = document.getElementById("start");
  const fullscreen = document.getElementById("fullscreen");
  const reload = document.getElementById("reload");
  const status = document.getElementById("status");
  const viewport = document.getElementById("game-viewport");
  const stage = document.getElementById("game-stage");
  const placeholder = document.getElementById("placeholder");
  const errorDetails = document.getElementById("error-details");
  const errorMessage = document.getElementById("error-message");
  let display;
  let startupObserver;
  let started = false;
  const sounds = new Map();
  const pendingAudio = new Set();
  let nextSoundId = 0;

  function playAudio(sound) {
    sound.audio.play().then(() => {
      pendingAudio.delete(sound);
    }).catch((error) => {
      if (error.name === "NotAllowedError" && sound.state === "playing") {
        pendingAudio.add(sound);
      } else if (error.name !== "AbortError") {
        console.warn("Audio playback failed:", sound.audio.src, error);
      }
    });
  }

  function unlockAudio() {
    for (const sound of pendingAudio) {
      if (sound.state === "playing") playAudio(sound);
    }
  }

  document.addEventListener("pointerdown", unlockAudio, { capture: true });
  document.addEventListener("keydown", unlockAudio, { capture: true });

  const audioNatives = {
    async Java_sound_Sound_browserCreate(_lib, resource, decibels) {
      const audio = new Audio(new URL(resource.replace(/^\/+/, ""), window.location.href).href);
      audio.preload = "none";
      audio.volume = Math.max(0, Math.min(1, Math.pow(10, decibels / 20)));
      document.body.append(audio);
      const id = nextSoundId++;
      sounds.set(id, { audio, state: "stopped" });
      return id;
    },
    async Java_sound_Sound_browserPlay(_lib, id, loop, restart) {
      const sound = sounds.get(id);
      if (!sound) return;
      sound.audio.loop = loop;
      if (restart) sound.audio.currentTime = 0;
      sound.state = "playing";
      playAudio(sound);
    },
    async Java_sound_Sound_browserStop(_lib, id) {
      const sound = sounds.get(id);
      if (!sound) return;
      sound.state = "stopped";
      pendingAudio.delete(sound);
      sound.audio.pause();
    },
    async Java_sound_Sound_browserPause(_lib, id) {
      const sound = sounds.get(id);
      if (!sound || sound.state !== "playing") return;
      sound.state = "paused";
      pendingAudio.delete(sound);
      sound.audio.pause();
    },
    async Java_sound_Sound_browserResume(_lib, id) {
      const sound = sounds.get(id);
      if (!sound || sound.state !== "paused") return;
      sound.state = "playing";
      playAudio(sound);
    },
  };

  function setStatus(message, state) {
    status.textContent = message;
    status.dataset.state = state;
  }

  function stopLoading(label) {
    startupObserver?.disconnect();
    startupObserver = undefined;
    start.dataset.loading = "false";
    start.removeAttribute("aria-busy");
    start.textContent = label;
  }

  function checkGameReady() {
    // CheerpJ 4.3 removes its startup overlay after the first rendered frame.
    if (display.classList.contains("cjStatus") || !display.querySelector("canvas")) return;
    stopLoading("Game running");
    setStatus("Game ready. Click inside the game for keyboard input and sound.", "running");
  }

  function fail(message, error) {
    stopLoading("Start game");
    setStatus(message, "error");
    errorMessage.textContent = error instanceof Error ? error.message : String(error);
    errorDetails.hidden = false;
    reload.hidden = false;
    start.disabled = true;
    fullscreen.disabled = true;
    console.error("Could not run Typing VS Demon:", error);
  }

  function fitDisplay() {
    const scale = Math.min(viewport.clientWidth / width, viewport.clientHeight / height);
    stage.style.setProperty("--game-scale", String(scale));
  }

  new ResizeObserver(fitDisplay).observe(viewport);
  fitDisplay();
  reload.addEventListener("click", () => window.location.reload());

  fullscreen.addEventListener("click", async () => {
    try {
      if (document.fullscreenElement === viewport) {
        await document.exitFullscreen();
      } else {
        await viewport.requestFullscreen();
      }
      display?.focus({ preventScroll: true });
    } catch (error) {
      setStatus("Fullscreen is unavailable. You can keep playing in the page.", "notice");
      console.warn("Fullscreen request failed:", error);
    }
  });

  document.addEventListener("fullscreenchange", () => {
    fullscreen.textContent = document.fullscreenElement === viewport ? "Exit fullscreen" : "Fullscreen";
    fitDisplay();
  });

  start.addEventListener("click", async () => {
    if (started) return;
    started = true;
    start.disabled = true;
    start.dataset.loading = "true";
    start.setAttribute("aria-busy", "true");
    start.textContent = "Loading...";
    setStatus("Checking game download…", "loading");

    try {
      const jar = new URL("./Game_2D.jar", window.location.href);
      const response = await fetch(jar, { headers: { Range: "bytes=0-0" }, cache: "no-store" });
      await response.body?.cancel();
      if (response.status !== 206 || !/^bytes 0-0\/\d+$/.test(response.headers.get("Content-Range") || "")) {
        throw new Error(`Game download returned HTTP ${response.status}. The JAR must exist and the server must support byte-range requests (206 Partial Content).`);
      }

      setStatus("Loading Java and game resources. The first launch may take a minute…", "loading");
      const launchParameters = new URL(window.location.href).searchParams;
      const javaProperties = ["typingvsdemon.browser=true"];
      if (launchParameters.get("profile") === "1") javaProperties.push("typingvsdemon.profile=true");
      await cheerpjInit({
        version: 17,
        natives: audioNatives,
        javaProperties,
        overrideShortcuts: (event) => stage.contains(document.activeElement) &&
          ["ArrowUp", "ArrowDown", "ArrowLeft", "ArrowRight", " ", "Backspace"].includes(event.key),
      });
      if (typeof cheerpjCreateDisplay !== "function" || typeof cheerpjRunJar !== "function") {
        throw new Error("CheerpJ initialization finished without the required display and JAR APIs. Reload the page and check the browser console.");
      }
      placeholder.hidden = true;
      display = cheerpjCreateDisplay(width, height, stage);
      display.tabIndex = 0;
      display.focus({ preventScroll: true });
      fullscreen.disabled = !document.fullscreenEnabled || !viewport.requestFullscreen;
      setStatus("Starting the game. Click inside it when it appears to activate keyboard input and sound.", "starting");
      startupObserver = new MutationObserver(checkGameReady);
      startupObserver.observe(display, { attributes: true, attributeFilter: ["class"], childList: true, subtree: true });
      checkGameReady();
      const exitCode = await cheerpjRunJar("/app" + jar.pathname);
      if (exitCode !== 0) {
        throw new Error(`The Java application exited with code ${exitCode}. Check the browser console for its error details.`);
      }
      stopLoading("Start game");
      setStatus("Game ended. Reload to play again.", "ended");
      for (const sound of sounds.values()) {
        sound.state = "stopped";
        sound.audio.pause();
      }
      pendingAudio.clear();
      fullscreen.disabled = true;
      reload.hidden = false;
    } catch (error) {
      fail("Could not start or continue the game. Check your connection, then reload. See technical details below.", error);
    }
  });

  // The loader supplies cheerpjInit; display and JAR APIs appear after it resolves.
  if (window.location.protocol === "file:") {
    fail("Open this page through a local web server, rather than directly from disk.",
      new Error("Run npx http-server build/cheerpj/site -p 8080 -c-1 and open http://localhost:8080."));
  } else if (typeof cheerpjInit !== "function") {
    fail("The browser runtime could not load. Check your connection or content blocker, then reload.",
      new Error("Unable to load CheerpJ Core from https://cjrtnc.leaningtech.com/4.3/loader.js."));
  }
})();
