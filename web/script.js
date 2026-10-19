/* ==========================================================================
   PLAYFINITY GAMES — Main Interactive Logic & 3 Playable HTML5 Games (script.js)
   ========================================================================== */

const OFFICIAL_GAMES = [
  {
    id: "shadow-fight-4",
    title: "Shadow Fight 4: Arena",
    category: "Action",
    showcase: ["featured", "popular"],
    store: "Google Play Official",
    size: "165 MB",
    rating: "4.6 ★",
    icon: "⚔️",
    gradient: "linear-gradient(135deg, #061c3d, #0066cc)",
    description: "Real-time PvP ninja & samurai combat with console-grade 3D animations and deep hero talent trees.",
    url: "https://play.google.com/store/apps/details?id=com.nekki.shadowfightarena"
  },
  {
    id: "dead-effect-2",
    title: "Dead Effect 2",
    category: "Action",
    showcase: ["latest"],
    store: "Google Play Official",
    size: "1.1 GB",
    rating: "4.5 ★",
    icon: "🔫",
    gradient: "linear-gradient(135deg, #0c142c, #004e92)",
    description: "Sci-fi action shooter aboard the ESS Meridian starship with upgradable cybernetic weapons.",
    url: "https://play.google.com/store/apps/details?id=com.badflyinteractive.deadeffect2"
  },
  {
    id: "dead-by-daylight",
    title: "Dead by Daylight Mobile",
    category: "Horror",
    showcase: ["featured", "popular"],
    store: "Google Play Official",
    size: "1.9 GB",
    rating: "4.4 ★",
    icon: "💀",
    gradient: "linear-gradient(135deg, #16091e, #003973)",
    description: "Asymmetric 4v1 multiplayer survival horror where survivors outsmart relentless killers in the Fog.",
    url: "https://play.google.com/store/apps/details?id=com.bhvr.deadbydaylight"
  },
  {
    id: "eyes-horror",
    title: "Eyes: Scary Thriller",
    category: "Horror",
    showcase: ["latest"],
    store: "Google Play Official",
    size: "98 MB",
    rating: "4.4 ★",
    icon: "👁️",
    gradient: "linear-gradient(135deg, #100b20, #1f3c68)",
    description: "First-person stealth horror exploration through cursed mansions with dynamic monster AI.",
    url: "https://play.google.com/store/apps/details?id=com.eyesthegame.eyes"
  },
  {
    id: "super-tux-kart",
    title: "SuperTuxKart Racing",
    category: "Racing",
    showcase: ["featured"],
    store: "F-Droid / Play Official",
    size: "210 MB",
    rating: "4.7 ★",
    icon: "🏎️",
    gradient: "linear-gradient(135deg, #001f3f, #0088cc)",
    description: "Open-source 3D arcade kart racer with story mode, time trials, local multiplayer, and zero ads.",
    url: "https://f-droid.org/packages/org.supertuxkart.stk/"
  },
  {
    id: "asphalt-legends",
    title: "Asphalt Legends Unite",
    category: "Racing",
    showcase: ["popular", "latest"],
    store: "Google Play Official",
    size: "2.4 GB",
    rating: "4.5 ★",
    icon: "⚡",
    gradient: "linear-gradient(135deg, #04152d, #00a8ff)",
    description: "Hypercar street racing featuring licensed hypercars, cross-play co-op chases, and neon nitro tracks.",
    url: "https://play.google.com/store/apps/details?id=com.gameloft.android.ANMP.GloftA9HM"
  },
  {
    id: "pac-man-256",
    title: "PAC-MAN 256: Endless Maze",
    category: "Arcade",
    showcase: ["popular"],
    store: "Google Play Official",
    size: "82 MB",
    rating: "4.6 ★",
    icon: "🕹️",
    gradient: "linear-gradient(135deg, #071936, #0072ff)",
    description: "Outrun the super-villain Glitch in an endless neon arcade maze packed with laser and freeze power-ups.",
    url: "https://play.google.com/store/apps/details?id=eu.bandainamcoent.pacman256"
  },
  {
    id: "shattered-pixel",
    title: "Shattered Pixel Dungeon",
    category: "Arcade",
    showcase: ["featured", "latest"],
    store: "F-Droid Official",
    size: "24 MB",
    rating: "4.8 ★",
    icon: "🛡️",
    gradient: "linear-gradient(135deg, #08203e, #00b4db)",
    description: "Critically acclaimed open-source roguelike dungeon crawler with 5 hero classes and procedural floors.",
    url: "https://f-droid.org/packages/com.shatteredpixel.shatteredpixeldungeon/"
  },
  {
    id: "sky-children",
    title: "Sky: Children of the Light",
    category: "Adventure",
    showcase: ["featured", "popular"],
    store: "Google Play Official",
    size: "1.4 GB",
    rating: "4.7 ★",
    icon: "🧭",
    gradient: "linear-gradient(135deg, #062246, #0099ff)",
    description: "Soar across seven dreamlike realms in an award-winning social adventure from the creators of Journey.",
    url: "https://play.google.com/store/apps/details?id=com.tgc.sky.android"
  },
  {
    id: "minetest-luanti",
    title: "Luanti (Minetest) Sandbox",
    category: "Adventure",
    showcase: ["latest"],
    store: "F-Droid Official",
    size: "32 MB",
    rating: "4.6 ★",
    icon: "⛏️",
    gradient: "linear-gradient(135deg, #071b34, #0077b6)",
    description: "Infinite open-source voxel world builder and survival adventure supporting singleplayer and community servers.",
    url: "https://f-droid.org/packages/net.minetest.minetest/"
  },
  {
    id: "alto-odyssey",
    title: "Alto's Odyssey",
    category: "Offline Games",
    showcase: ["featured", "popular"],
    store: "Google Play Official",
    size: "95 MB",
    rating: "4.7 ★",
    icon: "🏜️",
    gradient: "linear-gradient(135deg, #081832, #005bea)",
    description: "Sandboard across windswept dunes and ancient temples completely offline with Zen mode and fluid physics.",
    url: "https://play.google.com/store/apps/details?id=com.noodlecake.altosodyssey"
  },
  {
    id: "unciv-strategy",
    title: "Unciv: 4X Empire Strategy",
    category: "Offline Games",
    showcase: ["latest"],
    store: "F-Droid Official",
    size: "28 MB",
    rating: "4.8 ★",
    icon: "🏛️",
    gradient: "linear-gradient(135deg, #091e3a, #0096c7)",
    description: "Full offline civilization-building turn-based strategy game with tech trees, diplomacy, and zero ads.",
    url: "https://f-droid.org/packages/com.unciv.app/"
  }
];

let selectedCategory = "All";
let currentShowcaseTab = "featured";
let searchQuery = "";

// Render helper for game cards
function createGameCardHTML(game) {
  return `
    <article class="game-card" data-category="${game.category}">
      <div class="game-cover" style="background: ${game.gradient}">
        <div class="game-cover-top">
          <span class="badge-cat">${game.category}</span>
          <span class="badge-store">&#10004; ${game.store}</span>
        </div>
        <div class="game-cover-icon" aria-hidden="true">${game.icon}</div>
      </div>
      <div class="game-body">
        <h3 class="game-title">${game.title}</h3>
        <p class="game-desc">${game.description}</p>
        <div class="game-meta">
          <span>Size: ${game.size}</span>
          <span>Rating: ${game.rating}</span>
        </div>
        <a href="${game.url}" target="_blank" rel="noopener noreferrer" class="btn btn-primary btn-full">
          Official Download &#8599;
        </a>
      </div>
    </article>
  `;
}

function renderShowcase() {
  const showcaseGrid = document.getElementById("showcaseGrid");
  if (!showcaseGrid) return;
  const list = OFFICIAL_GAMES.filter((g) => g.showcase.includes(currentShowcaseTab));
  showcaseGrid.innerHTML = list.map(createGameCardHTML).join("");
}

function renderDownloadGames() {
  const grid = document.getElementById("downloadGamesGrid");
  const badge = document.getElementById("activeFilterBadge");
  const status = document.getElementById("searchStatusText");
  if (!grid) return;

  const q = searchQuery.trim().toLowerCase();
  const filtered = OFFICIAL_GAMES.filter((g) => {
    const matchesCat = selectedCategory === "All" || g.category === selectedCategory;
    const matchesSearch =
      !q ||
      g.title.toLowerCase().includes(q) ||
      g.category.toLowerCase().includes(q) ||
      g.description.toLowerCase().includes(q);
    return matchesCat && matchesSearch;
  });

  if (badge) {
    badge.textContent = `Showing: ${selectedCategory}${q ? ` • "${searchQuery}"` : ""} (${filtered.length})`;
  }
  if (status) {
    status.textContent = q ? `Found ${filtered.length} official game(s) matching "${searchQuery}"` : "";
  }

  if (filtered.length === 0) {
    grid.innerHTML = `
      <div class="about-card" style="grid-column: 1 / -1; text-align: center;">
        <h3>No matching games found</h3>
        <p>Try clearing your search query or selecting "All Games" in Categories.</p>
      </div>
    `;
    return;
  }
  grid.innerHTML = filtered.map(createGameCardHTML).join("");
}

// =========================================================================
// HTML5 MULTI-GAME ARCADE ENGINE (Cyber Drift, Void Striker, Quantum Pulse)
// =========================================================================
let activeGameId = "cyber-drift";
let score = 0;
let bestScores = { "cyber-drift": 0, "void-striker": 0, "quantum-pulse": 0 };
let inputState = { left: false, right: false, action: false };
let gameState = {};

function initArcadeGame(gameId) {
  activeGameId = gameId;
  score = 0;
  const titleMap = {
    "cyber-drift": "Cyber Drift: Neon Overdrive",
    "void-striker": "Void Striker: Sector Zero",
    "quantum-pulse": "Quantum Pulse: Core Matrix"
  };
  const titleEl = document.getElementById("activeGameTitle");
  if (titleEl) titleEl.textContent = titleMap[gameId] || "Cyber Drift";

  document.querySelectorAll(".online-game-card").forEach((c) => {
    c.classList.toggle("active-online-card", c.dataset.onlineGame === gameId);
  });

  if (gameId === "cyber-drift") {
    gameState = {
      playerX: 400,
      speed: 6,
      roadOffset: 0,
      obstacles: [],
      orbs: [],
      spawnTimer: 0,
      gameOver: false
    };
  } else if (gameId === "void-striker") {
    gameState = {
      shipX: 400,
      bullets: [],
      enemies: [],
      stars: Array.from({ length: 45 }, () => ({
        x: Math.random() * 800,
        y: Math.random() * 460,
        s: 1 + Math.random() * 2.5
      })),
      fireCooldown: 0,
      spawnTimer: 0,
      gameOver: false
    };
  } else {
    gameState = {
      nodes: [],
      spawnTimer: 0,
      missed: 0,
      gameOver: false
    };
  }
  updateScoreUI();
}

function updateScoreUI() {
  if (score > (bestScores[activeGameId] || 0)) {
    bestScores[activeGameId] = score;
  }
  const scoreEl = document.getElementById("cabinetScoreDisplay");
  if (scoreEl) {
    scoreEl.textContent = `SCORE: ${score} | BEST: ${bestScores[activeGameId]}`;
  }
}

function stepArcadeLoop() {
  const canvas = document.getElementById("gameCanvas");
  if (!canvas) return;
  const ctx = canvas.getContext("2d");

  ctx.fillStyle = "#040812";
  ctx.fillRect(0, 0, canvas.width, canvas.height);

  if (gameState.gameOver) {
    ctx.fillStyle = "rgba(5, 7, 14, 0.85)";
    ctx.fillRect(0, 0, canvas.width, canvas.height);
    ctx.fillStyle = "#00e5ff";
    ctx.font = "bold 32px sans-serif";
    ctx.textAlign = "center";
    ctx.fillText("SYSTEM OVERLOAD — GAME OVER", 400, 195);
    ctx.fillStyle = "#f4f8ff";
    ctx.font = "bold 20px sans-serif";
    ctx.fillText(`Final Score: ${score}  •  Tap Canvas or Restart to Play Again`, 400, 245);
    requestAnimationFrame(stepArcadeLoop);
    return;
  }

  if (activeGameId === "cyber-drift") {
    updateCyberDrift(ctx);
  } else if (activeGameId === "void-striker") {
    updateVoidStriker(ctx);
  } else {
    updateQuantumPulse(ctx);
  }

  requestAnimationFrame(stepArcadeLoop);
}

function updateCyberDrift(ctx) {
  const boost = inputState.action ? 1.65 : 1;
  if (inputState.left) gameState.playerX = Math.max(190, gameState.playerX - 7.5);
  if (inputState.right) gameState.playerX = Math.min(610, gameState.playerX + 7.5);

  gameState.roadOffset = (gameState.roadOffset + gameState.speed * boost) % 48;

  // Draw Neon Highway
  ctx.strokeStyle = "#00e5ff";
  ctx.lineWidth = 3;
  ctx.strokeRect(160, 0, 480, 460);

  ctx.strokeStyle = "rgba(0, 229, 255, 0.35)";
  ctx.setLineDash([26, 22]);
  ctx.lineDashOffset = -gameState.roadOffset;
  ctx.beginPath();
  ctx.moveTo(320, 0);
  ctx.lineTo(320, 460);
  ctx.moveTo(480, 0);
  ctx.lineTo(480, 460);
  ctx.stroke();
  ctx.setLineDash([]);

  // Spawn obstacles & orbs
  gameState.spawnTimer++;
  if (gameState.spawnTimer % 42 === 0) {
    const laneX = [240, 400, 560][Math.floor(Math.random() * 3)];
    gameState.obstacles.push({ x: laneX, y: -40 });
  }
  if (gameState.spawnTimer % 65 === 0) {
    const orbX = 200 + Math.random() * 400;
    gameState.orbs.push({ x: orbX, y: -20 });
  }

  // Draw & update orbs
  ctx.fillStyle = "#00e5ff";
  gameState.orbs.forEach((o) => {
    o.y += 5.5 * boost;
    ctx.beginPath();
    ctx.arc(o.x, o.y, 11, 0, Math.PI * 2);
    ctx.fill();
    if (Math.hypot(o.x - gameState.playerX, o.y - 380) < 34) {
      o.collected = true;
      score += 25;
      updateScoreUI();
    }
  });
  gameState.orbs = gameState.orbs.filter((o) => !o.collected && o.y < 490);

  // Draw & update obstacles
  ctx.fillStyle = "#ff1744";
  gameState.obstacles.forEach((ob) => {
    ob.y += (6.2 + score * 0.004) * boost;
    ctx.fillRect(ob.x - 32, ob.y - 18, 64, 36);
    if (Math.abs(ob.x - gameState.playerX) < 46 && Math.abs(ob.y - 380) < 34) {
      gameState.gameOver = true;
    }
    if (ob.y > 465 && !ob.passed) {
      ob.passed = true;
      score += 10;
      updateScoreUI();
    }
  });
  gameState.obstacles = gameState.obstacles.filter((ob) => ob.y < 500);

  // Draw Player Cyber Interceptor
  ctx.fillStyle = "#00e5ff";
  ctx.beginPath();
  ctx.moveTo(gameState.playerX, 345);
  ctx.lineTo(gameState.playerX + 24, 410);
  ctx.lineTo(gameState.playerX - 24, 410);
  ctx.closePath();
  ctx.fill();
}

function updateVoidStriker(ctx) {
  if (inputState.left) gameState.shipX = Math.max(45, gameState.shipX - 8);
  if (inputState.right) gameState.shipX = Math.min(755, gameState.shipX + 8);

  ctx.fillStyle = "#38b6ff";
  gameState.stars.forEach((st) => {
    st.y = (st.y + st.s) % 460;
    ctx.fillRect(st.x, st.y, 2, 2);
  });

  if (gameState.fireCooldown > 0) gameState.fireCooldown--;
  if ((inputState.action || gameState.spawnTimer % 16 === 0) && gameState.fireCooldown === 0) {
    gameState.bullets.push({ x: gameState.shipX - 12, y: 375 }, { x: gameState.shipX + 12, y: 375 });
    gameState.fireCooldown = 10;
  }

  gameState.spawnTimer++;
  if (gameState.spawnTimer % 46 === 0) {
    gameState.enemies.push({ x: 60 + Math.random() * 680, y: -25, hp: 2 });
  }

  ctx.fillStyle = "#00e5ff";
  gameState.bullets.forEach((b) => {
    b.y -= 11;
    ctx.fillRect(b.x - 2, b.y, 4, 14);
  });
  gameState.bullets = gameState.bullets.filter((b) => b.y > -20);

  ctx.fillStyle = "#ff1744";
  gameState.enemies.forEach((en) => {
    en.y += 2.6 + score * 0.002;
    ctx.beginPath();
    ctx.arc(en.x, en.y, 20, 0, Math.PI * 2);
    ctx.fill();

    gameState.bullets.forEach((b) => {
      if (Math.hypot(b.x - en.x, b.y - en.y) < 22) {
        b.y = -100;
        en.hp--;
        if (en.hp <= 0) {
          en.dead = true;
          score += 20;
          updateScoreUI();
        }
      }
    });

    if (en.y > 410) {
      gameState.gameOver = true;
    }
  });
  gameState.enemies = gameState.enemies.filter((e) => !e.dead);

  // Starfighter
  ctx.fillStyle = "#00e5ff";
  ctx.beginPath();
  ctx.moveTo(gameState.shipX, 365);
  ctx.lineTo(gameState.shipX + 26, 415);
  ctx.lineTo(gameState.shipX, 402);
  ctx.lineTo(gameState.shipX - 26, 415);
  ctx.closePath();
  ctx.fill();
}

function updateQuantumPulse(ctx) {
  gameState.spawnTimer++;
  if (gameState.spawnTimer % 45 === 0) {
    gameState.nodes.push({
      x: 110 + Math.random() * 580,
      y: 80 + Math.random() * 290,
      r: 36,
      ttl: 115
    });
  }

  ctx.fillStyle = "#94a7c6";
  ctx.font = "bold 16px sans-serif";
  ctx.textAlign = "left";
  ctx.fillText(`Tap Electric Nodes Before Overload! Integrity: ${Math.max(0, 5 - gameState.missed)}/5`, 24, 34);

  gameState.nodes.forEach((n) => {
    n.ttl--;
    ctx.strokeStyle = "#00e5ff";
    ctx.lineWidth = 3;
    ctx.beginPath();
    ctx.arc(n.x, n.y, n.r * (n.ttl / 115), 0, Math.PI * 2);
    ctx.stroke();

    ctx.fillStyle = "rgba(0, 229, 255, 0.35)";
    ctx.beginPath();
    ctx.arc(n.x, n.y, 18, 0, Math.PI * 2);
    ctx.fill();

    if (n.ttl <= 0) {
      n.expired = true;
      gameState.missed++;
      if (gameState.missed >= 5) gameState.gameOver = true;
    }
  });
  gameState.nodes = gameState.nodes.filter((n) => !n.expired && !n.hit);
}

// =========================================================================
// INITIALIZATION & EVENT LISTENERS
// =========================================================================
document.addEventListener("DOMContentLoaded", () => {
  renderShowcase();
  renderDownloadGames();
  initArcadeGame("cyber-drift");
  requestAnimationFrame(stepArcadeLoop);

  // Mobile Menu Toggle
  const menuBtn = document.getElementById("mobileMenuBtn");
  const mainNav = document.getElementById("mainNav");
  if (menuBtn && mainNav) {
    menuBtn.addEventListener("click", () => {
      const isOpen = mainNav.classList.toggle("open");
      menuBtn.setAttribute("aria-expanded", String(isOpen));
    });
    mainNav.querySelectorAll(".nav-link").forEach((link) => {
      link.addEventListener("click", () => {
        mainNav.classList.remove("open");
        menuBtn.setAttribute("aria-expanded", "false");
      });
    });
  }

  // Highlight Showcase Tabs
  document.querySelectorAll(".tab-btn").forEach((btn) => {
    btn.addEventListener("click", () => {
      document.querySelectorAll(".tab-btn").forEach((b) => {
        b.classList.remove("active");
        b.setAttribute("aria-selected", "false");
      });
      btn.classList.add("active");
      btn.setAttribute("aria-selected", "true");
      currentShowcaseTab = btn.dataset.showcase;
      renderShowcase();
    });
  });

  // Category Cards Filtering
  document.querySelectorAll(".category-card").forEach((card) => {
    card.addEventListener("click", () => {
      document.querySelectorAll(".category-card").forEach((c) => c.classList.remove("active"));
      card.classList.add("active");
      selectedCategory = card.dataset.category;
      renderDownloadGames();
      const dlSection = document.getElementById("download-games");
      if (dlSection) dlSection.scrollIntoView({ behavior: "smooth" });
    });
  });

  // Search Input
  const searchInput = document.getElementById("gameSearchInput");
  const clearBtn = document.getElementById("clearSearchBtn");
  const searchBtn = document.getElementById("searchActionBtn");

  if (searchInput) {
    searchInput.addEventListener("input", (e) => {
      searchQuery = e.target.value;
      if (clearBtn) clearBtn.hidden = !searchQuery;
      renderDownloadGames();
    });
  }
  if (clearBtn) {
    clearBtn.addEventListener("click", () => {
      searchQuery = "";
      if (searchInput) searchInput.value = "";
      clearBtn.hidden = true;
      renderDownloadGames();
    });
  }
  if (searchBtn) {
    searchBtn.addEventListener("click", () => {
      renderDownloadGames();
      const dlSection = document.getElementById("download-games");
      if (dlSection) dlSection.scrollIntoView({ behavior: "smooth" });
    });
  }

  // Launch Playable Online Games
  document.querySelectorAll(".launch-online-btn").forEach((btn) => {
    btn.addEventListener("click", () => {
      initArcadeGame(btn.dataset.launch);
      const cabinet = document.getElementById("arcadeCabinet");
      if (cabinet) cabinet.scrollIntoView({ behavior: "smooth" });
    });
  });

  // Restart & Fullscreen Controls
  const restartBtn = document.getElementById("restartGameBtn");
  if (restartBtn) {
    restartBtn.addEventListener("click", () => initArcadeGame(activeGameId));
  }

  const fsBtn = document.getElementById("fullscreenGameBtn");
  const cabinet = document.getElementById("arcadeCabinet");
  if (fsBtn && cabinet) {
    fsBtn.addEventListener("click", () => {
      if (!document.fullscreenElement) {
        if (cabinet.requestFullscreen) cabinet.requestFullscreen();
      } else if (document.exitFullscreen) {
        document.exitFullscreen();
      }
    });
  }

  // Canvas Pointer / Touch Interaction
  const canvas = document.getElementById("gameCanvas");
  if (canvas) {
    canvas.addEventListener("pointerdown", (e) => {
      const rect = canvas.getBoundingClientRect();
      const x = ((e.clientX - rect.left) / rect.width) * canvas.width;
      const y = ((e.clientY - rect.top) / rect.height) * canvas.height;

      if (gameState.gameOver) {
        initArcadeGame(activeGameId);
        return;
      }

      if (activeGameId === "quantum-pulse") {
        gameState.nodes.forEach((n) => {
          if (Math.hypot(n.x - x, n.y - y) <= n.r + 14) {
            n.hit = true;
            score += 15;
            updateScoreUI();
          }
        });
      } else {
        if (x < 400) inputState.left = true;
        else inputState.right = true;
      }
    });

    canvas.addEventListener("pointermove", (e) => {
      if (e.buttons > 0 && activeGameId !== "quantum-pulse") {
        const rect = canvas.getBoundingClientRect();
        const x = ((e.clientX - rect.left) / rect.width) * canvas.width;
        if (activeGameId === "cyber-drift") {
          gameState.playerX = Math.max(190, Math.min(610, x));
        } else if (activeGameId === "void-striker") {
          gameState.shipX = Math.max(45, Math.min(755, x));
        }
      }
    });

    canvas.addEventListener("pointerup", () => {
      inputState.left = false;
      inputState.right = false;
    });
  }

  // Touch Buttons
  const bindHold = (id, key) => {
    const el = document.getElementById(id);
    if (!el) return;
    el.addEventListener("pointerdown", () => (inputState[key] = true));
    el.addEventListener("pointerup", () => (inputState[key] = false));
    el.addEventListener("pointerleave", () => (inputState[key] = false));
  };
  bindHold("touchLeftBtn", "left");
  bindHold("touchRightBtn", "right");
  bindHold("touchActionBtn", "action");

  // Keyboard Support
  window.addEventListener("keydown", (e) => {
    if (e.key === "ArrowLeft" || e.key === "a") inputState.left = true;
    if (e.key === "ArrowRight" || e.key === "d") inputState.right = true;
    if (e.key === " ") inputState.action = true;
  });
  window.addEventListener("keyup", (e) => {
    if (e.key === "ArrowLeft" || e.key === "a") inputState.left = false;
    if (e.key === "ArrowRight" || e.key === "d") inputState.right = false;
    if (e.key === " ") inputState.action = false;
  });

  // Contact Form
  const contactForm = document.getElementById("contactForm");
  const feedback = document.getElementById("contactFeedback");
  if (contactForm && feedback) {
    contactForm.addEventListener("submit", (e) => {
      e.preventDefault();
      const name = document.getElementById("senderName").value.trim();
      const topic = document.getElementById("senderTopic").value;
      if (!name) return;
      feedback.textContent = `Thanks, ${name}! Your "${topic}" message has been logged by PLAYFINITY GAMES.`;
      contactForm.reset();
    });
  }
});
