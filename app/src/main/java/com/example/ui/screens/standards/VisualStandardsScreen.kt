package com.example.ui.screens.standards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.R
import com.example.data.model.VisualStandardEntity
import com.example.ui.theme.BentoBorderColor
import com.example.ui.theme.BentoInfoBg
import com.example.ui.theme.BentoInfoBody
import com.example.ui.theme.BentoInfoBorder
import com.example.ui.theme.BentoPaleBlue
import com.example.ui.theme.BentoPermittedBg
import com.example.ui.theme.BentoPermittedBody
import com.example.ui.theme.BentoPermittedBorder
import com.example.ui.theme.BentoPermittedTitle
import com.example.ui.theme.BentoPrimary
import com.example.ui.theme.BentoProhibitedBg
import com.example.ui.theme.BentoProhibitedBody
import com.example.ui.theme.BentoProhibitedBorder
import com.example.ui.theme.BentoProhibitedTitle
import com.example.ui.theme.BentoTextPrimary
import com.example.ui.theme.BentoTextSecondary
import com.example.viewmodel.TatibViewModel

@Composable
fun VisualStandardsScreen(
    viewModel: TatibViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val standards by viewModel.allStandards.collectAsState()
    var selectedCategory by remember { mutableStateOf("Semua") }
    var searchQuery by remember { mutableStateOf("") }

    val categories = listOf(
        "Semua",
        "Seragam",
        "Sepatu & Kaos Kaki",
        "Rambut Putra",
        "Rambut Putri",
        "Kuku & Aksesoris",
        "Gadget & HP"
    )

    val filteredStandards = standards.filter { std ->
        val matchesCategory = selectedCategory == "Semua" || std.category == selectedCategory
        val matchesSearch = searchQuery.isBlank() ||
                std.title.contains(searchQuery, ignoreCase = true) ||
                std.permittedRule.contains(searchQuery, ignoreCase = true) ||
                std.prohibitedRule.contains(searchQuery, ignoreCase = true)
        matchesCategory && matchesSearch
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .testTag("standards_screen_content")
    ) {
        // Bento Header
        Surface(
            color = Color.White,
            shadowElevation = 2.dp,
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.testTag("standards_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Kembali",
                        tint = BentoPrimary
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Column {
                    Text(
                        text = "Standar Visual & Ketentuan Khusus",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )
                    Text(
                        text = "Pedoman Seragam, Rambut 2-2-1, Sepatu & Aksesoris",
                        fontSize = 11.sp,
                        color = Color(0xFF64748B)
                    )
                }
            }
        }

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Cari standar (sepatu, rambut, batik, pramuka...)", fontSize = 13.sp, color = Color(0xFF64748B)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = BentoPrimary
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
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
                .testTag("standards_search_field")
        )

        // Categories Chips Bento
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
        ) {
            items(categories) { cat ->
                val isSelected = selectedCategory == cat
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedCategory = cat },
                    label = {
                        Text(
                            text = cat,
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
                        borderColor = BentoBorderColor,
                        selectedBorderColor = BentoPrimary
                    ),
                    modifier = Modifier.testTag("chip_standard_$cat")
                )
            }
        }

        // Standards List
        LazyColumn(
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(filteredStandards, key = { it.id }) { standard ->
                VisualStandardCard(standard = standard)
            }
        }
    }
}

@Composable
fun VisualStandardCard(standard: VisualStandardEntity) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, BentoBorderColor, RoundedCornerShape(20.dp))
            .testTag("standard_card_${standard.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
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
                        text = standard.category,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = BentoPrimary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Surface(
                    color = Color(0xFFF1F5F9),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Hal. ${standard.pageNumber}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = BentoTextSecondary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = standard.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = standard.subtitle,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // School Uniform Photo Showcase (Siswi Wajib Lengan Panjang)
            if (standard.id == "STD_SERAGAM" || standard.category == "Seragam") {
                var showSeragamZoomDialog by remember { mutableStateOf(false) }

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.2.dp, Color(0xFFBFDBFE), RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { showSeragamZoomDialog = true }
                        .testTag("card_photo_seragam_standar")
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(190.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_seragam_standar),
                                contentDescription = "Ilustrasi Animasi Seragam Resmi SMAN 1 Kepanjen (Putih Abu-Abu) Siswa Putra dan Siswi Putri",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )

                            // Top Tag
                            Surface(
                                color = BentoPrimary.copy(alpha = 0.92f),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(10.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Seragam SMAN 1 Kepanjen: Putra & Putri Wajib Dasi | Rambut 2-2-1",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }

                            // Bottom Hint
                            Surface(
                                color = Color.Black.copy(alpha = 0.65f),
                                shape = RoundedCornerShape(topStart = 10.dp),
                                modifier = Modifier.align(Alignment.BottomEnd)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ZoomIn,
                                        contentDescription = "Perbesar",
                                        tint = Color.White,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Ketuk untuk Memperbesar",
                                        fontSize = 10.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }

                        // Photo Caption
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(BentoPaleBlue)
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = BentoPrimary,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Ilustrasi Animasi Seragam Resmi SMAN 1 Kepanjen (Putih Abu-Abu): Siswa Putra dan Siswi Putri Mengenakan Seragam Lengkap SMAN 1 Kepanjen, Rambut Putra Rapi Standar 2-2-1 & Wajib Memakai Dasi Sekolah (Panjang di Bawah Pusar)",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF0F2B5C)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Ketentuan Seragam Siswa Laki-Laki (Putra) Callout Box
                Surface(
                    color = Color(0xFFEFF6FF),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF93C5FD)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("box_ketentuan_seragam_putra")
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(BentoPrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "👔", fontSize = 16.sp)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Ketentuan Seragam Siswa Laki-Laki (Putra)",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = BentoPrimary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "• Standar Rambut Rapi: Potongan rambut rapi disiplin ukuran 2-2-1 (tidak menyentuh telinga, alis, maupun kerah kemeja, tidak dicat/warna-warni)\n" +
                                        "• WAJIB Mengenakan Dasi Sekolah: Pemakaian dasi resmi sekolah dengan standar panjang dasi di bawah pusar (ujung dasi menjuntai rapi menutupi pusar hingga di bawah pusar, tidak menggantung tinggi)\n" +
                                        "• Kemeja Seragam: Wajib dimasukkan rapi ke dalam celana sepanjang jam sekolah\n" +
                                        "• Sabuk/Ikat Pinggang: Wajib warna hitam polos dengan gesper standar berlogo sekolah\n" +
                                        "• Celana Panjang: Model sopan standar siswa (dilarang pensil/ketat, cutbray, atau botol)\n" +
                                        "• Aturan Lengan: Senin & Selasa baju putih lengan panjang, Rabu baju putih lengan pendek, Kamis batik SMANEKA lengan pendek, Jumat pramuka lengan pendek",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF1E3A8A),
                                lineHeight = 16.5.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Standar Panjang Dasi Callout Box
                Surface(
                    color = Color(0xFFFEF3C7),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFDE68A)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("box_standar_dasi_seragam")
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFF59E0B)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "👔", fontSize = 16.sp)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Standar Pemakaian Dasi Sekolah (Putra & Putri)",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF92400E)
                            )
                            Text(
                                text = "Standar panjang dasi: Di bawah pusar (ujung dasi terpasang rapi menjuntai menutupi hingga di bawah pusar, tidak menggantung tinggi). Dasi wajib selalu dikenakan dengan rapi setiap saat di lingkungan sekolah oleh siswa putra dan siswi putri.",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF78350F),
                                lineHeight = 16.sp,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Jadwal Harian & Aturan Lengan Box
                Surface(
                    color = BentoPaleBlue,
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFBFDBFE)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("box_jadwal_lengan_seragam")
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = BentoPrimary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Aturan Lengan Seragam Harian (Senin s.d. Jumat)",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = BentoPrimary
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "• Senin: Baju putih lengan panjang (putra & putri) + dasi di bawah pusar & topi pet\n" +
                                    "• Selasa: Baju putih lengan panjang (putra & putri WAJIB lengan panjang)\n" +
                                    "• Rabu: Baju putih lengan pendek (putra) / baju putih lengan panjang (putri)\n" +
                                    "• Kamis: Baju Batik SMANEKA lengan pendek (putra) / baju batik lengan panjang (putri)\n" +
                                    "• Jumat: Baju Pramuka lengan pendek (putra) / baju pramuka lengan panjang (putri) + atribut lengkap\n" +
                                    "• Siswi: Seluruh seragam atasan (Senin–Jumat) WAJIB LENGAN PANJANG.",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF0F2B5C),
                            lineHeight = 16.5.sp
                        )
                    }
                }

                // Fullscreen Seragam Zoom Dialog
                if (showSeragamZoomDialog) {
                    Dialog(onDismissRequest = { showSeragamZoomDialog = false }) {
                        Card(
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            modifier = Modifier
                                .fillMaxWidth(0.95f)
                                .widthIn(max = 560.dp)
                                .padding(vertical = 16.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Standar Seragam Harian Sekolah",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    IconButton(
                                        onClick = { showSeragamZoomDialog = false },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Tutup",
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Image(
                                    painter = painterResource(id = R.drawable.img_seragam_standar),
                                    contentDescription = "Detail Ilustrasi Animasi Seragam Resmi SMAN 1 Kepanjen (Putih Abu-Abu) Siswa Putra dan Siswi Putri",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(210.dp)
                                        .clip(RoundedCornerShape(12.dp)),
                                    contentScale = ContentScale.Crop
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                // Ketentuan Khusus Siswa Laki-Laki (Putra) Box
                                Surface(
                                    color = Color(0xFFEFF6FF),
                                    shape = RoundedCornerShape(12.dp),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF93C5FD)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(
                                            text = "👔 Ketentuan Seragam Siswa Laki-Laki (Putra):",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = BentoPrimary
                                        )
                                        Spacer(modifier = Modifier.height(3.dp))
                                        Text(
                                            text = "• Standar rambut rapi: Disiplin ukuran 2-2-1 (tidak menyentuh alis, telinga, maupun kerah kemeja, tidak dicat warna-warni)\n" +
                                                    "• WAJIB mengenakan dasi sekolah resmi dengan standar panjang di bawah pusar (ujung dasi menjuntai rapi menutupi pusar hingga di bawah pusar, tidak menggantung tinggi)\n" +
                                                    "• Kemeja seragam wajib dimasukkan rapi ke dalam celana sepanjang waktu sekolah (tidak dibiarkan keluar/kancing terbuka)\n" +
                                                    "• Menggunakan ikat pinggang (sabuk) hitam polos standar berlogo sekolah\n" +
                                                    "• Celana panjang warna abu-abu/putih/pramuka model sopan standar siswa (dilarang pensil/ketat, cutbray, atau botol)\n" +
                                                    "• Aturan lengan baju kemeja: Senin & Selasa baju putih lengan panjang, Rabu baju putih lengan pendek, Kamis batik SMANEKA lengan pendek, Jumat pramuka lengan pendek",
                                            fontSize = 11.sp,
                                            color = Color(0xFF1E3A8A),
                                            lineHeight = 16.sp
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                 // Standar Panjang Dasi Box
                                Surface(
                                    color = Color(0xFFFEF3C7),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(
                                            text = "👔 Standar Panjang Dasi Sekolah (Putra & Putri):",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF92400E)
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = "• Standar panjang dasi: Di bawah pusar (ujung dasi wajib menjuntai rapi menutupi pusar hingga di bawah pusar, tidak terlalu pendek atau menggantung tinggi)\n" +
                                                    "• Dasi wajib selalu dikenakan dengan rapi di kerah kemeja setiap saat di lingkungan sekolah oleh siswa putra dan siswi putri.",
                                            fontSize = 11.sp,
                                            color = Color(0xFF78350F),
                                            lineHeight = 16.sp
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                // Ketentuan Khusus Siswi Box
                                Surface(
                                    color = BentoPermittedBg,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(
                                            text = "🧕 Ketentuan Khusus Siswa Perempuan (Putri):",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = BentoPermittedTitle
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = "• WAJIB mengenakan dasi sekolah resmi dengan standar panjang di bawah pusar setiap saat di sekolah\n" +
                                                    "• SELURUH seragam atasan (Senin s.d. Jumat) WAJIB LENGAN PANJANG\n" +
                                                    "• Rok wajib panjang menutup rapi hingga mata kaki\n" +
                                                    "• Jilbab kain segi empat resmi sekolah (bagi yang berhijab)\n" +
                                                    "• Seragam tidak boleh ketat atau membentuk lekuk tubuh",
                                            fontSize = 11.sp,
                                            color = BentoPermittedBody,
                                            lineHeight = 16.sp
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                // Jadwal Harian Box
                                Surface(
                                    color = BentoPaleBlue,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(
                                            text = "📅 Jadwal Pemakaian Seragam:",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = BentoPrimary
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = "• Senin: Baju Putih Lengan Panjang (Putra & Putri), Rok/Celana Putih + Topi/Dasi (di bawah pusar)/Sabuk\n" +
                                                    "• Selasa: Baju Putih Lengan Panjang (Putra & Putri WAJIB Lengan Panjang), Rok/Celana Abu-abu\n" +
                                                    "• Rabu: Baju Putih Lengan Pendek (Putra) / Baju Putih Lengan Panjang (Putri), Celana/Rok Abu-abu\n" +
                                                    "• Kamis: Baju Batik SMANEKA Lengan Pendek (Putra) / Baju Batik Lengan Panjang (Putri), Rok/Celana Putih\n" +
                                                    "• Jumat: Baju Pramuka Lengan Pendek (Putra) / Baju Pramuka Lengan Panjang (Putri) + Atribut Pramuka Lengkap",
                                            fontSize = 11.sp,
                                            color = Color(0xFF0F2B5C),
                                            lineHeight = 16.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Male Haircut Photo Showcase if this standard is Rambut Putra
            if (standard.id == "STD_RAMBUT_PUTRA" || standard.category == "Rambut Putra") {
                var showZoomDialog by remember { mutableStateOf(false) }

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.2.dp, Color(0xFFBFDBFE), RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { showZoomDialog = true }
                        .testTag("card_photo_rambut_putra")
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(190.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_rambut_putra),
                                contentDescription = "Contoh Foto Model Rambut Putra Ukuran 2-2-1",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )

                            // Top Tag
                            Surface(
                                color = BentoPrimary.copy(alpha = 0.9f),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(10.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ContentCut,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Contoh Resmi Potongan 2-2-1",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }

                            // Bottom Hint
                            Surface(
                                color = Color.Black.copy(alpha = 0.65f),
                                shape = RoundedCornerShape(topStart = 10.dp),
                                modifier = Modifier.align(Alignment.BottomEnd)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ZoomIn,
                                        contentDescription = "Perbesar",
                                        tint = Color.White,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Ketuk untuk Memperbesar",
                                        fontSize = 10.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }

                        // Photo Caption
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(BentoPaleBlue)
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = BentoPrimary,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Panduan Visual Lampiran Dokumen Resmi: Samping 2cm, Belakang 2cm, Tepi 1cm",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF0F2B5C)
                            )
                        }
                    }
                }

                // Fullscreen Zoom Dialog
                if (showZoomDialog) {
                    Dialog(onDismissRequest = { showZoomDialog = false }) {
                        Card(
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            modifier = Modifier
                                .fillMaxWidth(0.95f)
                                .widthIn(max = 560.dp)
                                .padding(vertical = 16.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Standar Potongan Rambut 2-2-1",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    IconButton(
                                        onClick = { showZoomDialog = false },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Tutup",
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Image(
                                    painter = painterResource(id = R.drawable.img_rambut_putra),
                                    contentDescription = "Detail Model Rambut Putra 2-2-1",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(230.dp)
                                        .clip(RoundedCornerShape(12.dp)),
                                    contentScale = ContentScale.Crop
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Surface(
                                    color = BentoPaleBlue,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text(
                                            text = "Ketentuan Ukuran Resmi 2-2-1:",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = BentoPrimary
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "• Samping: Maksimal 2 cm (tidak menutupi daun telinga)\n" +
                                                    "• Belakang: Maksimal 2 cm (tidak menyentuh kerah baju)\n" +
                                                    "• Batas Tepian: 1 cm (gradasi natural, bukan skin fade)\n" +
                                                    "• Depan/Atas: Rapi, tidak menutupi dahi/alis, tidak diwarnai",
                                            fontSize = 11.sp,
                                            color = Color(0xFF0F2B5C),
                                            lineHeight = 17.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Female Hair & Hijab Photo Showcase
            if (standard.id == "STD_RAMBUT_PUTRI" || standard.category == "Rambut Putri") {
                var showPutriZoomDialog by remember { mutableStateOf(false) }

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.2.dp, Color(0xFFBFDBFE), RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { showPutriZoomDialog = true }
                        .testTag("card_photo_rambut_putri")
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(190.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_rambut_putri),
                                contentDescription = "Contoh Ketentuan Rambut dan Jilbab Siswi",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )

                            // Top Tag
                            Surface(
                                color = BentoPrimary.copy(alpha = 0.9f),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(10.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Standar Rambut & Jilbab Siswi",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }

                            // Bottom Hint
                            Surface(
                                color = Color.Black.copy(alpha = 0.65f),
                                shape = RoundedCornerShape(topStart = 10.dp),
                                modifier = Modifier.align(Alignment.BottomEnd)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ZoomIn,
                                        contentDescription = "Perbesar",
                                        tint = Color.White,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Ketuk untuk Memperbesar",
                                        fontSize = 10.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }

                        // Photo Caption
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(BentoPaleBlue)
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = BentoPrimary,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Panduan Visual: Rambut Dikuncir Rapi / Jilbab Resmi vs Dilarang Cat/Sambung",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF0F2B5C)
                            )
                        }
                    }
                }

                // Fullscreen Putri Zoom Dialog
                if (showPutriZoomDialog) {
                    Dialog(onDismissRequest = { showPutriZoomDialog = false }) {
                        Card(
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            modifier = Modifier
                                .fillMaxWidth(0.95f)
                                .widthIn(max = 560.dp)
                                .padding(vertical = 16.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Standar Rambut & Jilbab Siswa Putri",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    IconButton(
                                        onClick = { showPutriZoomDialog = false },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Tutup",
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Image(
                                    painter = painterResource(id = R.drawable.img_rambut_putri),
                                    contentDescription = "Detail Standar Rambut Putri",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(210.dp)
                                        .clip(RoundedCornerShape(12.dp)),
                                    contentScale = ContentScale.Crop
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                // Boleh Box
                                Surface(
                                    color = BentoPermittedBg,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(
                                            text = "✅ Ketentuan yang BOLEH / DIANJURKAN:",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = BentoPermittedTitle
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = "• Bagi Non-Hijab: Rambut disisir rapi dan diikat/dikuncir satu ke belakang\n" +
                                                    "• Bagi yang Berhijab: Mengenakan jilbab kain segi empat resmi sekolah, rapi & menutup dada\n" +
                                                    "• Warna rambut asli (hitam alami)",
                                            fontSize = 11.sp,
                                            color = BentoPermittedBody,
                                            lineHeight = 16.sp
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                // Dilarang Box
                                Surface(
                                    color = BentoProhibitedBg,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(
                                            text = "❌ Larangan Penampilan Rambut:",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = BentoProhibitedTitle
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = "• Dilarang mengecat / mewarnai / menyemir rambut\n" +
                                                    "• Dilarang menyambung rambut (hair extension)\n" +
                                                    "• Dilarang mencurly buatan / penataan berlebihan\n" +
                                                    "• Dilarang membiarkan poni samping terurai menutupi wajah\n" +
                                                    "• Dilarang menggunakan aksesoris rambut mencolok",
                                            fontSize = 11.sp,
                                            color = BentoProhibitedBody,
                                            lineHeight = 16.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Shoes Standard Photo Showcase (Boleh vs Dilarang)
            if (standard.id == "STD_SEPATU_KAOSKAKI" || standard.category == "Sepatu & Kaos Kaki") {
                var showShoesZoomDialog by remember { mutableStateOf(false) }

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.2.dp, Color(0xFFBFDBFE), RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { showShoesZoomDialog = true }
                        .testTag("card_photo_sepatu_standar")
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(190.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_sepatu_standar),
                                contentDescription = "Contoh Perbandingan Sepatu Boleh dan Dilarang",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )

                            // Top Tag
                            Surface(
                                color = BentoPrimary.copy(alpha = 0.9f),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(10.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Perbandingan Sepatu: Boleh vs Dilarang",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }

                            // Bottom Hint
                            Surface(
                                color = Color.Black.copy(alpha = 0.65f),
                                shape = RoundedCornerShape(topStart = 10.dp),
                                modifier = Modifier.align(Alignment.BottomEnd)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ZoomIn,
                                        contentDescription = "Perbesar",
                                        tint = Color.White,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Ketuk untuk Memperbesar",
                                        fontSize = 10.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }

                        // Photo Caption
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(BentoPaleBlue)
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = BentoPrimary,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Panduan Visual: Sepatu Hitam Bertali vs Dilarang Putih/Flatshoes",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF0F2B5C)
                            )
                        }
                    }
                }

                // Fullscreen Shoes Zoom Dialog
                if (showShoesZoomDialog) {
                    Dialog(onDismissRequest = { showShoesZoomDialog = false }) {
                        Card(
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            modifier = Modifier
                                .fillMaxWidth(0.95f)
                                .widthIn(max = 560.dp)
                                .padding(vertical = 16.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Panduan Sepatu Boleh & Dilarang",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    IconButton(
                                        onClick = { showShoesZoomDialog = false },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Tutup",
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Image(
                                    painter = painterResource(id = R.drawable.img_sepatu_standar),
                                    contentDescription = "Detail Perbandingan Sepatu",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(210.dp)
                                        .clip(RoundedCornerShape(12.dp)),
                                    contentScale = ContentScale.Crop
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                // Boleh Box
                                Surface(
                                    color = BentoPermittedBg,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(
                                            text = "✅ Sepatu yang BOLEH / WAJIB Digunakan:",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = BentoPermittedTitle
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = "• Sepatu model siswa hitam polos / hitam doff bertali hitam\n" +
                                                    "• Sepatu model pantofel/fantovel hitam polos standar\n" +
                                                    "• Kaos kaki min. 15 cm di atas mata kaki (Senin-Kamis putih, Jumat hitam)",
                                            fontSize = 11.sp,
                                            color = BentoPermittedBody,
                                            lineHeight = 16.sp
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                // Dilarang Box
                                Surface(
                                    color = BentoProhibitedBg,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(
                                            text = "❌ Sepatu yang DILARANG Digunakan:",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = BentoProhibitedTitle
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = "• Sepatu putih / warna-warni / garis corak warna selain hitam\n" +
                                                    "• Model flatshoes atau slip-on santai tipis tanpa tali\n" +
                                                    "• Sepatu bahan glossy (mengkilap mencolok) / boots\n" +
                                                    "• Sandal atau selop ke sekolah tanpa surat izin dokter\n" +
                                                    "• Kaos kaki pendek / semata kaki (< 15 cm) / bermotif",
                                            fontSize = 11.sp,
                                            color = BentoProhibitedBody,
                                            lineHeight = 16.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Make Up & Cosmetics Standard Photo Showcase
            if (standard.id == "STD_KUKU_MAKEUP" || standard.category == "Kuku & Aksesoris") {
                var showMakeupZoomDialog by remember { mutableStateOf(false) }

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.2.dp, Color(0xFFBFDBFE), RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { showMakeupZoomDialog = true }
                        .testTag("card_photo_makeup_standar")
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_makeup_standar),
                                contentDescription = "Panduan Standar Visual Make Up, Kuku, dan Kosmetik Pelajar",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )

                            // Top Tag
                            Surface(
                                color = BentoPrimary.copy(alpha = 0.92f),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(10.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Standar Make Up, Kuku & Kosmetik",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }

                            // Bottom Hint
                            Surface(
                                color = Color.Black.copy(alpha = 0.65f),
                                shape = RoundedCornerShape(topStart = 10.dp),
                                modifier = Modifier.align(Alignment.BottomEnd)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ZoomIn,
                                        contentDescription = "Perbesar",
                                        tint = Color.White,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Ketuk untuk Memperbesar",
                                        fontSize = 10.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }

                        // Photo Caption
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(BentoPaleBlue)
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = BentoPrimary,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Panduan Visual: Wajah Alami & Kuku Pendek Bersih vs Larangan Kutek/Make Up Tebal",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF0F2B5C)
                            )
                        }
                    }
                }

                // Fullscreen Make Up Zoom Dialog
                if (showMakeupZoomDialog) {
                    Dialog(onDismissRequest = { showMakeupZoomDialog = false }) {
                        Card(
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            modifier = Modifier
                                .fillMaxWidth(0.95f)
                                .widthIn(max = 560.dp)
                                .padding(vertical = 16.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Standar Make Up, Kuku & Kosmetik",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    IconButton(
                                        onClick = { showMakeupZoomDialog = false },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Tutup",
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Image(
                                    painter = painterResource(id = R.drawable.img_makeup_standar),
                                    contentDescription = "Detail Standar Make Up dan Kuku",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(210.dp)
                                        .clip(RoundedCornerShape(12.dp)),
                                    contentScale = ContentScale.Crop
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                // Boleh Box
                                Surface(
                                    color = BentoPermittedBg,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(
                                            text = "✅ Standar yang DIPERBOLEHKAN / DIANJURKAN:",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = BentoPermittedTitle
                                        )
                                        Spacer(modifier = Modifier.height(3.dp))
                                        Text(
                                            text = "• Wajah: Bersih, segar, dan berpenampilan alami khas pelajar\n" +
                                                    "• Perawatan Kulit: Boleh menggunakan pelembab (moisturizer), sunscreen pelindung sinar UV, dan bedak tabur tipis transparan\n" +
                                                    "• Bibir: Hanya boleh pelembab/lip balm transparan tanpa warna mencolok\n" +
                                                    "• Kuku: Dipotong pendek rapi, bersih secara alami tanpa pewarna\n" +
                                                    "• Alis: Bentuk alis alami dan rapi",
                                            fontSize = 11.sp,
                                            color = BentoPermittedBody,
                                            lineHeight = 16.sp
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                // Dilarang Box
                                Surface(
                                    color = BentoProhibitedBg,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(
                                            text = "❌ Riasan & Kosmetik yang DILARANG:",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = BentoProhibitedTitle
                                        )
                                        Spacer(modifier = Modifier.height(3.dp))
                                        Text(
                                            text = "• Make up tebal / menor / foundation tebal / blush on berlebihan\n" +
                                                    "• Lipstik merah menyala, liptint tebal, atau lip gloss mencolok\n" +
                                                    "• Mencukur alis, sulam alis, membuat tato/garis alis, atau pensil alis tebal\n" +
                                                    "• Memakai eyelash extension (sambung bulu mata) / bulu mata palsu\n" +
                                                    "• Cat kuku (kutek), nail art, kuku palsu/sambung kuku akrilik, atau pacar kuku warna-warni\n" +
                                                    "• Sambungan rambut (hair extension) & cat warna rambut\n" +
                                                    "• Perhiasan emas mencolok, anting berlebihan / tindik bertingkat",
                                            fontSize = 11.sp,
                                            color = BentoProhibitedBody,
                                            lineHeight = 16.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Permitted (Boleh/Wajib) Bento Box - High Contrast
            Surface(
                color = BentoPermittedBg,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BentoPermittedBorder, RoundedCornerShape(14.dp))
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(BentoPermittedTitle),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Standar Diperbolehkan / Wajib:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = BentoPermittedTitle
                        )
                        Text(
                            text = standard.permittedRule,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = BentoPermittedBody,
                            lineHeight = 18.sp,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Prohibited (Dilarang) Bento Box - High Contrast
            Surface(
                color = BentoProhibitedBg,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BentoProhibitedBorder, RoundedCornerShape(14.dp))
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(BentoProhibitedTitle),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Standar Dilarang:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = BentoProhibitedTitle
                        )
                        Text(
                            text = standard.prohibitedRule,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = BentoProhibitedBody,
                            lineHeight = 18.sp,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
            }

            // Visual details info
            if (standard.details.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    color = BentoInfoBg,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, BentoInfoBorder, RoundedCornerShape(10.dp))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = BentoPrimary,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Ketentuan Rinci: ${standard.details}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = BentoInfoBody
                        )
                    }
                }
            }
        }
    }
}
