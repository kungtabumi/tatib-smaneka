package com.example.ui.screens.pelanggaran

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.HandlingProcedureEntity
import com.example.data.model.ViolationEntity
import com.example.ui.components.SeverityChip
import com.example.ui.theme.BentoAmber
import com.example.ui.theme.BentoBorderBlue
import com.example.ui.theme.BentoEmerald
import com.example.ui.theme.BentoPaleBlue
import com.example.ui.theme.BentoPrimary
import com.example.ui.theme.BentoPrimaryDark
import com.example.ui.theme.BentoRose
import com.example.ui.theme.BentoSoftBlue
import com.example.ui.theme.ColorCategoryBarangBg
import com.example.ui.theme.ColorCategoryBarangBorder
import com.example.ui.theme.ColorCategoryBarangText
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
import com.example.ui.theme.GradientPelanggaran
import com.example.ui.theme.SeverityHeavyColor
import com.example.ui.theme.SeverityLightColor
import com.example.ui.theme.SeverityMediumColor
import com.example.ui.theme.SmanekaGold
import com.example.viewmodel.TatibViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PelanggaranScreen(
    viewModel: TatibViewModel,
    modifier: Modifier = Modifier
) {
    val violations by viewModel.filteredViolations.collectAsState()
    val allProcedures by viewModel.allProcedures.collectAsState()
    val bookmarks by viewModel.bookmarks.collectAsState()
    val selectedTier by viewModel.selectedViolationTier.collectAsState()
    var activeSearchQuery by remember { mutableStateOf("") }
    var selectedViolationForDetail by remember { mutableStateOf<ViolationEntity?>(null) }
    var isShowingSopTab by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()

    val tabs = listOf(
        "Semua (51)",
        "Pelanggaran I (29)",
        "Pelanggaran II (6)",
        "Pelanggaran III (16)",
        "SOP Alur Sanksi"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .testTag("pelanggaran_screen_content")
    ) {
        // Search bar
        OutlinedTextField(
            value = activeSearchQuery,
            onValueChange = {
                activeSearchQuery = it
                viewModel.setSearchQuery(it)
            },
            placeholder = { Text("Cari pelanggaran (rokok, hp, terlambat, knalpot...)", fontSize = 13.sp, color = Color(0xFF64748B)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = BentoPrimary
                )
            },
            trailingIcon = {
                if (activeSearchQuery.isNotEmpty()) {
                    IconButton(onClick = {
                        activeSearchQuery = ""
                        viewModel.setSearchQuery("")
                    }) {
                        Icon(Icons.Default.Close, contentDescription = "Clear", tint = Color(0xFF64748B))
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = BentoPrimary,
                unfocusedBorderColor = Color(0xFFE2E8F0)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .testTag("pelanggaran_search_field")
        )

        // Bento Filter Tabs
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
        ) {
            items(tabs) { tabName ->
                val isSelected = when (tabName) {
                    "Semua (51)" -> !isShowingSopTab && selectedTier == 0
                    "Pelanggaran I (29)" -> !isShowingSopTab && selectedTier == 1
                    "Pelanggaran II (6)" -> !isShowingSopTab && selectedTier == 2
                    "Pelanggaran III (16)" -> !isShowingSopTab && selectedTier == 3
                    "SOP Alur Sanksi" -> isShowingSopTab
                    else -> false
                }

                FilterChip(
                    selected = isSelected,
                    onClick = {
                        when (tabName) {
                            "Semua (51)" -> {
                                isShowingSopTab = false
                                viewModel.setSelectedViolationTier(0)
                            }
                            "Pelanggaran I (29)" -> {
                                isShowingSopTab = false
                                viewModel.setSelectedViolationTier(1)
                            }
                            "Pelanggaran II (6)" -> {
                                isShowingSopTab = false
                                viewModel.setSelectedViolationTier(2)
                            }
                            "Pelanggaran III (16)" -> {
                                isShowingSopTab = false
                                viewModel.setSelectedViolationTier(3)
                            }
                            "SOP Alur Sanksi" -> {
                                isShowingSopTab = true
                            }
                        }
                    },
                    label = {
                        Text(
                            text = tabName,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = BentoPrimary,
                        selectedLabelColor = Color.White,
                        containerColor = MaterialTheme.colorScheme.surface,
                        labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = isSelected,
                        borderColor = Color(0xFFE2E8F0),
                        selectedBorderColor = BentoPrimary
                    ),
                    modifier = Modifier.testTag("tab_${tabName.replace(" ", "_")}")
                )
            }
        }

        // Content Area
        if (isShowingSopTab) {
            SopProceduresList(allProcedures)
        } else {
            if (violations.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(BentoPaleBlue),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                tint = BentoPrimary,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Tidak ada bentuk pelanggaran yang sesuai pencarian.",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(violations, key = { it.id }) { viol ->
                        val isBookmarked = bookmarks.any { it.id == viol.id }
                        ViolationItemCard(
                            violation = viol,
                            isBookmarked = isBookmarked,
                            onToggleBookmark = {
                                viewModel.toggleBookmark(
                                    id = viol.id,
                                    itemType = "VIOLATION",
                                    title = viol.name,
                                    subtitle = "${viol.tier} No. ${viol.number}",
                                    category = viol.tier
                                )
                            },
                            onViewDetail = {
                                selectedViolationForDetail = viol
                                viewModel.recordReading(
                                    id = viol.id,
                                    title = viol.name,
                                    subtitle = "${viol.tier} No. ${viol.number}",
                                    category = viol.tier,
                                    route = "pelanggaran"
                                )
                            }
                        )
                    }
                }
            }
        }
    }

    // Modal Bottom Sheet for Violation Detail
    if (selectedViolationForDetail != null) {
        val v = selectedViolationForDetail!!
        ModalBottomSheet(
            onDismissRequest = { selectedViolationForDetail = null },
            sheetState = bottomSheetState,
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SeverityChip(
                        tierNumber = v.tierNumber,
                        label = "${v.tier} • No. ${v.number}"
                    )

                    Surface(
                        color = Color(0xFFF1F5F9),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Halaman ${v.pageNumber}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = v.name,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Konsekuensi dan Penanganan Box
                Text(
                    text = "Konsekuensi dan Penanganan:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = BentoPrimary
                )

                Surface(
                    color = BentoPaleBlue,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .border(1.dp, Color(0xFFBFDBFE), RoundedCornerShape(14.dp))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = v.consequence,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF0F2B5C),
                            lineHeight = 21.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Sumber Rujukan: ${v.sourceChapter}",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ViolationItemCard(
    violation: ViolationEntity,
    isBookmarked: Boolean,
    onToggleBookmark: () -> Unit,
    onViewDetail: () -> Unit
) {
    val (tierBg, tierBorder, tierText) = when (violation.tierNumber) {
        1 -> Triple(ColorCategorySeragamBg, ColorCategorySeragamBorder, ColorCategorySeragamText)
        2 -> Triple(ColorCategoryBarangBg, ColorCategoryBarangBorder, ColorCategoryBarangText)
        3 -> Triple(ColorCategoryKendaraanBg, ColorCategoryKendaraanBorder, ColorCategoryKendaraanText)
        else -> Triple(BentoPaleBlue, BentoBorderBlue, BentoPrimary)
    }

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = androidx.compose.foundation.BorderStroke(1.2.dp, tierBorder),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("violation_card_${violation.id}")
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SeverityChip(
                    tierNumber = violation.tierNumber,
                    label = "${violation.tier} • No. ${violation.number}"
                )

                IconButton(
                    onClick = onToggleBookmark,
                    modifier = Modifier
                        .size(32.dp)
                        .testTag("btn_bookmark_violation_${violation.id}")
                ) {
                    Icon(
                        imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Simpan",
                        tint = if (isBookmarked) SmanekaGold else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Violation Title
            Text(
                text = violation.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Consequence preview
            Text(
                text = violation.consequence,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 18.sp,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Footer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Hal. ${violation.pageNumber} • Dokumen Resmi",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Surface(
                    color = tierBg,
                    border = androidx.compose.foundation.BorderStroke(1.dp, tierBorder),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { onViewDetail() }
                        .testTag("btn_detail_${violation.id}")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = "Detail Sanksi",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = tierText
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = tierText,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
            }
        }
    }
}

private data class SopTheme(
    val gradient: Brush,
    val cardBorder: Color,
    val badgeBg: Color,
    val badgeText: Color,
    val badgeBorder: Color,
    val stepBg: Color,
    val stepBorder: Color,
    val stepText: Color,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val badgeTitle: String,
    val severity: String
)

@Composable
fun SopProceduresList(procedures: List<HandlingProcedureEntity>) {
    // Helper to accurately classify procedure tier (checking III, then II, then I to avoid substring overlap)
    val getProcedureTier: (HandlingProcedureEntity) -> Int = { proc ->
        when {
            proc.id == "SOP_3" ||
            proc.severityLevel.equals("Berat", ignoreCase = true) ||
            proc.categoryName.contains("Pelanggaran III", ignoreCase = true) ||
            proc.categoryName.contains("Pelanggaran 3", ignoreCase = true) ||
            proc.categoryName.contains("Sanksi Berat", ignoreCase = true) -> 3

            proc.id == "SOP_2" ||
            proc.severityLevel.equals("Sedang", ignoreCase = true) ||
            proc.categoryName.contains("Pelanggaran II", ignoreCase = true) ||
            proc.categoryName.contains("Pelanggaran 2", ignoreCase = true) ||
            proc.categoryName.contains("Sanksi Sedang", ignoreCase = true) -> 2

            proc.id == "SOP_1" ||
            proc.severityLevel.equals("Ringan", ignoreCase = true) ||
            proc.categoryName.contains("Pelanggaran I", ignoreCase = true) ||
            proc.categoryName.contains("Pelanggaran 1", ignoreCase = true) ||
            proc.categoryName.contains("Sanksi Ringan", ignoreCase = true) -> 1

            else -> 1
        }
    }

    // Explicitly sort procedures strictly from Pelanggaran 1 (Ringan) to Pelanggaran 3 (Berat)
    val sortedProcedures = remember(procedures) {
        procedures.sortedBy { getProcedureTier(it) }
    }

    LazyColumn(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.testTag("sop_procedures_list")
    ) {
        // Hero SOP Header Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFF0F172A), Color(0xFF1E293B), Color(0xFF334155))
                        )
                    )
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Security,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text(
                                text = "SOP ALUR SANKSI & PENANGANAN",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                            Text(
                                text = "Urutan Penanganan Bertahap: Tingkat 1 → Tingkat 2 → Tingkat 3",
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White.copy(alpha = 0.88f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // 3-Stage Visual Pipeline Indicator
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Stage 1 Indicator
                        Surface(
                            color = Color(0xFF059669).copy(alpha = 0.25f),
                            shape = RoundedCornerShape(10.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF10B981).copy(alpha = 0.6f)),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 6.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "1. RINGAN",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFF6EE7B7)
                                )
                                Text(
                                    text = "Teguran & Bina",
                                    fontSize = 9.sp,
                                    color = Color.White.copy(alpha = 0.85f),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        // Stage 2 Indicator
                        Surface(
                            color = Color(0xFFD97706).copy(alpha = 0.25f),
                            shape = RoundedCornerShape(10.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF59E0B).copy(alpha = 0.6f)),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 6.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "2. SEDANG",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFFFCD34D)
                                )
                                Text(
                                    text = "Panggilan Ortu",
                                    fontSize = 9.sp,
                                    color = Color.White.copy(alpha = 0.85f),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        // Stage 3 Indicator
                        Surface(
                            color = Color(0xFFE11D48).copy(alpha = 0.25f),
                            shape = RoundedCornerShape(10.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFB7185).copy(alpha = 0.6f)),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 6.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "3. BERAT",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFFFDA4AF)
                                )
                                Text(
                                    text = "Konferensi Kasus",
                                    fontSize = 9.sp,
                                    color = Color.White.copy(alpha = 0.85f),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }

        // List of Cards with Distinct Color Theme per Tier
        items(sortedProcedures, key = { it.id }) { proc ->
            val procTier = getProcedureTier(proc)

            // Distinct Color Tokens for Each Tier
            val theme = when (procTier) {
                1 -> SopTheme(
                    gradient = Brush.horizontalGradient(listOf(Color(0xFF047857), Color(0xFF10B981))),
                    cardBorder = Color(0xFF86EFAC),
                    badgeBg = Color(0xFFDCFCE7),
                    badgeText = Color(0xFF065F46),
                    badgeBorder = Color(0xFF86EFAC),
                    stepBg = Color(0xFFF0FDF4),
                    stepBorder = Color(0xFFBBF7D0),
                    stepText = Color(0xFF14532D),
                    icon = Icons.Default.CheckCircle,
                    badgeTitle = "ALUR PENANGANAN 1",
                    severity = "SANKSI RINGAN"
                )
                2 -> SopTheme(
                    gradient = Brush.horizontalGradient(listOf(Color(0xFFB45309), Color(0xFFF59E0B))),
                    cardBorder = Color(0xFFFDE68A),
                    badgeBg = Color(0xFFFEF3C7),
                    badgeText = Color(0xFF92400E),
                    badgeBorder = Color(0xFFFDE68A),
                    stepBg = Color(0xFFFFFBEB),
                    stepBorder = Color(0xFFFDE68A),
                    stepText = Color(0xFF78350F),
                    icon = Icons.Default.Warning,
                    badgeTitle = "ALUR PENANGANAN 2",
                    severity = "SANKSI SEDANG"
                )
                else -> SopTheme(
                    gradient = Brush.horizontalGradient(listOf(Color(0xFF991B1B), Color(0xFFDC2626))),
                    cardBorder = Color(0xFFFECDD3),
                    badgeBg = Color(0xFFFFE4E6),
                    badgeText = Color(0xFF9F1239),
                    badgeBorder = Color(0xFFFDA4AF),
                    stepBg = Color(0xFFFFF1F2),
                    stepBorder = Color(0xFFFECDD3),
                    stepText = Color(0xFF881337),
                    icon = Icons.Default.Gavel,
                    badgeTitle = "ALUR PENANGANAN 3",
                    severity = "SANKSI BERAT"
                )
            }

            val categoryTitleText = when (procTier) {
                1 -> "KATEGORI PELANGGARAN I (Sanksi Ringan)"
                2 -> "KATEGORI PELANGGARAN II (Sanksi Sedang)"
                3 -> "KATEGORI PELANGGARAN III (Sanksi Berat)"
                else -> proc.categoryName
            }

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.5.dp),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, theme.cardBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("sop_card_${proc.id}")
            ) {
                Column {
                    // Card Top Accent Header Bar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(theme.gradient)
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(26.dp)
                                        .clip(CircleShape)
                                        .background(Color.White.copy(alpha = 0.25f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = theme.icon,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "${theme.badgeTitle} • ${theme.severity}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White,
                                    letterSpacing = 0.3.sp
                                )
                            }

                            Surface(
                                color = Color.White.copy(alpha = 0.25f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "Hal. ${proc.pageNumber}",
                                    fontSize = 10.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }
                    }

                    // Card Main Body
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = categoryTitleText,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = BentoPrimaryDark
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                        ) {
                            Surface(
                                color = theme.badgeBg,
                                shape = RoundedCornerShape(6.dp),
                                border = androidx.compose.foundation.BorderStroke(0.8.dp, theme.badgeBorder)
                            ) {
                                Text(
                                    text = proc.stage,
                                    fontSize = 11.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = theme.badgeText,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }

                        // Detailed Steps Box
                        Text(
                            text = "Langkah & Mekanisme Penanganan:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = BentoPrimaryDark
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Surface(
                            color = theme.stepBg,
                            shape = RoundedCornerShape(14.dp),
                            border = androidx.compose.foundation.BorderStroke(1.2.dp, theme.stepBorder),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = proc.steps,
                                fontSize = 12.5.sp,
                                fontWeight = FontWeight.Medium,
                                color = theme.stepText,
                                lineHeight = 19.5.sp,
                                modifier = Modifier.padding(14.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Pihak Terlibat
                        Surface(
                            color = Color(0xFFF8FAFC),
                            shape = RoundedCornerShape(10.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Groups,
                                    contentDescription = null,
                                    tint = BentoPrimary,
                                    modifier = Modifier.size(16.dp).padding(top = 1.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = "Pihak yang Terlibat:",
                                        fontSize = 10.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = BentoPrimaryDark
                                    )
                                    Text(
                                        text = proc.actorsInvolved,
                                        fontSize = 11.5.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color(0xFF334155),
                                        lineHeight = 16.sp
                                    )
                                }
                            }
                        }

                        // Hasil / Output yang Diharapkan
                        if (proc.expectedOutcome.isNotBlank()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Surface(
                                color = theme.badgeBg.copy(alpha = 0.6f),
                                shape = RoundedCornerShape(10.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, theme.badgeBorder),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.TaskAlt,
                                        contentDescription = null,
                                        tint = theme.badgeText,
                                        modifier = Modifier.size(16.dp).padding(top = 1.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(
                                            text = "Output & Target Pembinaan:",
                                            fontSize = 10.5.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = theme.badgeText
                                        )
                                        Text(
                                            text = proc.expectedOutcome,
                                            fontSize = 11.5.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = theme.stepText,
                                            lineHeight = 16.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
