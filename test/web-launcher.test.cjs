const assert = require("node:assert/strict");
const fs = require("node:fs");
const path = require("node:path");
const test = require("node:test");
const vm = require("node:vm");

const source = fs.readFileSync(path.join(__dirname, "../web/game.js"), "utf8");

function createPage({ href = "https://example.com/typing_vs_demon_game/", rangeStatus = 206,
  contentRange = "bytes 0-0/12345", runtimeMissing = false, exitCode = 0 } = {}) {
  const elements = new Map();
  const calls = [];
  const audioElements = [];
  const documentListeners = new Map();
  const observers = [];
  let finishGame;
  const gameLifetime = new Promise((resolve) => { finishGame = resolve; });
  function element(id) {
    return {
      id, hidden: ["reload", "error-details"].includes(id), disabled: id === "fullscreen", textContent: "", dataset: {},
      clientWidth: 1152, clientHeight: 672, listeners: {}, attributes: {}, style: { setProperty() {} },
      setAttribute(name, value) { this.attributes[name] = value; },
      removeAttribute(name) { delete this.attributes[name]; },
      addEventListener(name, callback) { this.listeners[name] = callback; },
      contains(target) { return target === this; },
      focus() { document.activeElement = this; },
      async requestFullscreen() { document.fullscreenElement = this; },
    };
  }
  for (const id of ["start", "fullscreen", "reload", "status", "game-viewport",
    "game-stage", "placeholder", "error-details", "error-message"]) {
    elements.set(id, element(id));
  }
  const display = element("display");
  display.initializing = true;
  display.hasCanvas = false;
  display.classList = { contains(name) { return name === 'cjStatus' && display.initializing; } };
  display.querySelector = (selector) => selector === 'canvas' && display.hasCanvas ? {} : null;
  const document = {
    activeElement: null, fullscreenElement: null, fullscreenEnabled: true,
    getElementById(id) { return elements.get(id); },
    body: { append(audio) { audioElements.push(audio); } },
    addEventListener(name, callback) {
      if (!documentListeners.has(name)) documentListeners.set(name, []);
      documentListeners.get(name).push(callback);
    },
    async exitFullscreen() { this.fullscreenElement = null; },
  };
  const window = { location: { href, protocol: new URL(href).protocol,
    reload() { calls.push(["reload"]); } } };
  const context = {
    URL, Error, document, window,
    console: { error() {}, warn() {} },
    ResizeObserver: class { observe() {} },
    MutationObserver: class {
      constructor(callback) { this.callback = callback; this.active = false; observers.push(this); }
      observe() { this.active = true; }
      disconnect() { this.active = false; }
    },
    Audio: class {
      constructor(src) { this.src = src; this.currentTime = 0; this.paused = true; this.blocked = false; }
      play() {
        if (this.blocked) {
          const error = new Error('Autoplay requires user input');
          error.name = 'NotAllowedError';
          return Promise.reject(error);
        }
        this.paused = false;
        return Promise.resolve();
      }
      pause() { this.paused = true; }
    },
    async fetch(url, options) {
      calls.push(["fetch", url.href, options]);
      return { status: rangeStatus, headers: { get() { return contentRange; } },
        body: { async cancel() { calls.push(["cancel"]); } } };
    },
    async cheerpjInit(options) {
      calls.push(["init", options]);
      // The real loader exports these APIs only after asynchronous initialization.
      await Promise.resolve();
      context.cheerpjCreateDisplay = (...args) => { calls.push(["display", ...args]); return display; };
      context.cheerpjRunJar = async (jarPath) => {
        calls.push(["run", jarPath]);
        await gameLifetime;
        return exitCode;
      };
    },
  };
  if (runtimeMissing) delete context.cheerpjInit;
  vm.runInNewContext(source, context);
  return { elements, calls, display, document, api: context, audioElements,
    showGame() {
      display.initializing = false;
      display.hasCanvas = true;
      for (const observer of observers) if (observer.active) observer.callback();
    },
    dispatch(name) { for (const callback of documentListeners.get(name) || []) callback(); },
    click(id) { return elements.get(id).listeners.click(); },
    finishGame,
  };
}

const settle = () => new Promise((resolve) => setImmediate(resolve));

test("accepts the loader-only API, then launches Java 17 once after initialization", async () => {
  const page = createPage();
  assert.equal(typeof page.api.cheerpjRunJar, "undefined");
  assert.equal(typeof page.api.cheerpjCreateDisplay, "undefined");
  assert.equal(page.elements.get("start").disabled, false);
  assert.notEqual(page.elements.get("status").dataset.state, "error");
  const lifetime = page.click("start");
  assert.equal(page.elements.get("start").textContent, "Loading...");
  assert.equal(page.elements.get("start").attributes['aria-busy'], 'true');
  await settle();
  assert.equal(page.calls.find(([name]) => name === "fetch")[1],
    "https://example.com/typing_vs_demon_game/Game_2D.jar");
  const options = page.calls.find(([name]) => name === "init")[1];
  assert.equal(options.version, 17);
  assert.deepEqual(Array.from(options.javaProperties), ["typingvsdemon.browser=true"]);
  assert.ok(page.calls.some(([name]) => name === "cancel"));
  assert.deepEqual(page.calls.find(([name]) => name === "display").slice(1, 3), [1152, 672]);
  assert.equal(page.calls.find(([name]) => name === "run")[1],
    "/app/typing_vs_demon_game/Game_2D.jar");
  await page.click("start");
  assert.equal(page.calls.filter(([name]) => name === "init").length, 1);
  assert.equal(page.elements.get("placeholder").hidden, true);
  assert.equal(page.elements.get("fullscreen").disabled, false);
  // Runtime initialization alone must not end loading before the first frame.
  assert.equal(page.elements.get("start").dataset.loading, 'true');
  page.showGame();
  assert.equal(page.elements.get("start").textContent, 'Game running');
  assert.equal(page.elements.get("start").dataset.loading, 'false');
  assert.equal(page.elements.get("start").attributes['aria-busy'], undefined);
  assert.equal(page.elements.get("status").dataset.state, 'running');
  page.finishGame();
  await lifetime;
  assert.equal(page.elements.get("status").dataset.state, "ended");
  assert.equal(page.elements.get("reload").hidden, false);
  assert.equal(page.elements.get("fullscreen").disabled, true);
});

test("supports a root-hosted page with an explicit index filename", async () => {
  const page = createPage({ href: "http://localhost:8080/index.html" });
  const lifetime = page.click("start");
  await settle();
  assert.equal(page.calls.find(([name]) => name === "run")[1], "/app/Game_2D.jar");
  page.finishGame();
  await lifetime;
});

test("stops before initialization when the JAR is missing or range support is invalid", async () => {
  for (const options of [{ rangeStatus: 404 }, { rangeStatus: 200 }, { contentRange: null }]) {
    const page = createPage(options);
    await page.click("start");
    assert.equal(page.elements.get("status").dataset.state, "error");
    assert.match(page.elements.get("error-message").textContent, /byte-range/);
    assert.equal(page.calls.some(([name]) => name === "init"), false);
    assert.equal(page.elements.get("reload").hidden, false);
    assert.equal(page.elements.get("start").dataset.loading, 'false');
    assert.equal(page.elements.get("start").attributes['aria-busy'], undefined);
  }
});

test("explains blocked runtime loading and unsupported file URLs", () => {
  const missingRuntime = createPage({ runtimeMissing: true });
  assert.equal(missingRuntime.elements.get("start").disabled, true);
  assert.match(missingRuntime.elements.get("status").textContent, /runtime could not load/);
  const localFile = createPage({ href: "file:///game/index.html" });
  assert.equal(localFile.elements.get("start").disabled, true);
  assert.match(localFile.elements.get("error-message").textContent, /http-server/);
});

test("reports a failing Java application exit", async () => {
  const page = createPage({ exitCode: 1 });
  const lifetime = page.click("start");
  await settle();
  page.finishGame();
  await lifetime;
  assert.equal(page.elements.get("status").dataset.state, "error");
  assert.match(page.elements.get("error-message").textContent, /exited with code 1/);
});

test("enters and leaves fullscreen and keeps the game focused", async () => {
  const page = createPage();
  const lifetime = page.click("start");
  await settle();
  await page.click("fullscreen");
  assert.equal(page.document.fullscreenElement, page.elements.get("game-viewport"));
  assert.equal(page.document.activeElement, page.display);
  await page.click("fullscreen");
  assert.equal(page.document.fullscreenElement, null);
  page.elements.get("game-viewport").requestFullscreen = async () => { throw new Error("Denied"); };
  await page.click("fullscreen");
  assert.match(page.elements.get("status").textContent, /keep playing/);
  page.finishGame();
  await lifetime;
});

test("browser audio resolves project paths, converts gain, and preserves pause position", async () => {
  const page = createPage();
  const lifetime = page.click('start');
  await settle();
  const native = page.calls.find(([name]) => name === 'init')[1].natives;
  const id = await native.Java_sound_Sound_browserCreate({}, '/resource/sound_res/main_menu.wav', -20);
  const audio = page.audioElements[0];
  assert.equal(audio.src, 'https://example.com/typing_vs_demon_game/resource/sound_res/main_menu.wav');
  assert.equal(audio.preload, 'none');
  assert.equal(audio.volume, 0.1);
  await native.Java_sound_Sound_browserPlay({}, id, true, false);
  await settle();
  assert.equal(audio.loop, true);
  assert.equal(audio.paused, false);
  audio.currentTime = 12;
  await native.Java_sound_Sound_browserPause({}, id);
  assert.equal(audio.paused, true);
  await native.Java_sound_Sound_browserResume({}, id);
  await settle();
  assert.equal(audio.paused, false);
  assert.equal(audio.currentTime, 12);
  await native.Java_sound_Sound_browserPlay({}, id, false, true);
  assert.equal(audio.currentTime, 0);
  assert.equal(audio.loop, false);
  page.finishGame();
  await lifetime;
  assert.equal(audio.paused, true);
});

test("browser audio retries blocked autoplay on input and cancels stopped sounds", async () => {
  const page = createPage();
  const lifetime = page.click('start');
  await settle();
  const native = page.calls.find(([name]) => name === 'init')[1].natives;
  const id = await native.Java_sound_Sound_browserCreate({}, '/resource/sound_res/play_game.wav', 6);
  const audio = page.audioElements[0];
  assert.equal(audio.volume, 1);
  audio.blocked = true;
  await native.Java_sound_Sound_browserPlay({}, id, true, false);
  await settle();
  assert.equal(audio.paused, true);
  audio.blocked = false;
  page.dispatch('pointerdown');
  await settle();
  assert.equal(audio.paused, false);
  await native.Java_sound_Sound_browserStop({}, id);
  page.dispatch('keydown');
  await settle();
  assert.equal(audio.paused, true);
  page.finishGame();
  await lifetime;
});
