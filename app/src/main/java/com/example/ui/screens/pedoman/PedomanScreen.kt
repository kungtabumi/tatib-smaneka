package com.example.ui.screens.pedoman

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.RegulationEntity
import com.example.ui.theme.BentoBorderBlue
import com.example.ui.theme.BentoPaleBlue
import com.example.ui.theme.BentoPrimary
import com.example.ui.theme.BentoPrimaryDark
import com.example.ui.theme.BentoSoftBlue
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
import com.example.ui.theme.GradientPelanggaran
import com.example.ui.theme.SmanekaGold
import com.example.viewmodel.TatibViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PedomanScreen(
    viewModel: TatibViewModel,
    modifier: Modifier = Modifier
) {
    val regulations by viewModel.filteredRegulations.collectAsState()
    val selectedChapter by viewModel.selectedChapter.collectAsState()
    val bookmarks by viewModel.bookmarks.collectAsState()
    var isVerbatimMode by remember { mutableStateOf(false) }
    var activeSearchQuery by remember { mutableStateOf("") }
    var selectedRegulationForSource by remember { mutableStateOf<RegulationEntity?>(null) }
    val bottomSheetState = rememberModalBottomSheetState()

    val chapters = listOf(
        "Semua BAB",
        "BAB 1",
        "BAB 2",
        "BAB 3",
        "BAB 4",
        "BAB 5",
        "BAB 6",
        "BAB 7"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .testTag("pedoman_screen_content")
    ) {
        // Bento Search Field
        OutlinedTextField(
            value = activeSearchQuery,
            onValueChange = {
                activeSearchQuery = it
                viewModel.setSearchQuery(it)
            },
            placeholder = { Text("Cari bab, pasal, seragam, jam masuk...", fontSize = 13.sp, color = Color(0xFF64748B)) },
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
                .testTag("pedoman_search_field")
        )

        // Bento Segmented Control: Ringkasan vs Ketentuan Asli
        Surface(
            color = BentoPaleBlue,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                Surface(
                    color = if (!isVerbatimMode) BentoPrimary else Color.Transparent,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { isVerbatimMode = false }
                        .testTag("tab_summary_mode")
                ) {
                    Box(
                        modifier = Modifier.padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Ringkasan Praktis",
                            fontSize = 12.sp,
                            fontWeight = if (!isVerbatimMode) FontWeight.Bold else FontWeight.Medium,
                            color = if (!isVerbatimMode) Color.White else BentoPrimaryDark
                        )
                    }
                }

                Surface(
                    color = if (isVerbatimMode) BentoPrimary else Color.Transparent,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { isVerbatimMode = true }
                        .testTag("tab_verbatim_mode")
                ) {
                    Box(
                        modifier = Modifier.padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Ketentuan Asli Dokumen",
                            fontSize = 12.sp,
                            fontWeight = if (isVerbatimMode) FontWeight.Bold else FontWeight.Medium,
                            color = if (isVerbatimMode) Color.White else BentoPrimaryDark
                        )
                    }
                }
            }
        }

        // Chapter Chips Bento
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(chapters) { chapter ->
                val isSelected = selectedChapter == chapter
                FilterChip(
                    selected = isSelected,
                    onClick = { viewModel.setSelectedChapter(chapter) },
                    label = {
                        Text(
                            text = chapter,
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
                    modifier = Modifier.testTag("chip_chapter_$chapter")
                )
            }
        }

        // Regulations List with subtle fade-in animation
        AnimatedContent(
            targetState = Pair(regulations, activeSearchQuery),
            transitionSpec = {
                fadeIn(animationSpec = tween(durationMillis = 280)) togetherWith
                fadeOut(animationSpec = tween(durationMillis = 150))
            },
            label = "rules_search_results_animation"
        ) { (targetRegulations, query) ->
            if (targetRegulations.isEmpty()) {
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
                                imageVector = Icons.AutoMirrored.Filled.MenuBook,
                                contentDescription = null,
                                tint = BentoPrimary,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Tidak ada aturan yang sesuai pencarian.",
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
                    if (query.isNotBlank()) {
                        item {
                            Surface(
                                color = BentoPaleBlue,
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.padding(bottom = 2.dp)
                            ) {
                                Text(
                                    text = "Ditemukan ${targetRegulations.size} aturan untuk \"$query\"",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = BentoPrimary,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                )
                            }
                        }
                    }

                    items(targetRegulations, key = { it.id }) { reg ->
                        val isBookmarked = bookmarks.any { it.id == reg.id }
                        RegulationItemCard(
                            regulation = reg,
                            isVerbatimMode = isVerbatimMode,
                            isBookmarked = isBookmarked,
                            onToggleBookmark = {
                                viewModel.toggleBookmark(
                                    id = reg.id,
                                    itemType = "REGULATION",
                                    title = reg.title,
                                    subtitle = "${reg.chapterNumber} Poin ${reg.sectionNumber}",
                                    category = reg.category
                                )
                            },
                            onViewSource = {
                                selectedRegulationForSource = reg
                                viewModel.recordReading(
                                    id = reg.id,
                                    title = reg.title,
                                    subtitle = "${reg.chapterNumber} Poin ${reg.sectionNumber}",
                                    category = reg.category,
                                    route = "pedoman"
                                )
                            }
                        )
                    }
                }
            }
        }
    }

    // Modal Bottom Sheet for Exact Source & Citation
    if (selectedRegulationForSource != null) {
        val reg = selectedRegulationForSource!!
        ModalBottomSheet(
            onDismissRequest = { selectedRegulationForSource = null },
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
                    Surface(
                        color = BentoPaleBlue,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "${reg.chapterNumber} : ${reg.chapterTitle}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = BentoPrimary,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }

                    Surface(
                        color = Color(0xFFF1F5F9),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Halaman ${reg.pageNumber}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Poin ${reg.sectionNumber}: ${reg.title}",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Teks Dokumen Asli:",
                    fontSize = 12.sp,
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
                    Text(
                        text = reg.fullContent,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF0F2B5C),
                        lineHeight = 21.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Dasar Pengesahan: Tata Tertib SMAN 1 Kepanjen 2026/2027 (Kepanjen, 13 Juli 2026)",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun RegulationItemCard(
    regulation: RegulationEntity,
    isVerbatimMode: Boolean,
    isBookmarked: Boolean,
    onToggleBookmark: () -> Unit,
    onViewSource: () -> Unit
) {
    val (catBg, catBorder, catText) = when (regulation.category.uppercase()) {
        "SERAGAM" -> Triple(ColorCategorySeragamBg, ColorCategorySeragamBorder, ColorCategorySeragamText)
        "RAMBUT_KUKU", "RAMBUT" -> Triple(ColorCategoryRambutBg, ColorCategoryRambutBorder, ColorCategoryRambutText)
        "WAKTU" -> Triple(ColorCategoryWaktuBg, ColorCategoryWaktuBorder, ColorCategoryWaktuText)
        "BARANG" -> Triple(ColorCategoryBarangBg, ColorCategoryBarangBorder, ColorCategoryBarangText)
        "KENDARAAN" -> Triple(ColorCategoryKendaraanBg, ColorCategoryKendaraanBorder, ColorCategoryKendaraanText)
        "ETIKA" -> Triple(ColorCategoryEtikaBg, ColorCategoryEtikaBorder, ColorCategoryEtikaText)
        "SOP", "SANKSI" -> Triple(ColorCategorySopBg, ColorCategorySopBorder, ColorCategorySopText)
        else -> Triple(BentoPaleBlue, BentoBorderBlue, BentoPrimary)
    }

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = androidx.compose.foundation.BorderStroke(1.2.dp, catBorder),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("regulation_card_${regulation.id}")
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header: Chapter Badge, Section Pill, Category Pill & Bookmark
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = catBg,
                        border = androidx.compose.foundation.BorderStroke(1.dp, catBorder),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = regulation.chapterNumber,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = catText,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Surface(
                        color = Color(0xFFF1F5F9),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Poin ${regulation.sectionNumber}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }

                IconButton(
                    onClick = onToggleBookmark,
                    modifier = Modifier
                        .size(32.dp)
                        .testTag("btn_bookmark_${regulation.id}")
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

            // Title
            Text(
                text = regulation.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Content according to mode
            Text(
                text = if (isVerbatimMode) regulation.fullContent else regulation.summaryContent,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 19.sp
            )

            // Source & Open Footer
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Hal. ${regulation.pageNumber} • Dokumen Resmi",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Surface(
                    color = catBg,
                    border = androidx.compose.foundation.BorderStroke(1.dp, catBorder),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { onViewSource() }
                        .testTag("btn_source_${regulation.id}")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = "Teks Dokumen",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = catText
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = catText,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
            }
        }
    }
}
