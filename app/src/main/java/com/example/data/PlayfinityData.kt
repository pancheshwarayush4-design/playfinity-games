package com.example.data

import androidx.compose.ui.graphics.Color

enum class GameCategory(val displayName: String, val tagline: String, val accentColor: Color) {
    ACTION("Action", "High-octane combat, tactical shooters & fast-paced melee", Color(0xFF00E5FF)),
    HORROR("Horror", "Survival thrillers, atmospheric dread & asymmetric chases", Color(0xFFFF1744)),
    ARCADE("Arcade", "Endless score chasers, roguelikes & neon reflex classics", Color(0xFF7C4DFF)),
    RACING("Racing", "Hypercar street circuits, drift battles & kart championships", Color(0xFF00E676)),
    ADVENTURE("Adventure", "Open-world exploration, sandbox realms & story quests", Color(0xFF38B6FF)),
    OFFLINE("Offline Games", "Play anywhere with zero internet or mobile data required", Color(0xFFFFB300))
}

enum class ShowcaseGroup(val label: String) {
    FEATURED("Featured Games"),
    LATEST("Latest Releases"),
    POPULAR("Popular Games")
}

data class DownloadableGame(
    val id: String,
    val title: String,
    val category: GameCategory,
    val showcases: Set<ShowcaseGroup>,
    val officialStoreName: String,
    val officialUrl: String,
    val sizeLabel: String,
    val ratingLabel: String,
    val developer: String,
    val description: String,
    val primaryColor: Color,
    val secondaryColor: Color
)

data class OnlineBrowserGame(
    val id: String,
    val title: String,
    val genre: String,
    val controlsHint: String,
    val description: String,
    val accentColor: Color
)

object PlayfinityRepository {

    val downloadableGames: List<DownloadableGame> = listOf(
        DownloadableGame(
            id = "shadow_fight_4",
            title = "Shadow Fight 4: Arena",
            category = GameCategory.ACTION,
            showcases = setOf(ShowcaseGroup.FEATURED, ShowcaseGroup.POPULAR),
            officialStoreName = "Google Play Official",
            officialUrl = "https://play.google.com/store/apps/details?id=com.nekki.shadowfightarena",
            sizeLabel = "165 MB",
            ratingLabel = "4.6 ★",
            developer = "NEKKI",
            description = "Console-grade 3D PvP ninja and samurai combat. Assemble a 3-hero squad with upgradable shadow abilities.",
            primaryColor = Color(0xFF071E3D),
            secondaryColor = Color(0xFF0077FF)
        ),
        DownloadableGame(
            id = "dead_effect_2",
            title = "Dead Effect 2",
            category = GameCategory.ACTION,
            showcases = setOf(ShowcaseGroup.LATEST),
            officialStoreName = "Google Play Official",
            officialUrl = "https://play.google.com/store/apps/details?id=com.badflyinteractive.deadeffect2",
            sizeLabel = "1.1 GB",
            ratingLabel = "4.5 ★",
            developer = "App Holdings",
            description = "Sci-fi action FPS aboard the ESS Meridian starship featuring cybernetic implants and 100+ upgradable weapons.",
            primaryColor = Color(0xFF0B162C),
            secondaryColor = Color(0xFF00B4D8)
        ),
        DownloadableGame(
            id = "dead_by_daylight",
            title = "Dead by Daylight Mobile",
            category = GameCategory.HORROR,
            showcases = setOf(ShowcaseGroup.FEATURED, ShowcaseGroup.POPULAR),
            officialStoreName = "Google Play Official",
            officialUrl = "https://play.google.com/store/apps/details?id=com.bhvr.deadbydaylight",
            sizeLabel = "1.9 GB",
            ratingLabel = "4.4 ★",
            developer = "Behaviour Interactive",
            description = "Intense 4v1 asymmetric multiplayer survival horror. Repair generators as a survivor or hunt through the Fog.",
            primaryColor = Color(0xFF1A0920),
            secondaryColor = Color(0xFFFF1744)
        ),
        DownloadableGame(
            id = "eyes_horror",
            title = "Eyes: Scary Thriller",
            category = GameCategory.HORROR,
            showcases = setOf(ShowcaseGroup.LATEST),
            officialStoreName = "Google Play Official",
            officialUrl = "https://play.google.com/store/apps/details?id=com.eyesthegame.eyes",
            sizeLabel = "98 MB",
            ratingLabel = "4.4 ★",
            developer = "FearlessGames",
            description = "First-person stealth horror exploration inside haunted manors and abandoned hospitals with dynamic monster AI.",
            primaryColor = Color(0xFF120C24),
            secondaryColor = Color(0xFF9D4EDD)
        ),
        DownloadableGame(
            id = "shattered_pixel_dungeon",
            title = "Shattered Pixel Dungeon",
            category = GameCategory.ARCADE,
            showcases = setOf(ShowcaseGroup.FEATURED, ShowcaseGroup.LATEST),
            officialStoreName = "F-Droid Official",
            officialUrl = "https://f-droid.org/packages/com.shatteredpixel.shatteredpixeldungeon/",
            sizeLabel = "24 MB",
            ratingLabel = "4.8 ★",
            developer = "Shattered Pixel",
            description = "Acclaimed open-source roguelike dungeon crawler with 5 distinct hero classes, artifacts, and procedural levels.",
            primaryColor = Color(0xFF09223B),
            secondaryColor = Color(0xFF00E5FF)
        ),
        DownloadableGame(
            id = "pacman_256",
            title = "PAC-MAN 256: Endless Maze",
            category = GameCategory.ARCADE,
            showcases = setOf(ShowcaseGroup.POPULAR),
            officialStoreName = "Google Play Official",
            officialUrl = "https://play.google.com/store/apps/details?id=eu.bandainamcoent.pacman256",
            sizeLabel = "82 MB",
            ratingLabel = "4.6 ★",
            developer = "BANDAI NAMCO",
            description = "Outrun the super-villain Glitch in an endless neon retro-arcade grid loaded with laser and tornado power-ups.",
            primaryColor = Color(0xFF081B38),
            secondaryColor = Color(0xFF7C4DFF)
        ),
        DownloadableGame(
            id = "supertuxkart",
            title = "SuperTuxKart 3D Racing",
            category = GameCategory.RACING,
            showcases = setOf(ShowcaseGroup.FEATURED),
            officialStoreName = "F-Droid Official",
            officialUrl = "https://f-droid.org/packages/org.supertuxkart.stk/",
            sizeLabel = "210 MB",
            ratingLabel = "4.7 ★",
            developer = "STK Team",
            description = "Full 3D open-source arcade kart racer with story campaigns, nitro drift boosts, battle arenas, and zero ads.",
            primaryColor = Color(0xFF04223A),
            secondaryColor = Color(0xFF00E676)
        ),
        DownloadableGame(
            id = "asphalt_legends",
            title = "Asphalt Legends Unite",
            category = GameCategory.RACING,
            showcases = setOf(ShowcaseGroup.POPULAR, ShowcaseGroup.LATEST),
            officialStoreName = "Google Play Official",
            officialUrl = "https://play.google.com/store/apps/details?id=com.gameloft.android.ANMP.GloftA9HM",
            sizeLabel = "2.4 GB",
            ratingLabel = "4.5 ★",
            developer = "Gameloft",
            description = "High-voltage arcade street racing with 250+ licensed hypercars, cross-platform multiplayer, and pulse nitro.",
            primaryColor = Color(0xFF061830),
            secondaryColor = Color(0xFF00E5FF)
        ),
        DownloadableGame(
            id = "sky_children",
            title = "Sky: Children of the Light",
            category = GameCategory.ADVENTURE,
            showcases = setOf(ShowcaseGroup.FEATURED, ShowcaseGroup.POPULAR),
            officialStoreName = "Google Play Official",
            officialUrl = "https://play.google.com/store/apps/details?id=com.tgc.sky.android",
            sizeLabel = "1.4 GB",
            ratingLabel = "4.7 ★",
            developer = "thatgamecompany",
            description = "Soar above enchanted clouds and explore seven breathtaking realms in an award-winning social adventure.",
            primaryColor = Color(0xFF072348),
            secondaryColor = Color(0xFF38B6FF)
        ),
        DownloadableGame(
            id = "luanti_minetest",
            title = "Luanti (Minetest) Sandbox",
            category = GameCategory.ADVENTURE,
            showcases = setOf(ShowcaseGroup.LATEST),
            officialStoreName = "F-Droid Official",
            officialUrl = "https://f-droid.org/packages/net.minetest.minetest/",
            sizeLabel = "32 MB",
            ratingLabel = "4.6 ★",
            developer = "Luanti Community",
            description = "Open-source infinite voxel world builder and survival adventure supporting offline worlds and mod servers.",
            primaryColor = Color(0xFF091D38),
            secondaryColor = Color(0xFF0096C7)
        ),
        DownloadableGame(
            id = "altos_odyssey",
            title = "Alto's Odyssey",
            category = GameCategory.OFFLINE,
            showcases = setOf(ShowcaseGroup.FEATURED, ShowcaseGroup.POPULAR),
            officialStoreName = "Google Play Official",
            officialUrl = "https://play.google.com/store/apps/details?id=com.noodlecake.altosodyssey",
            sizeLabel = "95 MB",
            ratingLabel = "4.7 ★",
            developer = "Noodlecake",
            description = "Sandboard across endless desert dunes, canyons, and temples completely offline with dynamic weather.",
            primaryColor = Color(0xFF0A1B36),
            secondaryColor = Color(0xFFFFB300)
        ),
        DownloadableGame(
            id = "unciv_strategy",
            title = "Unciv: 4X Empire Strategy",
            category = GameCategory.OFFLINE,
            showcases = setOf(ShowcaseGroup.LATEST),
            officialStoreName = "F-Droid Official",
            officialUrl = "https://f-droid.org/packages/com.unciv.app/",
            sizeLabel = "28 MB",
            ratingLabel = "4.8 ★",
            developer = "Yair Morgenstern",
            description = "Lightweight, ad-free offline civilization-building strategy game with deep research trees and diplomacy.",
            primaryColor = Color(0xFF091E3A),
            secondaryColor = Color(0xFF00E5FF)
        )
    )

    val onlineBrowserGames: List<OnlineBrowserGame> = listOf(
        OnlineBrowserGame(
            id = "cyber-drift",
            title = "Cyber Drift: Neon Overdrive",
            genre = "Racing / Arcade",
            controlsHint = "Drag on track or use LEFT / BOOST / RIGHT buttons to dodge red barriers & collect blue energy cores.",
            description = "Pilot an electric cyber-interceptor down a high-speed neon synth highway. Collect glowing blue cores for bonus points and hold Boost for overdrive speed!",
            accentColor = Color(0xFF00E5FF)
        ),
        OnlineBrowserGame(
            id = "void-striker",
            title = "Void Striker: Sector Zero",
            genre = "Action / Space Shooter",
            controlsHint = "Drag your starfighter or tap LEFT / FIRE / RIGHT to blast crimson drone squadrons before they breach perimeter.",
            description = "Command the PF-9 Starfighter in deep space. Auto-regenerating twin plasma cannons and overdrive rapid-fire let you shred incoming cyber-armadas.",
            accentColor = Color(0xFF38B6FF)
        ),
        OnlineBrowserGame(
            id = "quantum-pulse",
            title = "Quantum Pulse: Core Matrix",
            genre = "Arcade / Reflex",
            controlsHint = "Tap surging electric cyan nodes directly on the canvas before their containment ring collapses!",
            description = "High-speed mobile touch reflex challenge. Neutralize unstable quantum nodes across the cyber grid before 5 nodes overload.",
            accentColor = Color(0xFF7C4DFF)
        )
    )

    val androidGithubSetupSteps: List<Pair<String, String>> = listOf(
        "1. Create a Free GitHub Account" to "Open Chrome or Firefox on your Android phone, visit github.com, and sign up or log in. Tap the '+' icon in the top-right corner and select 'New repository'.",
        "2. Configure Your Repository" to "Name the repository 'playfinity-games', set visibility to 'Public', check 'Add a README file', and tap the green 'Create repository' button.",
        "3. Upload index.html, style.css & script.js" to "Inside your new repository, tap 'Add file' -> 'Upload files'. Use the Source Code Exporter below to save or copy index.html, style.css, and script.js, upload all three to the root folder, and tap 'Commit changes'.",
        "4. Activate Free GitHub Pages Hosting" to "Tap 'Settings' at the top of your repository, scroll down to 'Pages' in the menu, set Source to 'Deploy from a branch', select branch 'main' and '/ (root)', then tap 'Save'.",
        "5. Launch Your Live Gaming Website" to "Wait ~60 seconds and refresh the Pages settings screen. Your live mobile-friendly gaming website will be published at https://<your-username>.github.io/playfinity-games/!"
    )
}
