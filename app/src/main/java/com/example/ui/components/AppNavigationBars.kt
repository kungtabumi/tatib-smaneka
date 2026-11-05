package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.navigation.Screen
import com.example.ui.theme.BentoGold
import com.example.ui.theme.BentoPaleBlue
import com.example.ui.theme.BentoPrimary
import com.example.ui.theme.BentoPrimaryDark
import com.example.ui.theme.ColorCategoryBarangBg
import com.example.ui.theme.ColorCategoryBarangText
import com.example.ui.theme.ColorCategoryKendaraanBg
import com.example.ui.theme.ColorCategoryKendaraanText
import com.example.ui.theme.ColorCategoryRambutBg
import com.example.ui.theme.ColorCategoryRambutText
import com.example.ui.theme.ColorCategorySeragamBg
import com.example.ui.theme.ColorCategorySeragamText
import com.example.ui.theme.ColorCategoryWaktuBg
import com.example.ui.theme.ColorCategoryWaktuText
import com.example.ui.theme.GradientHeaderBadge
import com.example.ui.theme.SmanekaGold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    currentRoute: String,
    onNavigateToSearch: () -> Unit,
    onNavigateToBookmarks: () -> Unit,
    onNavigateToAbout: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isRootTab = currentRoute in listOf(
        Screen.Home.route,
        Screen.Pedoman.route,
        Screen.Pelanggaran.route,
        Screen.Belajar.route
    )

    val screenTitle = when (currentRoute) {
        Screen.Standards.route -> "Standar Visual Tatib"
        Screen.Pedoman.route -> "Pedoman & Ketentuan"
        Screen.Pelanggaran.route -> "51 Bentuk Pelanggaran"
        Screen.Belajar.route -> "Modul Pembelajaran"
        else -> "Tatib SMANEKA"
    }

    val screenSubtitle = when (currentRoute) {
        Screen.Standards.route -> "Seragam, Sepatu, Rambut & Kuku"
        Screen.Pedoman.route -> "BAB 1 s.d. BAB 7 Lengkap"
        Screen.Pelanggaran.route -> "Kategori I, II, III & SOP Sanksi"
        Screen.Belajar.route -> "Edukasi Tata Tertib Siswa"
        else -> "SMAN 1 Kepanjen 2026/2027"
    }

    TopAppBar(
        navigationIcon = {
            if (!isRootTab) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.testTag("app_top_bar_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Kembali",
                        tint = BentoPrimary
                    )
                }
            }
        },
        title = {
            if (isRootTab) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(GradientHeaderBadge),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "SN",
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Tatib SMANEKA",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = BentoPrimary
                        )
                        Text(
                            text = "SMAN 1 Kepanjen 2026/2027",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                Column {
                    Text(
                        text = screenTitle,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = BentoPrimary
                    )
                    Text(
                        text = screenSubtitle,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        actions = {
            if (isRootTab) {
                IconButton(
                    onClick = onNavigateToSearch,
                    modifier = Modifier.testTag("top_bar_search_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Pencarian",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }

                IconButton(
                    onClick = onNavigateToBookmarks,
                    modifier = Modifier.testTag("top_bar_bookmarks_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Bookmark,
                        contentDescription = "Simpanan",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }

                IconButton(
                    onClick = onNavigateToAbout,
                    modifier = Modifier.testTag("top_bar_about_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Tentang",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        ),
        modifier = modifier
            .testTag("app_top_bar")
    )
}

@Composable
fun AppBottomBar(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    data class NavItem(
        val route: String,
        val label: String,
        val icon: androidx.compose.ui.graphics.vector.ImageVector,
        val activeColor: Color,
        val activeBg: Color
    )

    val items = listOf(
        NavItem(Screen.Home.route, "Beranda", Icons.Default.Home, BentoPrimary, BentoPaleBlue),
        NavItem(Screen.Pedoman.route, "Pedoman", Icons.AutoMirrored.Filled.MenuBook, BentoPrimary, BentoPaleBlue),
        NavItem(Screen.Pelanggaran.route, "Pelanggaran", Icons.Default.Warning, ColorCategoryKendaraanText, ColorCategoryKendaraanBg),
        NavItem(Screen.TanyaTatib.route, "Tanya AI", Icons.Default.AutoAwesome, Color(0xFF7C3AED), Color(0xFFEDE9FE)),
        NavItem(Screen.Belajar.route, "Kuis", Icons.Default.Quiz, ColorCategoryWaktuText, ColorCategoryWaktuBg)
    )

    Surface(
        color = Color.White,
        shadowElevation = 8.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 0.dp,
                windowInsets = WindowInsets.navigationBars,
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 840.dp)
                    .testTag("app_bottom_bar")
            ) {
                items.forEach { item ->
                    val isSelected = currentRoute == item.route
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { onNavigate(item.route) },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                modifier = Modifier.size(23.dp)
                            )
                        },
                        label = {
                            Text(
                                text = item.label,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                maxLines = 1,
                                softWrap = false,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = item.activeColor,
                            selectedTextColor = item.activeColor,
                            indicatorColor = item.activeBg,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        modifier = Modifier.testTag("nav_item_${item.route}")
                    )
                }
            }
        }
    }
}

@Composable
fun AppNavigationRail(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    data class NavItem(
        val route: String,
        val label: String,
        val icon: androidx.compose.ui.graphics.vector.ImageVector,
        val activeColor: Color,
        val activeBg: Color
    )

    val items = listOf(
        NavItem(Screen.Home.route, "Beranda", Icons.Default.Home, BentoPrimary, BentoPaleBlue),
        NavItem(Screen.Pedoman.route, "Pedoman", Icons.AutoMirrored.Filled.MenuBook, BentoPrimary, BentoPaleBlue),
        NavItem(Screen.Pelanggaran.route, "Pelanggaran", Icons.Default.Warning, ColorCategoryKendaraanText, ColorCategoryKendaraanBg),
        NavItem(Screen.TanyaTatib.route, "Tanya AI", Icons.Default.AutoAwesome, Color(0xFF7C3AED), Color(0xFFEDE9FE)),
        NavItem(Screen.Belajar.route, "Kuis", Icons.Default.Quiz, ColorCategoryWaktuText, ColorCategoryWaktuBg)
    )

    Surface(
        color = Color.White,
        shadowElevation = 4.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
        modifier = modifier
    ) {
        NavigationRail(
            containerColor = Color.White,
            windowInsets = WindowInsets.safeDrawing,
            header = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = 16.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(GradientHeaderBadge),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "SN",
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 16.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "SMANEKA",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = BentoPrimaryDark,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "Tatib Digital",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            modifier = Modifier.testTag("app_navigation_rail")
        ) {
            Spacer(modifier = Modifier.weight(1f))
            items.forEach { item ->
                val isSelected = currentRoute == item.route
                NavigationRailItem(
                    selected = isSelected,
                    onClick = { onNavigate(item.route) },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = {
                        Text(
                            text = item.label,
                            fontSize = 11.5.sp,
                            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                            maxLines = 1,
                            softWrap = false,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    colors = NavigationRailItemDefaults.colors(
                        selectedIconColor = item.activeColor,
                        selectedTextColor = item.activeColor,
                        indicatorColor = item.activeBg,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .testTag("nav_rail_item_${item.route}")
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            // Footer Quick Actions on Tablet Side Rail
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                IconButton(
                    onClick = { onNavigate(Screen.Search.route) },
                    modifier = Modifier.testTag("rail_search_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Pencarian",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }
                IconButton(
                    onClick = { onNavigate(Screen.Bookmarks.route) },
                    modifier = Modifier.testTag("rail_bookmarks_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Bookmark,
                        contentDescription = "Simpanan",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }
                IconButton(
                    onClick = { onNavigate(Screen.About.route) },
                    modifier = Modifier.testTag("rail_about_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Tentang",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

