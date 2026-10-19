package com.example.ui.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.net.Uri
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ScreenRotation
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.data.DownloadableGame
import com.example.data.GameCategory
import com.example.data.PlayfinityRepository
import com.example.data.ShowcaseGroup
import com.example.ui.theme.BorderNeonSubtle
import com.example.ui.theme.CardSurfaceDark
import com.example.ui.theme.CardSurfaceElevated
import com.example.ui.theme.CrimsonDanger
import com.example.ui.theme.CyberBlueAccent
import com.example.ui.theme.DeepSpaceDark
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.NeonGreenStatus
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.TextPrimaryWhite
import com.example.ui.theme.TextSecondaryMuted
import com.example.util.NetworkUtils

private const val LOCAL_WEB_PORTAL_URL = "file:///android_asset/web/index.html"
private const val LOCAL_ASSET_PREFIX = "file:///android_asset/web/"

@Composable
fun HomeScreen(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onNavigateToDownloads: (GameCategory?) -> Unit,
    onNavigateToPlayOnline: (String) -> Unit,
    onNavigateToCategories: () -> Unit,
    onOpenLiveWebPortal: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedShowcase by rememberSaveable { mutableStateOf(ShowcaseGroup.FEATURED) }
    val allGames = PlayfinityRepository.downloadableGames
    val onlineGames = PlayfinityRepository.onlineBrowserGames

    val filteredBySearch = remember(searchQuery, allGames) {
        val q = searchQuery.trim().lowercase()
        if (q.isEmpty()) emptyList()
        else allGames.filter {
            it.title.lowercase().contains(q) ||
                it.category.displayName.lowercase().contains(q) ||
                it.description.lowercase().contains(q)
        }
    }

    val showcaseGames = remember(selectedShowcase, allGames) {
        allGames.filter { selectedShowcase in it.showcases }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack),
        contentPadding = PaddingValues(bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp)
    ) {
        // 1. HERO SECTION ("ENTER THE WORLD OF GAMING")
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFF071636), ObsidianBlack)
                        )
                    )
                    .padding(horizontal = 16.dp, vertical = 22.dp)
            ) {
                Canvas(
                    modifier = Modifier.matchParentSize()
                ) {
                    val step = 56.dp.toPx()
                    var x = 0f
                    while (x < size.width) {
                        drawLine(
                            color = ElectricBlue.copy(alpha = 0.07f),
                            start = Offset(x, 0f),
                            end = Offset(x, size.height),
                            strokeWidth = 1f
                        )
                        x += step
                    }
                    var y = 0f
                    while (y < size.height) {
                        drawLine(
                            color = ElectricBlue.copy(alpha = 0.07f),
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = 1f
                        )
                        y += step
                    }
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(ElectricBlue.copy(alpha = 0.18f), Color.Transparent)
                        ),
                        radius = size.width * 0.55f,
                        center = Offset(size.width * 0.5f, size.height * 0.25f)
                    )
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        color = ElectricBlue.copy(alpha = 0.14f),
                        shape = RoundedCornerShape(50),
                        border = BorderStroke(1.dp, ElectricBlue.copy(alpha = 0.6f))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.FlashOn,
                                contentDescription = null,
                                tint = ElectricBlue,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "PLAYFINITY GAMES PLATFORM",
                                style = MaterialTheme.typography.labelLarge,
                                color = ElectricBlue
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "ENTER THE WORLD OF GAMING",
                        style = MaterialTheme.typography.displayMedium,
                        color = TextPrimaryWhite,
                        modifier = Modifier.testTag("hero_heading_text")
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Play original mobile HTML5 browser games instantly and download verified official games across Action, Horror, Arcade, Racing, Adventure, and Offline play.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextSecondaryMuted
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = onSearchQueryChange,
                        placeholder = {
                            Text(
                                "Search games by name, genre, or tag…",
                                color = TextSecondaryMuted
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Games",
                                tint = ElectricBlue
                            )
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { onSearchQueryChange("") }) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "Clear search",
                                        tint = TextSecondaryMuted
                                    )
                                }
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = ElectricBlue,
                            unfocusedBorderColor = BorderNeonSubtle,
                            focusedContainerColor = CardSurfaceDark,
                            unfocusedContainerColor = CardSurfaceDark,
                            focusedTextColor = TextPrimaryWhite,
                            unfocusedTextColor = TextPrimaryWhite
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("home_search_input")
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { onNavigateToPlayOnline("cyber-drift") },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ElectricBlue,
                                contentColor = ObsidianBlack
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .testTag("hero_play_online_btn")
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("PLAY ONLINE", fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = { onNavigateToDownloads(null) },
                            border = BorderStroke(1.dp, ElectricBlue),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = ElectricBlue),
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .testTag("hero_downloads_btn")
                        ) {
                            Icon(Icons.Default.Download, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("DOWNLOADS", fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedButton(
                        onClick = onOpenLiveWebPortal,
                        border = BorderStroke(1.dp, CyberBlueAccent.copy(alpha = 0.6f)),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimaryWhite),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("hero_launch_web_portal_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Language,
                            contentDescription = null,
                            tint = ElectricBlue
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Launch Embedded HTML5 Website (WebView)")
                    }
                }
            }
        }

        if (searchQuery.isNotBlank()) {
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        text = "SEARCH RESULTS (${filteredBySearch.size})",
                        style = MaterialTheme.typography.headlineMedium,
                        color = ElectricBlue
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    if (filteredBySearch.isEmpty()) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = CardSurfaceDark),
                            border = BorderStroke(1.dp, BorderNeonSubtle),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "No games matched \"$searchQuery\"",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = TextPrimaryWhite
                                )
                                Text(
                                    text = "Try searching for Shadow Fight, Asphalt, Horror, Racing, or Offline.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextSecondaryMuted
                                )
                            }
                        }
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            filteredBySearch.forEach { game ->
                                OfficialGameCard(
                                    game = game,
                                    onDownloadClick = { openOfficialUrl(context, game.officialUrl) }
                                )
                            }
                        }
                    }
                }
            }
        }

        // 2. FEATURED, LATEST RELEASES & POPULAR GAMES SHOWCASE
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = "CURATED COLLECTIONS",
                    style = MaterialTheme.typography.labelLarge,
                    color = ElectricBlue
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = selectedShowcase.label.uppercase(),
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextPrimaryWhite
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ShowcaseGroup.entries.forEach { group ->
                        val active = selectedShowcase == group
                        FilterChip(
                            selected = active,
                            onClick = { selectedShowcase = group },
                            label = { Text(group.label) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = ElectricBlue,
                                selectedLabelColor = ObsidianBlack,
                                containerColor = CardSurfaceDark,
                                labelColor = TextPrimaryWhite
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = active,
                                borderColor = BorderNeonSubtle,
                                selectedBorderColor = ElectricBlue
                            ),
                            modifier = Modifier.testTag("showcase_tab_${group.name.lowercase()}")
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    showcaseGames.forEach { game ->
                        OfficialGameCard(
                            game = game,
                            onDownloadClick = { openOfficialUrl(context, game.officialUrl) }
                        )
                    }
                }
            }
        }

        // 3. INSTANT HTML5 ONLINE GAMES SPOTLIGHT
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "NO INSTALL REQUIRED",
                            style = MaterialTheme.typography.labelLarge,
                            color = ElectricBlue
                        )
                        Text(
                            text = "PLAY ONLINE ORIGINALS",
                            style = MaterialTheme.typography.headlineMedium,
                            color = TextPrimaryWhite
                        )
                    }
                    OutlinedButton(
                        onClick = { onNavigateToPlayOnline("cyber-drift") },
                        border = BorderStroke(1.dp, ElectricBlue),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = ElectricBlue)
                    ) {
                        Text("View Arcade")
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(onlineGames, key = { it.id }) { onlineGame ->
                        Card(
                            onClick = { onNavigateToPlayOnline(onlineGame.id) },
                            colors = CardDefaults.cardColors(containerColor = CardSurfaceDark),
                            border = BorderStroke(1.dp, onlineGame.accentColor),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.width(260.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = onlineGame.genre.uppercase(),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = onlineGame.accentColor
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = onlineGame.title,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = TextPrimaryWhite
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = onlineGame.description,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextSecondaryMuted,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Button(
                                    onClick = { onNavigateToPlayOnline(onlineGame.id) },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = ElectricBlue,
                                        contentColor = ObsidianBlack
                                    ),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(Icons.Default.PlayArrow, contentDescription = null)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("PLAY NOW", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }

        // 4. QUICK BROWSE BY CATEGORY STRIP
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "BROWSE 6 CATEGORIES",
                        style = MaterialTheme.typography.headlineMedium,
                        color = TextPrimaryWhite
                    )
                    Text(
                        text = "See All →",
                        style = MaterialTheme.typography.titleMedium,
                        color = ElectricBlue,
                        modifier = Modifier
                            .clickable { onNavigateToCategories() }
                            .padding(8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(GameCategory.entries) { cat ->
                        Surface(
                            onClick = { onNavigateToDownloads(cat) },
                            color = CardSurfaceDark,
                            shape = RoundedCornerShape(14.dp),
                            border = BorderStroke(1.dp, cat.accentColor.copy(alpha = 0.7f))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = categoryIcon(cat),
                                    contentDescription = null,
                                    tint = cat.accentColor,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = cat.displayName,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = TextPrimaryWhite
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DownloadGamesScreen(
    selectedCategory: GameCategory?,
    onSelectCategory: (GameCategory?) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val allGames = PlayfinityRepository.downloadableGames

    val filteredGames = remember(selectedCategory, searchQuery, allGames) {
        val q = searchQuery.trim().lowercase()
        allGames.filter { game ->
            val catMatch = selectedCategory == null || game.category == selectedCategory
            val queryMatch = q.isEmpty() ||
                game.title.lowercase().contains(q) ||
                game.category.displayName.lowercase().contains(q) ||
                game.description.lowercase().contains(q) ||
                game.developer.lowercase().contains(q)
            catMatch && queryMatch
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column {
                Text(
                    text = "OFFICIAL & VERIFIED REPOSITORY",
                    style = MaterialTheme.typography.labelLarge,
                    color = ElectricBlue
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "DOWNLOAD GAMES",
                    style = MaterialTheme.typography.headlineLarge,
                    color = TextPrimaryWhite
                )
                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    color = ElectricBlue.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, ElectricBlue.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = "Verified Official Links",
                            tint = NeonGreenStatus,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "100% Official Store Links: Every download button opens the verified Google Play Store or F-Droid page. Never fake links.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextPrimaryWhite
                        )
                    }
                }
            }
        }

        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = { Text("Filter download games by name or keyword…", color = TextSecondaryMuted) },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null, tint = ElectricBlue)
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { onSearchQueryChange("") }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear", tint = TextSecondaryMuted)
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ElectricBlue,
                    unfocusedBorderColor = BorderNeonSubtle,
                    focusedContainerColor = CardSurfaceDark,
                    unfocusedContainerColor = CardSurfaceDark,
                    focusedTextColor = TextPrimaryWhite,
                    unfocusedTextColor = TextPrimaryWhite
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("download_search_input")
            )
        }

        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                item {
                    val isAll = selectedCategory == null
                    FilterChip(
                        selected = isAll,
                        onClick = { onSelectCategory(null) },
                        label = { Text("All (${allGames.size})") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ElectricBlue,
                            selectedLabelColor = ObsidianBlack,
                            containerColor = CardSurfaceDark,
                            labelColor = TextPrimaryWhite
                        ),
                        modifier = Modifier.testTag("filter_chip_all")
                    )
                }
                items(GameCategory.entries) { category ->
                    val selected = selectedCategory == category
                    FilterChip(
                        selected = selected,
                        onClick = { onSelectCategory(category) },
                        label = { Text(category.displayName) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ElectricBlue,
                            selectedLabelColor = ObsidianBlack,
                            containerColor = CardSurfaceDark,
                            labelColor = TextPrimaryWhite
                        ),
                        modifier = Modifier.testTag("filter_chip_${category.name.lowercase()}")
                    )
                }
            }
        }

        if (filteredGames.isEmpty()) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = CardSurfaceDark),
                    border = BorderStroke(1.dp, BorderNeonSubtle),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No games match your current filter",
                            style = MaterialTheme.typography.titleLarge,
                            color = TextPrimaryWhite
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                onSelectCategory(null)
                                onSearchQueryChange("")
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ElectricBlue,
                                contentColor = ObsidianBlack
                            )
                        ) {
                            Text("Reset Filters")
                        }
                    }
                }
            }
        } else {
            items(filteredGames, key = { it.id }) { game ->
                OfficialGameCard(
                    game = game,
                    onDownloadClick = { openOfficialUrl(context, game.officialUrl) }
                )
            }
        }
    }
}

@Composable
fun OfficialGameCard(
    game: DownloadableGame,
    onDownloadClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = CardSurfaceDark),
        border = BorderStroke(1.dp, BorderNeonSubtle),
        modifier = modifier
            .fillMaxWidth()
            .testTag("game_card_${game.id}")
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(132.dp)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(game.primaryColor, game.secondaryColor)
                        )
                    )
                    .padding(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = ObsidianBlack.copy(alpha = 0.78f),
                        shape = RoundedCornerShape(50),
                        border = BorderStroke(1.dp, game.category.accentColor)
                    ) {
                        Text(
                            text = game.category.displayName.uppercase(),
                            style = MaterialTheme.typography.labelMedium,
                            color = game.category.accentColor,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    Surface(
                        color = ObsidianBlack.copy(alpha = 0.78f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = NeonGreenStatus,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = game.officialStoreName,
                                style = MaterialTheme.typography.labelMedium,
                                color = NeonGreenStatus
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text(
                            text = game.developer.uppercase(),
                            style = MaterialTheme.typography.labelMedium,
                            color = TextPrimaryWhite.copy(alpha = 0.8f)
                        )
                        Text(
                            text = game.title,
                            style = MaterialTheme.typography.titleLarge,
                            color = TextPrimaryWhite
                        )
                    }

                    Surface(
                        color = ObsidianBlack.copy(alpha = 0.65f),
                        shape = CircleShape,
                        border = BorderStroke(1.dp, ElectricBlue.copy(alpha = 0.6f))
                    ) {
                        Icon(
                            imageVector = categoryIcon(game.category),
                            contentDescription = null,
                            tint = ElectricBlue,
                            modifier = Modifier
                                .padding(10.dp)
                                .size(24.dp)
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = game.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondaryMuted
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Size: ${game.sizeLabel}  •  Rating: ${game.ratingLabel}",
                        style = MaterialTheme.typography.labelMedium,
                        color = ElectricBlue
                    )

                    Button(
                        onClick = onDownloadClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ElectricBlue,
                            contentColor = ObsidianBlack
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("download_btn_${game.id}")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("OFFICIAL DOWNLOAD", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun CategoriesScreen(
    onSelectCategoryForDownloads: (GameCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    val allGames = PlayfinityRepository.downloadableGames

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "BROWSE BY GENRE",
                style = MaterialTheme.typography.labelLarge,
                color = ElectricBlue
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "GAME CATEGORIES",
                style = MaterialTheme.typography.headlineLarge,
                color = TextPrimaryWhite
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Select any category below to view verified official downloads tailored to your playstyle.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondaryMuted
            )
        }

        items(GameCategory.entries) { category ->
            val categoryGames = allGames.filter { it.category == category }
            Card(
                onClick = { onSelectCategoryForDownloads(category) },
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = CardSurfaceDark),
                border = BorderStroke(1.dp, category.accentColor.copy(alpha = 0.65f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("category_card_${category.name.lowercase()}")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = category.accentColor.copy(alpha = 0.16f),
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(1.dp, category.accentColor)
                    ) {
                        Icon(
                            imageVector = categoryIcon(category),
                            contentDescription = null,
                            tint = category.accentColor,
                            modifier = Modifier
                                .padding(14.dp)
                                .size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = category.displayName,
                                style = MaterialTheme.typography.titleLarge,
                                color = TextPrimaryWhite
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                color = category.accentColor.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(50)
                            ) {
                                Text(
                                    text = "${categoryGames.size} Games",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = category.accentColor,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = category.tagline,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondaryMuted
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Includes: " + categoryGames.joinToString(", ") { it.title },
                            style = MaterialTheme.typography.labelMedium,
                            color = ElectricBlue
                        )
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Explore ${category.displayName}",
                        tint = category.accentColor
                    )
                }
            }
        }
    }
}

@Composable
fun AboutAndContactScreen(
    onOpenLiveWebPortal: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var gamerName by rememberSaveable { mutableStateOf("") }
    var selectedTopic by rememberSaveable { mutableStateOf("Request Official Game") }
    var messageBody by rememberSaveable { mutableStateOf("") }
    var feedbackBanner by rememberSaveable { mutableStateOf("") }

    var selectedSourceFile by rememberSaveable { mutableStateOf("index.html") }
    val sourceContent = remember(selectedSourceFile) {
        readWebAssetFile(context, selectedSourceFile)
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // 1. ABOUT US
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = CardSurfaceDark),
                border = BorderStroke(1.dp, ElectricBlue.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "ABOUT US",
                        style = MaterialTheme.typography.labelLarge,
                        color = ElectricBlue
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "PLAYFINITY GAMES",
                        style = MaterialTheme.typography.headlineMedium,
                        color = TextPrimaryWhite
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "PLAYFINITY GAMES delivers both a native Android application and a standalone, mobile-responsive HTML5/CSS3/JS website. We combine original instant-play HTML5 games with a verified catalog of official store downloads.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextSecondaryMuted
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(color = BorderNeonSubtle)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "✔ Zero fake download buttons — verified Google Play & F-Droid links only\n✔ Original touch-ready HTML5 games with Full-Screen & Landscape mode\n✔ Secure WebView integration (JavaScript restricted to local game assets)\n✔ Complete index.html, style.css & script.js ready for GitHub Pages",
                        style = MaterialTheme.typography.bodyMedium,
                        color = ElectricBlue
                    )
                }
            }
        }

        // 2. APK BUILD & ANDROID STUDIO GUIDE
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = CardSurfaceElevated),
                border = BorderStroke(1.dp, ElectricBlue.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Build, contentDescription = null, tint = ElectricBlue)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "BUILD & LOCATE DEBUG APK",
                            style = MaterialTheme.typography.headlineMedium,
                            color = TextPrimaryWhite
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Option A — Direct Export in AI Studio:\nOpen the Settings / Export menu in AI Studio to download the compiled APK directly to your Android phone or export the project ZIP.\n\nOption B — Android Studio / Gradle CLI:\n1. Open the project folder in Android Studio.\n2. Select Build → Build Bundle(s) / APK(s) → Build APK(s) (or run 'gradle assembleDebug').\n3. Locate your installable APK at:\napp/build/outputs/apk/debug/app-debug.apk",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondaryMuted
                    )
                }
            }
        }

        // 3. GITHUB PAGES ANDROID MOBILE SETUP INSTRUCTIONS
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = CardSurfaceElevated),
                border = BorderStroke(1.dp, BorderNeonSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "FREE WEBSITE HOSTING GUIDE",
                        style = MaterialTheme.typography.labelLarge,
                        color = ElectricBlue
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "PUBLISH ON GITHUB PAGES FROM ANDROID",
                        style = MaterialTheme.typography.headlineMedium,
                        color = TextPrimaryWhite
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    PlayfinityRepository.androidGithubSetupSteps.forEach { (stepTitle, stepDesc) ->
                        Column(modifier = Modifier.padding(vertical = 6.dp)) {
                            Text(
                                text = stepTitle,
                                style = MaterialTheme.typography.titleMedium,
                                color = ElectricBlue
                            )
                            Text(
                                text = stepDesc,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondaryMuted
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = onOpenLiveWebPortal,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ElectricBlue,
                            contentColor = ObsidianBlack
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("about_launch_web_portal_btn")
                    ) {
                        Icon(Icons.Default.Language, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("LAUNCH EMBEDDED HTML5 WEBSITE", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // 4. COMPLETE SOURCE CODE VIEWER & EXPORTER (index.html, style.css, script.js)
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = CardSurfaceDark),
                border = BorderStroke(1.dp, BorderNeonSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Code, contentDescription = null, tint = ElectricBlue)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "WEBSITE SOURCE CODE KIT",
                            style = MaterialTheme.typography.headlineMedium,
                            color = TextPrimaryWhite
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Inspect, copy, or share the standalone index.html, style.css, and script.js files (located in /web/ and app/src/main/assets/web/).",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondaryMuted
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("index.html", "style.css", "script.js").forEach { fileName ->
                            val selected = selectedSourceFile == fileName
                            FilterChip(
                                selected = selected,
                                onClick = { selectedSourceFile = fileName },
                                label = { Text(fileName) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = ElectricBlue,
                                    selectedLabelColor = ObsidianBlack,
                                    containerColor = DeepSpaceDark,
                                    labelColor = TextPrimaryWhite
                                ),
                                modifier = Modifier.testTag("source_tab_$fileName")
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = {
                                copyTextToClipboard(context, selectedSourceFile, sourceContent)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ElectricBlue,
                                contentColor = ObsidianBlack
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("copy_source_btn")
                        ) {
                            Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Copy $selectedSourceFile", fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = {
                                shareSourceFileText(context, selectedSourceFile, sourceContent)
                            },
                            border = BorderStroke(1.dp, ElectricBlue),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = ElectricBlue),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("share_source_btn")
                        ) {
                            Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Export / Share")
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        color = ObsidianBlack,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, BorderNeonSubtle),
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 160.dp, max = 280.dp)
                    ) {
                        LazyColumn(modifier = Modifier.padding(12.dp)) {
                            item {
                                Text(
                                    text = sourceContent,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 12.sp,
                                    lineHeight = 17.sp,
                                    color = CyberBlueAccent
                                )
                            }
                        }
                    }
                }
            }
        }

        // 5. CONTACT US FORM
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = CardSurfaceDark),
                border = BorderStroke(1.dp, BorderNeonSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "GET IN TOUCH",
                        style = MaterialTheme.typography.labelLarge,
                        color = ElectricBlue
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "CONTACT US",
                        style = MaterialTheme.typography.headlineMedium,
                        color = TextPrimaryWhite
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Suggest an official game link, submit your HTML5 browser game, or send feedback to the PLAYFINITY GAMES team.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondaryMuted
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = gamerName,
                        onValueChange = { gamerName = it },
                        label = { Text("Gamer Tag / Name") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = ElectricBlue,
                            unfocusedBorderColor = BorderNeonSubtle,
                            focusedTextColor = TextPrimaryWhite,
                            unfocusedTextColor = TextPrimaryWhite
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("contact_name_input")
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(listOf("Request Official Game", "Submit HTML5 Game", "General Feedback")) { topic ->
                            FilterChip(
                                selected = selectedTopic == topic,
                                onClick = { selectedTopic = topic },
                                label = { Text(topic) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = ElectricBlue,
                                    selectedLabelColor = ObsidianBlack,
                                    containerColor = DeepSpaceDark,
                                    labelColor = TextPrimaryWhite
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = messageBody,
                        onValueChange = { messageBody = it },
                        label = { Text("Your Message") },
                        minLines = 3,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = ElectricBlue,
                            unfocusedBorderColor = BorderNeonSubtle,
                            focusedTextColor = TextPrimaryWhite,
                            unfocusedTextColor = TextPrimaryWhite
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("contact_message_input")
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            if (gamerName.isBlank() || messageBody.isBlank()) {
                                feedbackBanner = "Please enter your gamer tag and a message before sending."
                            } else {
                                feedbackBanner = "✔ Message received, ${gamerName.trim()}! Topic: $selectedTopic."
                                gamerName = ""
                                messageBody = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ElectricBlue,
                            contentColor = ObsidianBlack
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("contact_submit_button")
                    ) {
                        Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("SEND MESSAGE", fontWeight = FontWeight.Bold)
                    }

                    if (feedbackBanner.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = feedbackBanner,
                            style = MaterialTheme.typography.bodyMedium,
                            color = NeonGreenStatus,
                            modifier = Modifier.testTag("contact_feedback_text")
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun LiveWebPortalOverlay(
    onClose: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as? Activity
    var webViewRef by remember { mutableStateOf<WebView?>(null) }
    var loadErrorDescription by remember { mutableStateOf<String?>(null) }
    var isLandscape by rememberSaveable { mutableStateOf(false) }

    DisposableEffect(isLandscape) {
        if (isLandscape) {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        } else {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
        onDispose {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    // Support Android back navigation inside WebView history before closing overlay
    BackHandler {
        val wv = webViewRef
        if (wv != null && wv.canGoBack()) {
            wv.goBack()
        } else {
            isLandscape = false
            onClose()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBlack)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(CardSurfaceDark)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Language,
                    contentDescription = null,
                    tint = ElectricBlue,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "PLAYFINITY GAMES — WEBVIEW",
                        style = MaterialTheme.typography.labelLarge,
                        color = TextPrimaryWhite
                    )
                    Text(
                        text = "Local HTML5/JS Engine (JS scoped to local assets)",
                        style = MaterialTheme.typography.bodyMedium,
                        color = ElectricBlue,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                OutlinedButton(
                    onClick = { isLandscape = !isLandscape },
                    border = BorderStroke(1.dp, if (isLandscape) NeonGreenStatus else ElectricBlue),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = if (isLandscape) NeonGreenStatus else ElectricBlue
                    ),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("webview_landscape_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.ScreenRotation,
                        contentDescription = "Rotate Screen",
                        modifier = Modifier.size(16.dp)
                    )
                }

                OutlinedButton(
                    onClick = {
                        isLandscape = false
                        onClose()
                    },
                    border = BorderStroke(1.dp, ElectricBlue),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = ElectricBlue),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("close_web_portal_button")
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Close Web View", modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Exit")
                }
            }
        }

        if (loadErrorDescription != null) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = CardSurfaceDark),
                    border = BorderStroke(1.dp, CrimsonDanger),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.ErrorOutline,
                            contentDescription = null,
                            tint = CrimsonDanger,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Unable to Load Content",
                            style = MaterialTheme.typography.titleLarge,
                            color = TextPrimaryWhite
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = loadErrorDescription ?: "Check your connection or reload local game assets.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondaryMuted
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                loadErrorDescription = null
                                webViewRef?.loadUrl(LOCAL_WEB_PORTAL_URL)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ElectricBlue,
                                contentColor = ObsidianBlack
                            )
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Retry Local Game Portal", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        } else {
            AndroidView(
                factory = { ctx ->
                    WebView(ctx).apply {
                        webViewRef = this
                        // Enable JavaScript strictly for local bundled game content
                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true
                        settings.allowFileAccess = true
                        settings.allowContentAccess = false
                        @Suppress("DEPRECATION")
                        settings.allowFileAccessFromFileURLs = false
                        @Suppress("DEPRECATION")
                        settings.allowUniversalAccessFromFileURLs = false

                        webViewClient = object : WebViewClient() {
                            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                                // Enforce JavaScript only for local game assets
                                val isLocalGameContent = url != null && url.startsWith(LOCAL_ASSET_PREFIX)
                                view?.settings?.javaScriptEnabled = isLocalGameContent
                                super.onPageStarted(view, url, favicon)
                            }

                            override fun shouldOverrideUrlLoading(
                                view: WebView?,
                                request: WebResourceRequest?
                            ): Boolean {
                                val targetUrl = request?.url?.toString() ?: return false
                                // Allow local bundled HTML5 files inside WebView
                                if (targetUrl.startsWith(LOCAL_ASSET_PREFIX)) {
                                    view?.settings?.javaScriptEnabled = true
                                    return false
                                }
                                // Route external store links safely outside WebView with connectivity check
                                if (targetUrl.startsWith("https://") || targetUrl.startsWith("http://")) {
                                    openOfficialUrl(context, targetUrl)
                                    return true
                                }
                                return true
                            }

                            override fun onReceivedError(
                                view: WebView?,
                                request: WebResourceRequest?,
                                error: WebResourceError?
                            ) {
                                if (request?.isForMainFrame == true) {
                                    loadErrorDescription = error?.description?.toString()
                                        ?: "Error loading page."
                                }
                            }
                        }
                        loadUrl(LOCAL_WEB_PORTAL_URL)
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
        }
    }
}

private fun categoryIcon(category: GameCategory): ImageVector = when (category) {
    GameCategory.ACTION -> Icons.Default.FlashOn
    GameCategory.HORROR -> Icons.Default.Shield
    GameCategory.ARCADE -> Icons.Default.SportsEsports
    GameCategory.RACING -> Icons.Default.Star
    GameCategory.ADVENTURE -> Icons.Default.Explore
    GameCategory.OFFLINE -> Icons.Default.WifiOff
}

fun openOfficialUrl(context: Context, url: String) {
    if (!NetworkUtils.isInternetAvailable(context)) {
        Toast.makeText(
            context,
            "You are offline. Connect to the internet to open official store downloads.",
            Toast.LENGTH_LONG
        ).show()
        return
    }
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Opening official link: $url", Toast.LENGTH_SHORT).show()
    }
}

private fun readWebAssetFile(context: Context, fileName: String): String {
    return try {
        context.assets.open("web/$fileName").bufferedReader().use { it.readText() }
    } catch (e: Exception) {
        "// Unable to load web/$fileName"
    }
}

private fun copyTextToClipboard(context: Context, label: String, content: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
    clipboard?.setPrimaryClip(ClipData.newPlainText(label, content))
    Toast.makeText(context, "Copied $label to clipboard!", Toast.LENGTH_SHORT).show()
}

private fun shareSourceFileText(context: Context, fileName: String, content: String) {
    try {
        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "PLAYFINITY GAMES - $fileName")
            putExtra(Intent.EXTRA_TEXT, content)
        }
        context.startActivity(Intent.createChooser(sendIntent, "Export $fileName"))
    } catch (e: Exception) {
        Toast.makeText(context, "Copied $fileName", Toast.LENGTH_SHORT).show()
    }
}
