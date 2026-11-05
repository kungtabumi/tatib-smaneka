package com.example.ui.screens.resources

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BentoBorderBlue
import com.example.ui.theme.BentoGold
import com.example.ui.theme.BentoPaleBlue
import com.example.ui.theme.BentoPrimary
import com.example.ui.theme.BentoPrimaryDark
import com.example.ui.theme.ColorCategoryBarangBg
import com.example.ui.theme.ColorCategoryBarangBorder
import com.example.ui.theme.ColorCategoryBarangText
import com.example.ui.theme.ColorCategoryEtikaBg
import com.example.ui.theme.ColorCategoryEtikaBorder
import com.example.ui.theme.ColorCategoryEtikaText
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
import com.example.ui.theme.GradientTanyaAi
import com.example.viewmodel.TatibViewModel

data class EduArticle(
    val id: String,
    val title: String,
    val category: String,
    val readTime: String,
    val coreValue: String,
    val summary: String,
    val content: String,
    val keyPoints: List<String>,
    val actionItems: List<String>,
    val relatedChapter: String,
    val icon: ImageVector,
    val bgGradient: Brush
)

data class ConductTip(
    val id: String,
    val title: String,
    val category: String,
    val subtitle: String,
    val tipPoints: List<String>,
    val doAndDont: Pair<String, String>,
    val icon: ImageVector,
    val themeColorBg: Color,
    val themeColorBorder: Color,
    val themeColorText: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EducationalResourcesScreen(
    viewModel: TatibViewModel,
    onNavigate: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Semua") }
    var selectedTab by remember { mutableStateOf(0) } // 0: Artikel & Nilai, 1: Tips Praktis, 2: Refleksi Diri
    var selectedArticleForDialog by remember { mutableStateOf<EduArticle?>(null) }
    var selectedTipForDialog by remember { mutableStateOf<ConductTip?>(null) }

    val categories = listOf("Semua", "Karakter SMANEKA", "Disiplin Waktu", "Etika Digital", "Anti-Bullying", "Keselamatan & Ketertiban")

    val articles = remember { getEduArticles() }
    val tips = remember { getConductTips() }

    val filteredArticles = remember(searchQuery, selectedCategory) {
        articles.filter { article ->
            val matchesCategory = selectedCategory == "Semua" || article.category.equals(selectedCategory, ignoreCase = true)
            val matchesSearch = searchQuery.isBlank() ||
                    article.title.contains(searchQuery, ignoreCase = true) ||
                    article.summary.contains(searchQuery, ignoreCase = true) ||
                    article.coreValue.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }
    }

    val filteredTips = remember(searchQuery, selectedCategory) {
        tips.filter { tip ->
            val matchesCategory = selectedCategory == "Semua" || tip.category.equals(selectedCategory, ignoreCase = true)
            val matchesSearch = searchQuery.isBlank() ||
                    tip.title.contains(searchQuery, ignoreCase = true) ||
                    tip.subtitle.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Edukasi & Nilai Karakter",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = BentoPrimary
                        )
                        Text(
                            text = "Panduan Perilaku Positif Pelajar SMANEKA",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("btn_back_edu_resources")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali",
                            tint = BentoPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.Transparent)
        ) {
            // Search Input
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text(
                            text = "Cari artikel, nilai karakter, atau tips...",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = BentoPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Hapus",
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BentoPrimary,
                        unfocusedBorderColor = BentoBorderBlue,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    ),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_search_edu_resources")
                )
            }

            // Tab Selector
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = BentoPrimary,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = BentoPrimary,
                        height = 3.dp
                    )
                }
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = {
                        Text(
                            text = "Artikel & Nilai (${filteredArticles.size})",
                            fontSize = 13.sp,
                            fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = {
                        Text(
                            text = "Tips Perilaku (${filteredTips.size})",
                            fontSize = 13.sp,
                            fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
                Tab(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    text = {
                        Text(
                            text = "Refleksi Karakter",
                            fontSize = 13.sp,
                            fontWeight = if (selectedTab == 2) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }

            // Category Chips
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
            ) {
                items(categories) { cat ->
                    val isSelected = selectedCategory == cat
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) BentoPrimary else MaterialTheme.colorScheme.surface,
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isSelected) BentoPrimary else BentoBorderBlue
                        ),
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { selectedCategory = cat }
                            .testTag("cat_chip_$cat")
                    ) {
                        Text(
                            text = cat,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            // Content Body
            when (selectedTab) {
                0 -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // Hero Quote Card
                        item {
                            EduHeroBanner()
                        }

                        items(filteredArticles, key = { it.id }) { article ->
                            EduArticleCard(
                                article = article,
                                onClick = { selectedArticleForDialog = article }
                            )
                        }

                        if (filteredArticles.isEmpty()) {
                            item {
                                EmptyEduPlaceholder(message = "Tidak ada artikel yang cocok dengan pencarian.")
                            }
                        }
                    }
                }

                1 -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        item {
                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = ColorCategorySeragamBg,
                                border = androidx.compose.foundation.BorderStroke(1.dp, ColorCategorySeragamBorder),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Lightbulb,
                                        contentDescription = null,
                                        tint = ColorCategorySeragamText,
                                        modifier = Modifier.size(28.dp)
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            text = "Kiat Praktis Sikap & Kebiasaan Positif",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = ColorCategorySeragamText
                                        )
                                        Text(
                                            text = "Langkah konkret yang dapat langsung kamu terapkan setiap hari di SMANEKA.",
                                            fontSize = 11.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }

                        items(filteredTips, key = { it.id }) { tip ->
                            ConductTipCard(
                                tip = tip,
                                onClick = { selectedTipForDialog = tip }
                            )
                        }

                        if (filteredTips.isEmpty()) {
                            item {
                                EmptyEduPlaceholder(message = "Tidak ada tips yang cocok dengan pencarian.")
                            }
                        }
                    }
                }

                2 -> {
                    CharacterReflectionView(
                        onNavigateToRules = { onNavigate("pedoman") }
                    )
                }
            }
        }
    }

    // Dialog for Full Article Reader
    selectedArticleForDialog?.let { article ->
        ArticleDetailDialog(
            article = article,
            onDismiss = { selectedArticleForDialog = null },
            onNavigateToRules = {
                selectedArticleForDialog = null
                onNavigate("pedoman")
            }
        )
    }

    // Dialog for Full Conduct Tip Detail
    selectedTipForDialog?.let { tip ->
        TipDetailDialog(
            tip = tip,
            onDismiss = { selectedTipForDialog = null }
        )
    }
}

@Composable
fun EduHeroBanner() {
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(
                Brush.linearGradient(
                    listOf(Color(0xFF0F3A7D), Color(0xFF1E5BB5), Color(0xFF3B82F6))
                )
            )
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    color = BentoGold,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "FILOSOFI & NILAI SMANEKA",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Icon(
                    imageVector = Icons.Default.Verified,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "“Disiplin bukan sekadar menaati larangan, melainkan bentuk penghormatan tertinggi pada masa depanmu.”",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Nilai BERKIBAR: Beriman, Berkarakter, Berprestasi • Profil Pelajar Pancasila",
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.88f)
            )
        }
    }
}

@Composable
fun EduArticleCard(
    article: EduArticle,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = androidx.compose.foundation.BorderStroke(1.2.dp, BentoBorderBlue),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .testTag("article_card_${article.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(BentoPaleBlue),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = article.icon,
                            contentDescription = null,
                            tint = BentoPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = article.category,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = BentoPrimary
                        )
                        Text(
                            text = "Nilai: ${article.coreValue}",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Surface(
                    color = Color(0xFFF1F5F9),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "⏱️ ${article.readTime}",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = article.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 20.sp
            )

            Text(
                text = article.summary,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 17.sp,
                modifier = Modifier.padding(top = 4.dp, bottom = 10.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Bab Rujukan: ${article.relatedChapter}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BentoPrimaryDark
                )

                Surface(
                    color = BentoPaleBlue,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Baca Lengkap",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = BentoPrimary
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = BentoPrimary,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ConductTipCard(
    tip: ConductTip,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = androidx.compose.foundation.BorderStroke(1.2.dp, tip.themeColorBorder),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .testTag("tip_card_${tip.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(tip.themeColorBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = tip.icon,
                        contentDescription = null,
                        tint = tip.themeColorText,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = tip.title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = tip.subtitle,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Highlight 2 key points
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                tip.tipPoints.take(2).forEach { point ->
                    Row(verticalAlignment = Alignment.Top) {
                        Text("• ", color = tip.themeColorText, fontWeight = FontWeight.Bold)
                        Text(
                            text = point,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 16.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Do vs Don't pill
            Surface(
                color = tip.themeColorBg,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "DO: ${tip.doAndDont.first}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = tip.themeColorText,
                        maxLines = 1,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun CharacterReflectionView(
    onNavigateToRules: () -> Unit
) {
    val reflectionQuestions = listOf(
        "Apakah saya sudah membiasakan budaya 5S (Senyum, Salam, Sapa, Sopan, Santun) kepada seluruh warga sekolah hari ini?",
        "Apakah saya tiba di sekolah tepat waktu (sebelum 06.45 / 07.30 WIB) dan menjaga ketertiban selama KBM?",
        "Apakah saya telah menggunakan smartphone dengan bijak hanya untuk pembelajaran saat diizinkan guru?",
        "Apakah seragam, atribut, dan kerapian rambut saya sudah sepenuhnya sesuai standar resmi SMANEKA?",
        "Apakah saya telah memperlakukan teman dengan rasa hormat dan menolak segala bentuk perundungan?"
    )

    var checkedStates by remember { mutableStateOf(List(reflectionQuestions.size) { false }) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = BentoPaleBlue),
            border = androidx.compose.foundation.BorderStroke(1.2.dp, BentoBorderBlue),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Psychology,
                        contentDescription = null,
                        tint = BentoPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Lembar Refleksi Karakter Harian",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = BentoPrimary
                    )
                }
                Text(
                    text = "Refleksi berkala melatih kesadaran diri dan integritas tanpa perlu selalu diawasi.",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        Text(
            text = "Evaluasi Mandiri Hari Ini:",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        reflectionQuestions.forEachIndexed { index, question ->
            val isChecked = checkedStates.getOrElse(index) { false }
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isChecked) ColorCategoryWaktuBg else MaterialTheme.colorScheme.surface
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isChecked) ColorCategoryWaktuBorder else Color(0xFFE2E8F0)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .clickable {
                        val newStates = checkedStates.toMutableList()
                        newStates[index] = !newStates[index]
                        checkedStates = newStates
                    }
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (isChecked) Icons.Default.CheckCircle else Icons.Default.Verified,
                        contentDescription = null,
                        tint = if (isChecked) ColorCategoryWaktuText else Color(0xFF94A3B8),
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = question,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 17.sp,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        val checkedCount = checkedStates.count { it }
        Surface(
            color = if (checkedCount == reflectionQuestions.size) BentoGold else ColorCategorySeragamBg,
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Skor Refleksi: $checkedCount / ${reflectionQuestions.size} Terpenuhi",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (checkedCount == reflectionQuestions.size) Color.Black else ColorCategorySeragamText
                )
                Text(
                    text = if (checkedCount == reflectionQuestions.size)
                        "Luar biasa! Kamu adalah teladan Pelajar SMANEKA Berkarakter!"
                    else
                        "Pertahankan kebiasaan baik dan terus tingkatkan nilai kedisiplinan dirimu.",
                    fontSize = 11.sp,
                    color = if (checkedCount == reflectionQuestions.size) Color.Black.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        Button(
            onClick = onNavigateToRules,
            colors = ButtonDefaults.buttonColors(containerColor = BentoPrimary),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(imageVector = Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Tinjau Dokumen Pedoman Tatib Lengkap", fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }
    }
}

@Composable
fun ArticleDetailDialog(
    article: EduArticle,
    onDismiss: () -> Unit,
    onNavigateToRules: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.widthIn(max = 560.dp),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    color = BentoPaleBlue,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = article.category,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = BentoPrimary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Text(
                    text = "⏱️ ${article.readTime}",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = article.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 22.sp
                )

                Surface(
                    color = ColorCategoryBarangBg,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Nilai Inti: ${article.coreValue} • Rujukan: ${article.relatedChapter}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = ColorCategoryBarangText,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Text(
                    text = article.content,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 19.sp
                )

                Text(
                    text = "Poin Intisari Karakter:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = BentoPrimary
                )

                article.keyPoints.forEach { pt ->
                    Row(verticalAlignment = Alignment.Top) {
                        Text("✔ ", color = Color(0xFF10B981), fontWeight = FontWeight.Bold)
                        Text(
                            text = pt,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 17.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Aksi Nyata Pelajar SMANEKA:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = BentoPrimaryDark
                )

                article.actionItems.forEach { act ->
                    Row(verticalAlignment = Alignment.Top) {
                        Text("👉 ", fontSize = 12.sp)
                        Text(
                            text = act,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 17.sp
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onNavigateToRules,
                colors = ButtonDefaults.buttonColors(containerColor = BentoPrimary),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Buka Pedoman Resmi", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Tutup", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        },
        shape = RoundedCornerShape(20.dp),
        containerColor = MaterialTheme.colorScheme.surface
    )
}

@Composable
fun TipDetailDialog(
    tip: ConductTip,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.widthIn(max = 560.dp),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(tip.themeColorBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = tip.icon,
                        contentDescription = null,
                        tint = tip.themeColorText,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = tip.title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = tip.category,
                        fontSize = 11.sp,
                        color = tip.themeColorText
                    )
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = tip.subtitle,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 18.sp
                )

                Text(
                    text = "Langkah-Langkah Praktis:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = BentoPrimary
                )

                tip.tipPoints.forEachIndexed { i, pt ->
                    Row(verticalAlignment = Alignment.Top) {
                        Text("${i + 1}. ", fontWeight = FontWeight.Bold, color = tip.themeColorText)
                        Text(
                            text = pt,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 17.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Surface(
                    color = Color(0xFFECFDF5),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFA7F3D0)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text("✅ Hal yang Dianjurkan (DO):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF047857))
                        Text(tip.doAndDont.first, fontSize = 12.sp, color = Color(0xFF065F46), modifier = Modifier.padding(top = 2.dp))
                    }
                }

                Surface(
                    color = Color(0xFFFEF2F2),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFECACA)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text("❌ Hal yang Dilarang (DON'T):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFB91C1C))
                        Text(tip.doAndDont.second, fontSize = 12.sp, color = Color(0xFF991B1B), modifier = Modifier.padding(top = 2.dp))
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = BentoPrimary),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Mengerti", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        },
        shape = RoundedCornerShape(20.dp),
        containerColor = MaterialTheme.colorScheme.surface
    )
}

@Composable
fun EmptyEduPlaceholder(message: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = Color(0xFF94A3B8),
                modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

fun getEduArticles(): List<EduArticle> {
    return listOf(
        EduArticle(
            id = "art-5s",
            title = "Membangun Budaya Positif Melalui 5S (Senyum, Salam, Sapa, Sopan, Santun)",
            category = "Karakter SMANEKA",
            readTime = "3 mnt",
            coreValue = "Rasa Hormat & Keramahan",
            summary = "5S bukan sekadar formalitas, melainkan perekat sosial yang menciptakan atmosfer sekolah yang ramah, damai, dan saling menghargai.",
            content = "Budaya 5S merupakan pilar etika utama di SMAN 1 Kepanjen. Ketika kita membiasakan senyum dan salam kepada Bapak/Ibu guru, staf tata usaha, petugas kebersihan, maupun sesama rekan siswa, kita sedang membangun komunitas yang penuh empati. Sopan dalam bertutur kata dan santun dalam berperilaku mencerminkan kematangan pribadi seorang pelajar terdidik.",
            keyPoints = listOf(
                "Senyum dan salam dengan tulus kepada guru, karyawan, dan tamu sekolah.",
                "Menggunakan bahasa yang santun (krama inggil/bahasa Indonesia yang baik).",
                "Menghormati hak orang lain dan mengedepankan musyawarah mufakat."
            ),
            actionItems = listOf(
                "Ucapkan salam saat berpapasan dengan guru di lorong kelas.",
                "Ucapkan terima kasih setelah menerima bantuan atau bimbingan.",
                "Tunjukkan gestur menghormati saat melintas di depan orang yang lebih tua."
            ),
            relatedChapter = "BAB II: Hak & Kewajiban Siswa (Pasal 3 & 4)",
            icon = Icons.Default.Favorite,
            bgGradient = Brush.linearGradient(listOf(Color(0xFF0F3A7D), Color(0xFF1E5BB5)))
        ),
        EduArticle(
            id = "art-integritas",
            title = "Integritas Akademik: Mengapa Kejujuran Lebih Bernilai daripada Angka",
            category = "Karakter SMANEKA",
            readTime = "4 mnt",
            coreValue = "Kejujuran & Tanggung Jawab",
            summary = "Mencontek dan plagiarisme adalah jalan pintas yang merusak karakter. Nilai sejati seorang pelajar diukur dari proses dan kejujurannya.",
            content = "SMANEKA menjunjung tinggi nilai kejujuran akademik. Ujian, asesmen, dan tugas mandiri adalah cermin evaluasi belajar nyata. Pelanggaran kejujuran seperti mencontek, menggunakan joki, atau menyalin tugas teman secara instan menghilangkan kesempatan belajar yang berharga. Sekolah memberikan apresiasi tertinggi pada kerja keras yang jujur.",
            keyPoints = listOf(
                "Kejujuran adalah pondasi utama kepemimpinan masa depan.",
                "Tugas dan asesmen dikerjakan secara mandiri dengan referensi yang benar.",
                "Menolak segala bentuk kecurangan dalam penilaian harian maupun ujian sumatif."
            ),
            actionItems = listOf(
                "Belajar terencana jauh hari sebelum jadwal asesmen.",
                "Jangan segan bertanya pada guru bila ada materi yang belum dipahami.",
                "Bangga pada hasil jerih payah karya sendiri."
            ),
            relatedChapter = "BAB II Pasal 4 & BAB VI Pelanggaran Akademik",
            icon = Icons.Default.Verified,
            bgGradient = Brush.linearGradient(listOf(Color(0xFF059669), Color(0xFF10B981)))
        ),
        EduArticle(
            id = "art-waktu",
            title = "Seni Manajemen Waktu: Kunci Sukses Pelajar Berprestasi",
            category = "Disiplin Waktu",
            readTime = "3 mnt",
            coreValue = "Kedisiplinan & Ketepatan",
            summary = "Tiba sebelum 06.45 WIB bukan beban aturan, melainkan latihan membangun mentalitas tepat waktu yang sangat dibutuhkan di perguruan tinggi dan dunia kerja.",
            content = "Keterlambatan seringkali berawal dari manajemen malam hari yang kurang teratur. Dengan tidur cukup dan menyiapkan seragam serta perlengkapan sekolah pada malam sebelumnya, siswa dapat mengawali pagi dengan tenang. Ketenangan di pagi hari terbukti meningkatkan fokus kognitif selama jam pelajaran pertama.",
            keyPoints = listOf(
                "Masuk sekolah pukul 06.45 WIB (Senin–Kamis) dan 07.30 WIB (Jum'at).",
                "Waktu pagi yang tenang mempersiapkan mental belajar yang optimal.",
                "Disiplin waktu melatih ketahanan menghadapi target masa depan."
            ),
            actionItems = listOf(
                "Pasang alarm 1 jam sebelum jadwal berangkat.",
                "Siapkan seragam dan atribut lengkap di malam hari.",
                "Alokasikan jeda waktu 15 menit untuk antisipasi lalu lintas perjalanan."
            ),
            relatedChapter = "BAB II Pasal 4 & BAB IV Waktu dan Kehadiran",
            icon = Icons.Default.Speed,
            bgGradient = Brush.linearGradient(listOf(Color(0xFFD97706), Color(0xFFF59E0B)))
        ),
        EduArticle(
            id = "art-digital",
            title = "Etika Digital: Menjaga Jejak & Kehormatan di Media Sosial",
            category = "Etika Digital",
            readTime = "4 mnt",
            coreValue = "Kebijaksanaan & Jejak Positif",
            summary = "Dunia maya adalah ruang publik. Setiap postingan, komentar, dan pesan instan merefleksikan karakter pribadi dan almamater sekolah.",
            content = "Kemajuan teknologi membuka peluang belajar tanpa batas, namun juga menuntut kedewasaan digital. Berkomunikasi dengan guru melalui pesan WhatsApp harus diawali salam, identitas diri, dan bahasa yang santun pada jam kerja yang wajar. Hindari menyebarkan foto atau video yang merendahkan martabat orang lain.",
            keyPoints = listOf(
                "Gunakan smartphone di kelas hanya untuk kepentingan KBM atas izin guru.",
                "Etika chat guru: Salam, perkenalkan nama & kelas, sampaikan maksud dengan sopan.",
                "Jaga rekam jejak digital dari konten negatif, hoaks, dan perundungan siber."
            ),
            actionItems = listOf(
                "Simpan HP dalam tas/tempat khusus saat jam pelajaran berlangsung.",
                "Pikirkan dampak sebelum membagikan postingan di media sosial.",
                "Gunakan internet sekolah untuk riset dan pengembangan potensi diri."
            ),
            relatedChapter = "BAB V: Larangan Penggunaan Gadget Tanpa Izin",
            icon = Icons.Default.Smartphone,
            bgGradient = Brush.linearGradient(listOf(Color(0xFF7C3AED), Color(0xFFA855F7)))
        ),
        EduArticle(
            id = "art-bullying",
            title = "Menciptakan Sekolah Ramah Anak: Menolak Segala Bentuk Perundungan",
            category = "Anti-Bullying",
            readTime = "4 mnt",
            coreValue = "Empati & Perlindungan",
            summary = "Bullying dalam bentuk fisik, verbal, relasional, maupun online tidak memiliki tempat di SMANEKA. Jadilah teman yang saling menguatkan.",
            content = "Candaan yang membuat orang lain merasa sakit hati, terkucilkan, atau takut bukanlah lelucon, melainkan perundungan. SMANEKA berkomitmen mewujudkan lingkungan belajar yang aman dan inklusif. Siswa diajak untuk menjadi 'Upstander' (pembela yang berani bersuara) bukan 'Bystander' (penonton diam) ketika melihat ketidakadilan.",
            keyPoints = listOf(
                "Menghargai perbedaan suku, agama, fisik, dan latar belakang sesama siswa.",
                "Berani melapor kepada Guru BK, Wali Kelas, atau Tim Tatib jika melihat perundungan.",
                "Penyelesaian konflik melalui pendekatan Restorative Justice dan pembinaan."
            ),
            actionItems = listOf(
                "Rangkul teman yang terlihat menyendiri atau kesulitan.",
                "Tolak ikut serta menertawakan candaan yang menyinggung fisik/keluarga.",
                "Konsultasikan masalahmu dengan Guru BK secara aman dan rahasia."
            ),
            relatedChapter = "BAB II Pasal 3, BAB V Larangan, & BAB VI Sanksi Berat",
            icon = Icons.Default.Shield,
            bgGradient = Brush.linearGradient(listOf(Color(0xFFE11D48), Color(0xFFFB7185)))
        ),
        EduArticle(
            id = "art-lingkungan",
            title = "Gerakan LISA: Kepedulian Lingkungan & Menjaga Fasilitas Sekolah",
            category = "Keselamatan & Ketertiban",
            readTime = "3 mnt",
            coreValue = "Tanggung Jawab Lingkungan",
            summary = "Budaya Lihat Sampah Ambil (LISA) dan menjaga sarana prasarana sekolah adalah bukti nyata kecintaan pada kampus SMANEKA yang asri.",
            content = "Kebersihan kelas, laboratorium, perpustakaan, dan toilet sekolah adalah tanggung jawab bersama seluruh warga sekolah. Menjaga sarana dari coretan vandalisme dan memilah sampah plastik menjaga kelestarian lingkungan sekolah Adiwiyata SMANEKA.",
            keyPoints = listOf(
                "Menerapkan budaya LISA di seluruh area sekolah.",
                "Membuang sampah sesuai jenis tempat sampah yang disediakan.",
                "Merawat fasilitas belajar, meja kursi, dan peralatan praktikum."
            ),
            actionItems = listOf(
                "Bawa botol minum (tumbler) guna ulang untuk kurangi sampah plastik.",
                "Pungut sampah yang berserakan di depan kelas tanpa menunggu piket.",
                "Matikan lampu dan kipas angin kelas saat selesai KBM."
            ),
            relatedChapter = "BAB II Pasal 4 & BAB V Ketertiban Sarana",
            icon = Icons.Default.School,
            bgGradient = Brush.linearGradient(listOf(Color(0xFF047857), Color(0xFF10B981)))
        )
    )
}

fun getConductTips(): List<ConductTip> {
    return listOf(
        ConductTip(
            id = "tip-pagi",
            title = "Strategi Bebas Terlambat 06.45 WIB",
            category = "Disiplin Waktu",
            subtitle = "Kiat bangun segar dan tiba di sekolah tanpa terburu-buru.",
            tipPoints = listOf(
                "Hindari begadang melebihi pukul 22.00 WIB di hari sekolah.",
                "Siapkan seragam, dasi, topi, dan tas buku pada malam sebelumnya.",
                "Berangkat minimal 20 menit lebih awal untuk mengantisipasi macet."
            ),
            doAndDont = Pair("Tiba pukul 06.30 WIB untuk membaca atau bersosialisasi santai", "Menunda bangun pagi hingga menit-menit kritis gerbang ditutup"),
            icon = Icons.Default.Speed,
            themeColorBg = ColorCategoryWaktuBg,
            themeColorBorder = ColorCategoryWaktuBorder,
            themeColorText = ColorCategoryWaktuText
        ),
        ConductTip(
            id = "tip-wa-guru",
            title = "Etika Kirim Pesan (WhatsApp) ke Guru",
            category = "Etika Digital",
            subtitle = "Format santun berkomunikasi formal dengan pendidik.",
            tipPoints = listOf(
                "Kirim pesan pada jam kerja yang wajar (pukul 07.00 – 17.00 WIB).",
                "Awali dengan salam: 'Selamat pagi/siang Bapak/Ibu...'",
                "Sebutkan identitas: 'Saya [Nama Lengkap], kelas [X/XI/XII]...'",
                "Sampaikan keperluan dengan singkat, jelas, dan santun, akhiri dengan terima kasih."
            ),
            doAndDont = Pair("Gunakan bahasa Indonesia baku dan sopan", "Memulai chat hanya dengan 'P', 'Pak', atau singkatan tak jelas"),
            icon = Icons.Default.Smartphone,
            themeColorBg = ColorCategoryBarangBg,
            themeColorBorder = ColorCategoryBarangBorder,
            themeColorText = ColorCategoryBarangText
        ),
        ConductTip(
            id = "tip-seragam",
            title = "Kerapian Seragam & Standar Visual",
            category = "Karakter SMANEKA",
            subtitle = "Menjaga martabat pelajar lewat pakaian yang rapi dan sesuai ketentuan.",
            tipPoints = listOf(
                "Siswi: Wajib seragam lengan panjang setiap hari (Senin–Jum'at).",
                "Selasa: Putra & Putri WAJIB lengan panjang; Rabu: Putra lengan pendek / Putri lengan panjang; Kamis Batik & Jumat Pramuka: Putra lengan pendek / Putri lengan panjang.",
                "Dasi: Standar panjang dasi wajib di bawah pusar dan wajib dipakai setiap saat di sekolah.",
                "Putra: Baju dimasukkan rapi dengan sabuk hitam polos dan celana standar (tidak ketat/pensil).",
                "Sepatu hitam bertali polos dan kaos kaki putih 15 cm di atas mata kaki (Jum'at hitam)."
            ),
            doAndDont = Pair("Kenakan atribut badge dan lokasi lengkap yang terjahit rapi", "Memodifikasi seragam menjadi ketat atau memotong rok di atas mata kaki"),
            icon = Icons.Default.School,
            themeColorBg = ColorCategorySeragamBg,
            themeColorBorder = ColorCategorySeragamBorder,
            themeColorText = ColorCategorySeragamText
        ),
        ConductTip(
            id = "tip-rambut",
            title = "Standar Rambut 2-2-1 & Kerapian Diri",
            category = "Karakter SMANEKA",
            subtitle = "Tampil rapi dan percaya diri tanpa melanggar aturan.",
            tipPoints = listOf(
                "Siswa Putra: Ukuran rambut maksimal 2 cm atas, 2 cm samping, 1 cm belakang.",
                "Rambut tidak menyentuh alis, telinga, maupun kerah baju.",
                "Tidak disemir warna, tidak memakai tato/tato temporer, dan kuku dipotong bersih."
            ),
            doAndDont = Pair("Cukur rambut secara berkala setiap 2-3 minggu sekali", "Mencukur model punk, undercut ekstrem, atau mengecat rambut"),
            icon = Icons.Default.Star,
            themeColorBg = ColorCategoryRambutBg,
            themeColorBorder = ColorCategoryRambutBorder,
            themeColorText = ColorCategoryRambutText
        ),
        ConductTip(
            id = "tip-kendaraan",
            title = "Keselamatan Berkendara & Parkir Resmi",
            category = "Keselamatan & Ketertiban",
            subtitle = "Tertib di jalan raya demi keselamatan diri dan sesama.",
            tipPoints = listOf(
                "Wajib memakai helm standar SNI terkancing rapat.",
                "Sepeda motor wajib knalpot standar pabrik (dilarang keras knalpot brong).",
                "Spion lengkap dua sisi dan plat nomor terpasang rapi.",
                "Parkir tertib di zona parkir dalam sekolah yang telah ditentukan."
            ),
            doAndDont = Pair("Mengemudi dengan kecepatan rendah dan sopan di area gerbang sekolah", "Melakukan aksi kebut-kebutan atau bonceng tiga"),
            icon = Icons.Default.Security,
            themeColorBg = ColorCategoryKendaraanBg,
            themeColorBorder = ColorCategoryKendaraanBorder,
            themeColorText = ColorCategoryKendaraanText
        ),
        ConductTip(
            id = "tip-stop-bullying",
            title = "Langkah Cerdas Atasi & Cegah Bullying",
            category = "Anti-Bullying",
            subtitle = "Menciptakan ruang belajar yang aman bagi setiap siswa.",
            tipPoints = listOf(
                "Katakan dengan tegas 'Stop' bila ada perlakuan yang tidak menyenangkan.",
                "Jangan ragu bercerita kepada sahabat, wali kelas, atau guru BK.",
                "Jika melihat teman dirundung, dekati korban dan laporkan pada guru."
            ),
            doAndDont = Pair("Menjadi pelindung dan teman yang suportif bagi sesama", "Ikut menyebarkan gosip atau mempermalukan teman di grup kelas"),
            icon = Icons.Default.Shield,
            themeColorBg = ColorCategorySopBg,
            themeColorBorder = ColorCategorySopBorder,
            themeColorText = ColorCategorySopText
        )
    )
}
