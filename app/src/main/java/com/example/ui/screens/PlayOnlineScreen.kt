package com.example.ui.screens

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ScreenRotation
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.OnlineBrowserGame
import com.example.data.PlayfinityRepository
import com.example.ui.theme.BorderNeonSubtle
import com.example.ui.theme.CardSurfaceDark
import com.example.ui.theme.CrimsonDanger
import com.example.ui.theme.DeepSpaceDark
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.NeonBlueSecondary
import com.example.ui.theme.NeonGreenStatus
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.TextPrimaryWhite
import com.example.ui.theme.TextSecondaryMuted
import kotlin.math.abs
import kotlin.math.hypot
import kotlin.random.Random

private data class ObstacleEntity(var xNorm: Float, var yNorm: Float, var passed: Boolean = false)
private data class OrbEntity(var xNorm: Float, var yNorm: Float)
private data class BulletEntity(var xNorm: Float, var yNorm: Float)
private data class EnemyEntity(var xNorm: Float, var yNorm: Float, var hp: Int = 2)
private data class QuantumNodeEntity(var xNorm: Float, var yNorm: Float, var ttl: Float = 1f)

@Composable
fun PlayOnlineScreen(
    activeGameId: String,
    onSelectGame: (String) -> Unit,
    isFullScreen: Boolean,
    onToggleFullScreen: (Boolean) -> Unit,
    onOpenLiveWebPortal: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val games = PlayfinityRepository.onlineBrowserGames
    val activeGame = games.find { it.id == activeGameId } ?: games.first()

    var isLandscapeLocked by rememberSaveable { mutableStateOf(false) }
    var score by remember(activeGameId) { mutableIntStateOf(0) }
    var bestScoreCyber by rememberSaveable { mutableIntStateOf(0) }
    var bestScoreVoid by rememberSaveable { mutableIntStateOf(0) }
    var bestScoreQuantum by rememberSaveable { mutableIntStateOf(0) }
    var isGameOver by remember(activeGameId) { mutableStateOf(false) }
    var restartTrigger by remember { mutableIntStateOf(0) }

    // Manage landscape orientation for games when requested
    DisposableEffect(isLandscapeLocked) {
        if (isLandscapeLocked) {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        } else {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
        onDispose {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    val currentBest = when (activeGame.id) {
        "cyber-drift" -> bestScoreCyber
        "void-striker" -> bestScoreVoid
        else -> bestScoreQuantum
    }

    fun updateBest(newScore: Int) {
        when (activeGame.id) {
            "cyber-drift" -> if (newScore > bestScoreCyber) bestScoreCyber = newScore
            "void-striker" -> if (newScore > bestScoreVoid) bestScoreVoid = newScore
            else -> if (newScore > bestScoreQuantum) bestScoreQuantum = newScore
        }
    }

    if (isFullScreen) {
        BackHandler {
            isLandscapeLocked = false
            onToggleFullScreen(false)
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(ObsidianBlack)
                .padding(10.dp)
        ) {
            ArcadeCabinetCard(
                game = activeGame,
                score = score,
                bestScore = currentBest,
                isGameOver = isGameOver,
                isFullScreen = true,
                isLandscape = isLandscapeLocked,
                restartTrigger = restartTrigger,
                onScoreChange = {
                    score = it
                    updateBest(it)
                },
                onGameOverChange = { isGameOver = it },
                onRestart = {
                    score = 0
                    isGameOver = false
                    restartTrigger++
                },
                onToggleLandscape = { isLandscapeLocked = !isLandscapeLocked },
                onToggleFullScreen = {
                    isLandscapeLocked = false
                    onToggleFullScreen(false)
                },
                modifier = Modifier.fillMaxSize()
            )
        }
        return
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "INSTANT HTML5 & MOBILE ARCADE",
                        style = MaterialTheme.typography.labelLarge,
                        color = ElectricBlue
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "PLAY ONLINE",
                        style = MaterialTheme.typography.headlineLarge,
                        color = TextPrimaryWhite
                    )
                }
                OutlinedButton(
                    onClick = onOpenLiveWebPortal,
                    border = BorderStroke(1.dp, ElectricBlue),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = ElectricBlue),
                    modifier = Modifier.testTag("open_web_portal_from_online")
                ) {
                    Icon(
                        imageVector = Icons.Default.Language,
                        contentDescription = "Launch HTML5 WebView Game",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("HTML5 WebView")
                }
            }
        }

        // Game Selection Horizontal Carousel
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(games, key = { it.id }) { item ->
                    val selected = item.id == activeGame.id
                    Card(
                        onClick = { onSelectGame(item.id) },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (selected) Color(0xFF152548) else CardSurfaceDark
                        ),
                        border = BorderStroke(
                            width = if (selected) 2.dp else 1.dp,
                            color = if (selected) ElectricBlue else BorderNeonSubtle
                        ),
                        modifier = Modifier
                            .width(275.dp)
                            .testTag("online_game_card_${item.id}")
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    color = item.accentColor.copy(alpha = 0.18f),
                                    shape = RoundedCornerShape(50),
                                    border = BorderStroke(1.dp, item.accentColor)
                                ) {
                                    Text(
                                        text = "PLAYABLE NOW",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = item.accentColor,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
                                    )
                                }
                                Icon(
                                    imageVector = Icons.Default.SportsEsports,
                                    contentDescription = null,
                                    tint = item.accentColor
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.titleMedium,
                                color = TextPrimaryWhite
                            )
                            Text(
                                text = item.genre,
                                style = MaterialTheme.typography.labelMedium,
                                color = ElectricBlue
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = item.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondaryMuted,
                                maxLines = 3
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = {
                                    onSelectGame(item.id)
                                    score = 0
                                    isGameOver = false
                                    restartTrigger++
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (selected) ElectricBlue else NeonBlueSecondary,
                                    contentColor = if (selected) ObsidianBlack else TextPrimaryWhite
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("play_now_btn_${item.id}")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(if (selected) "PLAYING NOW" else "PLAY NOW")
                            }
                        }
                    }
                }
            }
        }

        // Interactive Playable Arcade Cabinet
        item {
            ArcadeCabinetCard(
                game = activeGame,
                score = score,
                bestScore = currentBest,
                isGameOver = isGameOver,
                isFullScreen = false,
                isLandscape = isLandscapeLocked,
                restartTrigger = restartTrigger,
                onScoreChange = {
                    score = it
                    updateBest(it)
                },
                onGameOverChange = { isGameOver = it },
                onRestart = {
                    score = 0
                    isGameOver = false
                    restartTrigger++
                },
                onToggleLandscape = { isLandscapeLocked = !isLandscapeLocked },
                onToggleFullScreen = { onToggleFullScreen(true) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(470.dp)
            )
        }
    }
}

@Composable
private fun ArcadeCabinetCard(
    game: OnlineBrowserGame,
    score: Int,
    bestScore: Int,
    isGameOver: Boolean,
    isFullScreen: Boolean,
    isLandscape: Boolean,
    restartTrigger: Int,
    onScoreChange: (Int) -> Unit,
    onGameOverChange: (Boolean) -> Unit,
    onRestart: () -> Unit,
    onToggleLandscape: () -> Unit,
    onToggleFullScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    var playerXNorm by remember(game.id, restartTrigger) { mutableStateOf(0.5f) }
    var moveLeft by remember(game.id, restartTrigger) { mutableStateOf(false) }
    var moveRight by remember(game.id, restartTrigger) { mutableStateOf(false) }
    var boostOrFire by remember(game.id, restartTrigger) { mutableStateOf(false) }
    var roadDashPhase by remember { mutableStateOf(0f) }
    var missedNodes by remember(game.id, restartTrigger) { mutableIntStateOf(0) }

    val obstacles = remember(game.id, restartTrigger) { mutableStateListOf<ObstacleEntity>() }
    val orbs = remember(game.id, restartTrigger) { mutableStateListOf<OrbEntity>() }
    val bullets = remember(game.id, restartTrigger) { mutableStateListOf<BulletEntity>() }
    val enemies = remember(game.id, restartTrigger) { mutableStateListOf<EnemyEntity>() }
    val quantumNodes = remember(game.id, restartTrigger) { mutableStateListOf<QuantumNodeEntity>() }

    // 60FPS Game Loop
    LaunchedEffect(game.id, restartTrigger, isGameOver) {
        if (isGameOver) return@LaunchedEffect
        var frameCount = 0
        var lastNanos = 0L
        while (!isGameOver) {
            withFrameNanos { nanos ->
                val dt = if (lastNanos == 0L) 0.016f else ((nanos - lastNanos) / 1_000_000_000f).coerceIn(0.005f, 0.05f)
                lastNanos = nanos
                frameCount++

                val speedMult = if (boostOrFire) 1.65f else 1f
                if (moveLeft) playerXNorm = (playerXNorm - 0.72f * dt).coerceIn(0.18f, 0.82f)
                if (moveRight) playerXNorm = (playerXNorm + 0.72f * dt).coerceIn(0.18f, 0.82f)

                when (game.id) {
                    "cyber-drift" -> {
                        roadDashPhase = (roadDashPhase + 420f * dt * speedMult) % 80f
                        if (frameCount % 38 == 0) {
                            val lane = listOf(0.28f, 0.5f, 0.72f).random()
                            obstacles.add(ObstacleEntity(lane, -0.08f))
                        }
                        if (frameCount % 55 == 0) {
                            orbs.add(OrbEntity(Random.nextFloat() * 0.56f + 0.22f, -0.06f))
                        }

                        val orbIter = orbs.iterator()
                        while (orbIter.hasNext()) {
                            val o = orbIter.next()
                            o.yNorm += 0.48f * dt * speedMult
                            if (hypot(o.xNorm - playerXNorm, o.yNorm - 0.82f) < 0.085f) {
                                onScoreChange(score + 25)
                                orbIter.remove()
                            } else if (o.yNorm > 1.08f) {
                                orbIter.remove()
                            }
                        }

                        val obsIter = obstacles.iterator()
                        while (obsIter.hasNext()) {
                            val ob = obsIter.next()
                            ob.yNorm += (0.55f + score * 0.0003f) * dt * speedMult
                            if (abs(ob.xNorm - playerXNorm) < 0.085f && abs(ob.yNorm - 0.82f) < 0.075f) {
                                onGameOverChange(true)
                            } else if (ob.yNorm > 0.88f && !ob.passed) {
                                ob.passed = true
                                onScoreChange(score + 10)
                            }
                            if (ob.yNorm > 1.12f) obsIter.remove()
                        }
                    }

                    "void-striker" -> {
                        roadDashPhase = (roadDashPhase + 180f * dt) % 100f
                        if (frameCount % (if (boostOrFire) 8 else 15) == 0) {
                            bullets.add(BulletEntity(playerXNorm - 0.03f, 0.78f))
                            bullets.add(BulletEntity(playerXNorm + 0.03f, 0.78f))
                        }
                        if (frameCount % 44 == 0) {
                            enemies.add(EnemyEntity(Random.nextFloat() * 0.72f + 0.14f, -0.06f, hp = 2))
                        }

                        val bIter = bullets.iterator()
                        while (bIter.hasNext()) {
                            val b = bIter.next()
                            b.yNorm -= 0.95f * dt
                            if (b.yNorm < -0.05f) bIter.remove()
                        }

                        val eIter = enemies.iterator()
                        while (eIter.hasNext()) {
                            val en = eIter.next()
                            en.yNorm += (0.25f + score * 0.0002f) * dt
                            val hitBullet = bullets.firstOrNull { hypot(it.xNorm - en.xNorm, it.yNorm - en.yNorm) < 0.065f }
                            if (hitBullet != null) {
                                bullets.remove(hitBullet)
                                en.hp--
                                if (en.hp <= 0) {
                                    onScoreChange(score + 20)
                                    eIter.remove()
                                    continue
                                }
                            }
                            if (en.yNorm > 0.84f) {
                                onGameOverChange(true)
                            }
                        }
                    }

                    else -> {
                        if (frameCount % 42 == 0) {
                            quantumNodes.add(
                                QuantumNodeEntity(
                                    xNorm = Random.nextFloat() * 0.72f + 0.14f,
                                    yNorm = Random.nextFloat() * 0.65f + 0.18f,
                                    ttl = 1f
                                )
                            )
                        }
                        val nIter = quantumNodes.iterator()
                        while (nIter.hasNext()) {
                            val node = nIter.next()
                            node.ttl -= 0.42f * dt
                            if (node.ttl <= 0f) {
                                nIter.remove()
                                missedNodes++
                                if (missedNodes >= 5) {
                                    onGameOverChange(true)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DeepSpaceDark),
        border = BorderStroke(2.dp, ElectricBlue),
        modifier = modifier.testTag("interactive_arcade_cabinet")
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top Control Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CardSurfaceDark)
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(9.dp)
                                .clip(CircleShape)
                                .background(NeonGreenStatus)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = game.title,
                            style = MaterialTheme.typography.titleMedium,
                            color = TextPrimaryWhite
                        )
                    }
                    Text(
                        text = "SCORE: $score   •   BEST: $bestScore",
                        style = MaterialTheme.typography.labelLarge,
                        color = ElectricBlue
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    OutlinedButton(
                        onClick = onToggleLandscape,
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                        border = BorderStroke(1.dp, if (isLandscape) NeonGreenStatus else ElectricBlue),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = if (isLandscape) NeonGreenStatus else ElectricBlue
                        ),
                        modifier = Modifier.testTag("arcade_landscape_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ScreenRotation,
                            contentDescription = "Toggle Landscape Orientation",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(if (isLandscape) "Portrait" else "Landscape")
                    }

                    OutlinedButton(
                        onClick = onRestart,
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                        border = BorderStroke(1.dp, ElectricBlue),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = ElectricBlue),
                        modifier = Modifier.testTag("arcade_restart_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Restart Game",
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Button(
                        onClick = onToggleFullScreen,
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ElectricBlue,
                            contentColor = ObsidianBlack
                        ),
                        modifier = Modifier.testTag("arcade_fullscreen_button")
                    ) {
                        Icon(
                            imageVector = if (isFullScreen) Icons.Default.FullscreenExit else Icons.Default.Fullscreen,
                            contentDescription = if (isFullScreen) "Exit Full Screen" else "Full Screen",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(if (isFullScreen) "Exit" else "Full")
                    }
                }
            }

            // Interactive Game Canvas
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color(0xFF03060E))
                    .pointerInput(game.id, isGameOver, restartTrigger) {
                        detectTapGestures { tapOffset ->
                            if (isGameOver) {
                                onRestart()
                                return@detectTapGestures
                            }
                            val xNorm = (tapOffset.x / size.width.coerceAtLeast(1)).coerceIn(0.1f, 0.9f)
                            val yNorm = (tapOffset.y / size.height.coerceAtLeast(1)).coerceIn(0.1f, 0.9f)
                            if (game.id == "quantum-pulse") {
                                val hit = quantumNodes.firstOrNull {
                                    hypot(it.xNorm - xNorm, it.yNorm - yNorm) < 0.13f
                                }
                                if (hit != null) {
                                    quantumNodes.remove(hit)
                                    onScoreChange(score + 15)
                                }
                            } else {
                                playerXNorm = xNorm.coerceIn(0.18f, 0.82f)
                            }
                        }
                    }
                    .pointerInput(game.id, isGameOver) {
                        detectDragGestures { change, _ ->
                            change.consume()
                            if (!isGameOver && game.id != "quantum-pulse") {
                                val xNorm = (change.position.x / size.width.coerceAtLeast(1)).coerceIn(0.18f, 0.82f)
                                playerXNorm = xNorm
                            }
                        }
                    }
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height

                    when (game.id) {
                        "cyber-drift" -> {
                            val roadLeft = w * 0.14f
                            val roadRight = w * 0.86f
                            drawRect(
                                brush = Brush.verticalGradient(
                                    listOf(Color(0xFF040B1A), Color(0xFF081632))
                                ),
                                topLeft = Offset(roadLeft, 0f),
                                size = Size(roadRight - roadLeft, h)
                            )
                            drawLine(
                                color = ElectricBlue,
                                start = Offset(roadLeft, 0f),
                                end = Offset(roadLeft, h),
                                strokeWidth = 4f
                            )
                            drawLine(
                                color = ElectricBlue,
                                start = Offset(roadRight, 0f),
                                end = Offset(roadRight, h),
                                strokeWidth = 4f
                            )

                            // Lane markers
                            val lane1 = w * 0.38f
                            val lane2 = w * 0.62f
                            var yCursor = -80f + roadDashPhase
                            while (yCursor < h) {
                                drawLine(
                                    color = ElectricBlue.copy(alpha = 0.35f),
                                    start = Offset(lane1, yCursor),
                                    end = Offset(lane1, yCursor + 36f),
                                    strokeWidth = 3f
                                )
                                drawLine(
                                    color = ElectricBlue.copy(alpha = 0.35f),
                                    start = Offset(lane2, yCursor),
                                    end = Offset(lane2, yCursor + 36f),
                                    strokeWidth = 3f
                                )
                                yCursor += 80f
                            }

                            // Orbs
                            orbs.forEach { orb ->
                                drawCircle(
                                    color = ElectricBlue,
                                    radius = 16f,
                                    center = Offset(orb.xNorm * w, orb.yNorm * h)
                                )
                                drawCircle(
                                    color = Color.White,
                                    radius = 7f,
                                    center = Offset(orb.xNorm * w, orb.yNorm * h)
                                )
                            }

                            // Obstacles
                            obstacles.forEach { ob ->
                                drawRoundRect(
                                    color = CrimsonDanger,
                                    topLeft = Offset(ob.xNorm * w - 42f, ob.yNorm * h - 20f),
                                    size = Size(84f, 40f),
                                    cornerRadius = CornerRadius(10f, 10f)
                                )
                            }

                            // Player Interceptor
                            val px = playerXNorm * w
                            val py = h * 0.82f
                            val shipPath = Path().apply {
                                moveTo(px, py - 36f)
                                lineTo(px + 28f, py + 28f)
                                lineTo(px, py + 14f)
                                lineTo(px - 28f, py + 28f)
                                close()
                            }
                            drawPath(shipPath, color = ElectricBlue)
                        }

                        "void-striker" -> {
                            for (i in 1..6) {
                                val x = (w / 7f) * i
                                drawLine(
                                    color = ElectricBlue.copy(alpha = 0.1f),
                                    start = Offset(x, 0f),
                                    end = Offset(x, h),
                                    strokeWidth = 1.5f
                                )
                            }

                            bullets.forEach { b ->
                                drawRoundRect(
                                    color = ElectricBlue,
                                    topLeft = Offset(b.xNorm * w - 3f, b.yNorm * h - 12f),
                                    size = Size(6f, 24f),
                                    cornerRadius = CornerRadius(3f, 3f)
                                )
                            }

                            enemies.forEach { en ->
                                drawCircle(
                                    color = CrimsonDanger,
                                    radius = 26f,
                                    center = Offset(en.xNorm * w, en.yNorm * h)
                                )
                                drawCircle(
                                    color = Color.White,
                                    radius = 10f,
                                    center = Offset(en.xNorm * w, en.yNorm * h)
                                )
                            }

                            val px = playerXNorm * w
                            val py = h * 0.84f
                            val jetPath = Path().apply {
                                moveTo(px, py - 38f)
                                lineTo(px + 34f, py + 26f)
                                lineTo(px, py + 12f)
                                lineTo(px - 34f, py + 26f)
                                close()
                            }
                            drawPath(jetPath, color = ElectricBlue)
                        }

                        else -> {
                            quantumNodes.forEach { node ->
                                val cx = node.xNorm * w
                                val cy = node.yNorm * h
                                drawCircle(
                                    color = ElectricBlue.copy(alpha = 0.25f),
                                    radius = 34f,
                                    center = Offset(cx, cy)
                                )
                                drawCircle(
                                    color = ElectricBlue,
                                    radius = 58f * node.ttl.coerceAtLeast(0.1f),
                                    center = Offset(cx, cy),
                                    style = Stroke(width = 4f)
                                )
                            }
                        }
                    }
                }

                // HUD Overlay
                Text(
                    text = if (game.id == "quantum-pulse") {
                        "GRID INTEGRITY: ${(5 - missedNodes).coerceAtLeast(0)}/5 • TAP NODES DIRECTLY"
                    } else {
                        game.controlsHint
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondaryMuted,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(10.dp)
                        .background(ObsidianBlack.copy(alpha = 0.72f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                )

                if (isGameOver) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(ObsidianBlack.copy(alpha = 0.86f))
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "SYSTEM OVERLOAD",
                            style = MaterialTheme.typography.headlineLarge,
                            color = ElectricBlue
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Final Score: $score  •  High Score: $bestScore",
                            style = MaterialTheme.typography.titleLarge,
                            color = TextPrimaryWhite
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = onRestart,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ElectricBlue,
                                contentColor = ObsidianBlack
                            )
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("PLAY AGAIN", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Mobile Touch Control Deck
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CardSurfaceDark)
                    .padding(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        playerXNorm = (playerXNorm - 0.11f).coerceIn(0.18f, 0.82f)
                    },
                    border = BorderStroke(1.dp, ElectricBlue),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimaryWhite),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("touch_left_button")
                ) {
                    Text("◀ LEFT", fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { boostOrFire = !boostOrFire },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (boostOrFire) NeonGreenStatus else ElectricBlue,
                        contentColor = ObsidianBlack
                    ),
                    modifier = Modifier
                        .weight(1.3f)
                        .height(48.dp)
                        .testTag("touch_boost_button")
                ) {
                    Text(
                        text = if (boostOrFire) "⚡ OVERDRIVE ON" else "⚡ BOOST / FIRE",
                        fontWeight = FontWeight.Bold
                    )
                }

                OutlinedButton(
                    onClick = {
                        playerXNorm = (playerXNorm + 0.11f).coerceIn(0.18f, 0.82f)
                    },
                    border = BorderStroke(1.dp, ElectricBlue),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimaryWhite),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("touch_right_button")
                ) {
                    Text("RIGHT ▶", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
