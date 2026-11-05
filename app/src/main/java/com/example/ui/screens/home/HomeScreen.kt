package com.example.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ReadingHistoryEntity
import com.example.ui.adaptive.LocalWindowSizeInfo
import com.example.ui.navigation.Screen
import com.example.ui.theme.BentoBorderBlue
import com.example.ui.theme.BentoCyan
import com.example.ui.theme.BentoGold
import com.example.ui.theme.BentoPaleBlue
import com.example.ui.theme.BentoPrimary
import com.example.ui.theme.BentoPrimaryDark
import com.example.ui.theme.BentoRose
import com.example.ui.theme.BentoSoftBlue
import com.example.ui.theme.BentoTeal
import com.example.ui.theme.BentoViolet
import com.example.ui.theme.ColorCategoryBarangBg
import com.example.ui.theme.ColorCategoryBarangBorder
import com.example.ui.theme.ColorCategoryBarangText
import com.example.ui.theme.ColorCategoryEtikaBg
import com.example.ui.theme.ColorCategoryEtikaBorder
import com.example.ui.theme.ColorCategoryEtikaText
import com.example.ui.theme.ColorCategoryIzinBg
import com.example.ui.theme.ColorCategoryIzinBorder
import com.example.ui.theme.ColorCategoryIzinText
import com.example.ui.theme.ColorCategoryKendaraanBg
import com.example.ui.theme.ColorCategoryKendaraanBorder
import com.example.ui.theme.ColorCategoryKendaraanText
import com.example.ui.theme.ColorCategoryRambutBg
import com.example.ui.theme.ColorCategoryRambutBorder
import com.example.ui.theme.ColorCategoryRambutText
import com.example.ui.theme.ColorCategorySeragamBg
import com.example.ui.theme.ColorCategorySeragamBorder
import com.example.ui.theme.ColorCategorySeragamText
import com.example.ui.theme.ColorCategorySopBg
import com.example.ui.theme.ColorCategorySopBorder
import com.example.ui.theme.ColorCategorySopText
import com.example.ui.theme.ColorCategoryWaktuBg
import com.example.ui.theme.ColorCategoryWaktuBorder
import com.example.ui.theme.ColorCategoryWaktuText
import com.example.ui.theme.GradientHeaderBadge
import com.example.ui.theme.GradientPelanggaran
import com.example.ui.theme.GradientTanyaAi
import com.example.viewmodel.TatibViewModel
import java.util.Calendar

data class DigitalQuickFilter(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val targetRoute: String,
    val bg: Color,
    val border: Color,
    val textCol: Color
)

@Composable
fun HomeScreen(
    viewModel: TatibViewModel,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val readingHistory by viewModel.readingHistory.collectAsState()
    val windowInfo = LocalWindowSizeInfo.current

    // Determine current day for daily uniform insight
    val calendar = Calendar.getInstance()
    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    val todayUniformInsight = when (dayOfWeek) {
        Calendar.MONDAY -> "Senin: Baju Putih Lengan Panjang (Putra & Putri), Rok/Celana Putih, Dasi di Bawah Pusar, Topi Pet & Sabuk Hitam. Masuk 06.45 WIB."
        Calendar.TUESDAY -> "Selasa: Baju Putih Lengan Panjang (Putra & Putri Wajib Lengan Panjang), Rok/Celana Abu-abu + Atribut Lengkap. Masuk 06.45 WIB."
        Calendar.WEDNESDAY -> "Rabu: Baju Putih Lengan Pendek (Putra) / Baju Putih Lengan Panjang (Putri), Rok/Celana Abu-abu. Masuk 06.45 WIB."
        Calendar.THURSDAY -> "Kamis: Baju Batik SMANEKA Lengan Pendek (Putra) / Baju Batik Lengan Panjang (Putri), Rok/Celana Putih. Masuk 06.45 WIB."
        Calendar.FRIDAY -> "Jum'at: Baju Pramuka Lengan Pendek (Putra) / Baju Pramuka Lengan Panjang (Putri) + Kaos Kaki Hitam 15 cm. Masuk 07.30 WIB."
        else -> "Standar seragam: Standar panjang dasi di bawah pusar. Selasa putra & putri wajib lengan panjang; Rabu putra lengan pendek putri lengan panjang; Kamis batik & Jumat pramuka lengan pendek putra / lengan panjang putri."
    }

    val quickNavItems = listOf(
        DigitalQuickFilter("Seragam Hari Ini", Icons.Default.School, Screen.Standards.route, ColorCategorySeragamBg, ColorCategorySeragamBorder, ColorCategorySeragamText),
        DigitalQuickFilter("Jam Belajar", Icons.Default.AccessTime, Screen.Pedoman.route, ColorCategoryWaktuBg, ColorCategoryWaktuBorder, ColorCategoryWaktuText),
        DigitalQuickFilter("51 Pelanggaran", Icons.Default.Warning, Screen.Pelanggaran.route, ColorCategoryKendaraanBg, ColorCategoryKendaraanBorder, ColorCategoryKendaraanText),
        DigitalQuickFilter("Tanya AI", Icons.Default.AutoAwesome, Screen.TanyaTatib.route, Color(0xFFEDE9FE), Color(0xFFDDD6FE), Color(0xFF6D28D9)),
        DigitalQuickFilter("Standar Rambut", Icons.Default.ContentCut, Screen.Standards.route, ColorCategoryRambutBg, ColorCategoryRambutBorder, ColorCategoryRambutText),
        DigitalQuickFilter("Kendaraan & Helm", Icons.Default.DirectionsBike, Screen.Pedoman.route, ColorCategoryKendaraanBg, ColorCategoryKendaraanBorder, ColorCategoryKendaraanText),
        DigitalQuickFilter("Aturan HP & Barang", Icons.Default.PhoneAndroid, Screen.Pedoman.route, ColorCategoryBarangBg, ColorCategoryBarangBorder, ColorCategoryBarangText),
        DigitalQuickFilter("Modul Edukasi", Icons.Default.School, Screen.EducationalResources.route, BentoPaleBlue, BentoBorderBlue, BentoPrimary)
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .testTag("home_screen_content"),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        // Section 1: Digital Top Branding & Modern Search Bar
        item {
            BentoHeader(
                onSearchClick = { onNavigate(Screen.Search.route) },
                onBookmarksClick = { onNavigate(Screen.Bookmarks.route) },
                onAboutClick = { onNavigate(Screen.About.route) }
            )
        }

        // Section 2: Quick Digital Navigator Ribbon (Horizontal Scrollable Chips)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 8.dp)
            ) {
                Text(
                    text = "Pintasan Cepat",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF64748B),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(quickNavItems) { item ->
                        Surface(
                            color = item.bg,
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, item.border),
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { onNavigate(item.targetRoute) }
                                .testTag("quick_nav_${item.title.replace(" ", "_")}")
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = null,
                                    tint = item.textCol,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = item.title,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = item.textCol
                                )
                            }
                        }
                    }
                }
            }
        }

        // Section 3: Jadwal & Ketentuan Hari Ini (Clean Notice Banner)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                BentoDailyInsightCard(
                    insightText = todayUniformInsight,
                    onViewDetails = { onNavigate(Screen.Standards.route) }
                )
            }
        }

        // Section 4: 4 Menu Utama (Clean 2x2 Grid)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f, fill = false)) {
                        Text(
                            text = "Navigasi Utama",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp,
                            color = Color(0xFF0F172A)
                        )
                        Text(
                            text = "Pusat pedoman resmi, sanksi, standar & asisten AI",
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 12.sp,
                            color = Color(0xFF64748B),
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                    Surface(
                        color = BentoPaleBlue,
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, BentoBorderBlue)
                    ) {
                        Text(
                            text = "Resmi 2026/2027",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = BentoPrimaryDark,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                if (windowInfo.widthDp >= 720.dp) {
                    // Wide Tablet Layout: 4 Columns in single row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        BentoPrimaryNavCard(
                            title = "Pedoman Tatib",
                            subtitle = "Bab I s.d. VII",
                            tag = "DOKUMEN RESMI",
                            icon = Icons.AutoMirrored.Filled.MenuBook,
                            gradient = Brush.linearGradient(
                                listOf(Color(0xFF0C3068), Color(0xFF194FA0), Color(0xFF2563EB))
                            ),
                            onClick = { onNavigate(Screen.Pedoman.route) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("menu_pedoman")
                        )

                        BentoPrimaryNavCard(
                            title = "51 Pelanggaran",
                            subtitle = "Kategori & Poin",
                            tag = "SOP & SANKSI",
                            icon = Icons.Default.Warning,
                            gradient = Brush.linearGradient(
                                listOf(Color(0xFF991B1B), Color(0xFFDC2626), Color(0xFFEA580C))
                            ),
                            onClick = { onNavigate(Screen.Pelanggaran.route) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("menu_pelanggaran")
                        )

                        BentoPrimaryNavCard(
                            title = "Standar Visual",
                            subtitle = "Seragam & Rambut",
                            tag = "PANDUAN VISUAL",
                            icon = Icons.Default.School,
                            gradient = Brush.linearGradient(
                                listOf(Color(0xFFB45309), Color(0xFFD97706), Color(0xFFF59E0B))
                            ),
                            onClick = { onNavigate(Screen.Standards.route) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("menu_standar_visual")
                        )

                        BentoPrimaryNavCard(
                            title = "Tanya Tatib AI",
                            subtitle = "Asisten Cerdas",
                            tag = "ASISTEN AI",
                            icon = Icons.Default.AutoAwesome,
                            gradient = Brush.linearGradient(
                                listOf(Color(0xFF581C87), Color(0xFF7C3AED), Color(0xFF9333EA))
                            ),
                            onClick = { onNavigate(Screen.TanyaTatib.route) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("menu_tanya_ai")
                        )
                    }
                } else {
                    // Compact Phone Layout: 2x2 Grid
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        BentoPrimaryNavCard(
                            title = "Pedoman Tatib",
                            subtitle = "Bab I s.d. VII & Tata Krama",
                            tag = "DOKUMEN RESMI",
                            icon = Icons.AutoMirrored.Filled.MenuBook,
                            gradient = Brush.linearGradient(
                                listOf(Color(0xFF0C3068), Color(0xFF194FA0), Color(0xFF2563EB))
                            ),
                            onClick = { onNavigate(Screen.Pedoman.route) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("menu_pedoman")
                        )

                        BentoPrimaryNavCard(
                            title = "51 Pelanggaran",
                            subtitle = "Kategori I–III & Poin Sanksi",
                            tag = "SOP & SANKSI",
                            icon = Icons.Default.Warning,
                            gradient = Brush.linearGradient(
                                listOf(Color(0xFF991B1B), Color(0xFFDC2626), Color(0xFFEA580C))
                            ),
                            onClick = { onNavigate(Screen.Pelanggaran.route) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("menu_pelanggaran")
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        BentoPrimaryNavCard(
                            title = "Standar Visual",
                            subtitle = "Seragam, Rambut & Kuku",
                            tag = "PANDUAN VISUAL",
                            icon = Icons.Default.School,
                            gradient = Brush.linearGradient(
                                listOf(Color(0xFFB45309), Color(0xFFD97706), Color(0xFFF59E0B))
                            ),
                            onClick = { onNavigate(Screen.Standards.route) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("menu_standar_visual")
                        )

                        BentoPrimaryNavCard(
                            title = "Tanya Tatib AI",
                            subtitle = "Konsultasi & Sitasi Bab",
                            tag = "ASISTEN AI",
                            icon = Icons.Default.AutoAwesome,
                            gradient = Brush.linearGradient(
                                listOf(Color(0xFF581C87), Color(0xFF7C3AED), Color(0xFF9333EA))
                            ),
                            onClick = { onNavigate(Screen.TanyaTatib.route) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("menu_tanya_ai")
                        )
                    }
                }
            }
        }

        // Section 5: Modul Pembelajaran & Fitur Tambahan
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Akses Modul & Fitur Khusus",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF0F172A),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    BentoQuickChip(
                        title = "Edukasi & Nilai",
                        icon = Icons.Default.School,
                        bg = BentoPaleBlue,
                        border = BentoBorderBlue,
                        textCol = BentoPrimary,
                        onClick = { onNavigate(Screen.EducationalResources.route) },
                        modifier = Modifier.weight(1f)
                    )

                    BentoQuickChip(
                        title = "Mulai Kuis",
                        icon = Icons.Default.Quiz,
                        bg = ColorCategoryWaktuBg,
                        border = ColorCategoryWaktuBorder,
                        textCol = ColorCategoryWaktuText,
                        onClick = { onNavigate(Screen.Quiz.route) },
                        modifier = Modifier.weight(1f)
                    )

                    BentoQuickChip(
                        title = "Simpanan",
                        icon = Icons.Default.Bookmark,
                        bg = ColorCategoryBarangBg,
                        border = ColorCategoryBarangBorder,
                        textCol = ColorCategoryBarangText,
                        onClick = { onNavigate(Screen.Bookmarks.route) },
                        modifier = Modifier.weight(1f)
                    )

                    BentoQuickChip(
                        title = "Pencarian",
                        icon = Icons.Default.Search,
                        bg = Color(0xFFEDE9FE),
                        border = Color(0xFFDDD6FE),
                        textCol = Color(0xFF6D28D9),
                        onClick = { onNavigate(Screen.Search.route) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Section 6: Lanjutkan Membaca (Recent Reading History)
        val lastRead = readingHistory.firstOrNull()
        if (lastRead != null) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 10.dp)
                ) {
                    Text(
                        text = "Lanjutkan Membaca",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF0F172A),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    BentoLastReadCard(
                        lastRead = lastRead,
                        onNavigate = onNavigate
                    )
                }
            }
        }
    }
}

@Composable
fun BentoHeader(
    onSearchClick: () -> Unit,
    onBookmarksClick: () -> Unit,
    onAboutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 6.dp)
    ) {
        // Top Branding Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(GradientHeaderBadge),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "SN",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "SMANEKA",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Black,
                            color = BentoPrimary
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            color = BentoPaleBlue,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "2026/2027",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = BentoPrimaryDark,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                    Text(
                        text = "Pedoman Tata Tertib Siswa Resmi",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF64748B)
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                // Bookmarks
                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .clickable { onBookmarksClick() }
                        .testTag("btn_bookmarks_home")
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Bookmark,
                            contentDescription = "Simpanan",
                            tint = BentoPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Info / About
                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .clickable { onAboutClick() }
                        .testTag("btn_about_home")
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Tentang",
                            tint = BentoPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Instant Digital Search Bar with AI Quick Badge
        Surface(
            color = Color.White,
            shape = RoundedCornerShape(18.dp),
            shadowElevation = 2.dp,
            border = androidx.compose.foundation.BorderStroke(1.2.dp, BentoBorderBlue),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .clickable { onSearchClick() }
                .testTag("home_bento_search_launcher")
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(BentoPaleBlue),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = BentoPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Cari aturan, seragam, poin sanksi, atau pasal...",
                    fontSize = 13.sp,
                    color = Color(0xFF64748B),
                    modifier = Modifier.weight(1f)
                )
                Surface(
                    color = Color(0xFFEDE9FE),
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFDDD6FE))
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = Color(0xFF7C3AED),
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "AI",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF7C3AED)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BentoPrimaryNavCard(
    title: String,
    subtitle: String,
    tag: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    gradient: Brush,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val windowSizeInfo = LocalWindowSizeInfo.current
    val isCompactSmall = windowSizeInfo.isCompactSmall

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.5.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.35f)),
        modifier = modifier
            .heightIn(min = if (isCompactSmall) 148.dp else 160.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(gradient)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Subtle Aesthetic Background Watermark Icon
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.10f),
                modifier = Modifier
                    .size(86.dp)
                    .align(Alignment.BottomEnd)
                    .padding(end = 4.dp, bottom = 4.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top Header Row: Glass Icon Badge + Category Tag
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White.copy(alpha = 0.22f))
                            .border(
                                1.dp,
                                Color.White.copy(alpha = 0.40f),
                                RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Surface(
                        color = Color.White.copy(alpha = 0.20f),
                        shape = RoundedCornerShape(20.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            0.8.dp,
                            Color.White.copy(alpha = 0.40f)
                        )
                    ) {
                        Text(
                            text = tag,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 0.6.sp,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.5.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Bottom Content Row: Title, Subtitle, and Micro Action Indicator
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 6.dp)
                    ) {
                        Text(
                            text = title,
                            fontSize = if (isCompactSmall) 14.sp else 15.5.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White,
                            lineHeight = if (isCompactSmall) 18.sp else 20.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = subtitle,
                            fontSize = if (isCompactSmall) 10.5.sp else 11.5.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White.copy(alpha = 0.90f),
                            lineHeight = if (isCompactSmall) 14.sp else 15.sp,
                            modifier = Modifier.padding(top = 2.5.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    // Micro Action Pill / Arrow
                    Box(
                        modifier = Modifier
                            .size(22.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.22f))
                            .border(0.8.dp, Color.White.copy(alpha = 0.45f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BentoQuickChip(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    bg: Color,
    border: Color,
    textCol: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = bg,
        border = androidx.compose.foundation.BorderStroke(1.dp, border),
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .testTag("quick_chip_${title.replace(" ", "_")}")
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = textCol,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = title,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = textCol,
                maxLines = 1,
                softWrap = false,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun BentoDailyInsightCard(
    insightText: String,
    onViewDetails: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = ColorCategoryBarangBg),
        border = androidx.compose.foundation.BorderStroke(1.dp, ColorCategoryBarangBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("bento_card_daily_insight")
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(BentoGold),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Ketentuan & Seragam Hari Ini",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = ColorCategoryBarangText
                )
                Text(
                    text = insightText,
                    fontSize = 12.sp,
                    color = Color(0xFF0F172A),
                    lineHeight = 17.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Buka Panduan Visual Lengkap →",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = BentoPrimary,
                    modifier = Modifier
                        .clickable { onViewDetails() }
                        .testTag("btn_view_uniform_standards")
                )
            }
        }
    }
}

@Composable
fun BentoLastReadCard(
    lastRead: ReadingHistoryEntity?,
    onNavigate: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .clickable {
                if (lastRead != null) {
                    onNavigate(lastRead.route)
                } else {
                    onNavigate(Screen.Pedoman.route)
                }
            }
            .testTag("bento_card_last_read")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(ColorCategorySeragamBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = null,
                    tint = ColorCategorySeragamText,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = lastRead?.title ?: "BAB II: Hak dan Kewajiban Siswa SMANEKA",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A),
                    maxLines = 1
                )
                Text(
                    text = lastRead?.subtitle ?: "Pedoman Kedisiplinan & Etika Murid",
                    fontSize = 11.sp,
                    color = Color(0xFF64748B),
                    maxLines = 1,
                    modifier = Modifier.padding(top = 1.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Surface(
                color = BentoPrimary,
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = "Buka",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
        }
    }
}

