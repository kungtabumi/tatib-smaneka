package com.example.ui.screens.learning

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.LearningTopicEntity
import com.example.ui.navigation.Screen
import com.example.ui.theme.BentoBorderBlue
import com.example.ui.theme.BentoGold
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
import com.example.ui.theme.GradientBelajar
import com.example.viewmodel.TatibViewModel

@Composable
fun LearningScreen(
    viewModel: TatibViewModel,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val topics by viewModel.allLearningTopics.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .testTag("learning_screen_content"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Quiz Banner Bento Card
        item {
            Card(
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .background(GradientBelajar)
                    .testTag("quiz_launcher_card")
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Surface(
                            color = BentoGold,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "EVALUASI PEMAHAMAN",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.Black,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Kuis Tata Tertib SMANEKA",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "10 soal interaktif pilihan ganda, benar/salah, & studi kasus bersitasi resmi.",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.9f),
                            lineHeight = 17.sp,
                            modifier = Modifier.padding(top = 3.dp)
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Button(
                            onClick = { onNavigate(Screen.Quiz.route) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BentoGold,
                                contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("btn_start_quiz")
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Mulai Kuis", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.25f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Quiz,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }

        // Educational Resources Banner Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = BentoPaleBlue),
                border = androidx.compose.foundation.BorderStroke(1.2.dp, BentoBorderBlue),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .clickable { onNavigate(Screen.EducationalResources.route) }
                    .testTag("edu_resources_launcher_card")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(BentoPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.School,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Surface(
                            color = ColorCategorySeragamBg,
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier.padding(bottom = 3.dp)
                        ) {
                            Text(
                                text = "NILAI & PANDUAN PERILAKU",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                color = ColorCategorySeragamText,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Text(
                            text = "Artikel & Tips Edukasi Karakter",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = BentoPrimaryDark
                        )
                        Text(
                            text = "Budaya 5S, etika digital, anti-bullying & lembar refleksi diri",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 15.sp,
                            modifier = Modifier.padding(top = 1.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = BentoPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Text(
                    text = "Modul Belajar Tata Tertib",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        items(topics, key = { it.id }) { topic ->
            LearningTopicCard(
                topic = topic,
                onExplore = {
                    onNavigate(Screen.Pedoman.route)
                }
            )
        }
    }
}

@Composable
fun LearningTopicCard(
    topic: LearningTopicEntity,
    onExplore: () -> Unit
) {
    val (catBg, catBorder, catText) = when {
        topic.category.contains("Seragam", ignoreCase = true) -> Triple(ColorCategorySeragamBg, ColorCategorySeragamBorder, ColorCategorySeragamText)
        topic.category.contains("Rambut", ignoreCase = true) || topic.category.contains("Kuku", ignoreCase = true) -> Triple(ColorCategoryRambutBg, ColorCategoryRambutBorder, ColorCategoryRambutText)
        topic.category.contains("Waktu", ignoreCase = true) || topic.category.contains("Belajar", ignoreCase = true) -> Triple(ColorCategoryWaktuBg, ColorCategoryWaktuBorder, ColorCategoryWaktuText)
        topic.category.contains("Barang", ignoreCase = true) || topic.category.contains("HP", ignoreCase = true) -> Triple(ColorCategoryBarangBg, ColorCategoryBarangBorder, ColorCategoryBarangText)
        topic.category.contains("Kendaraan", ignoreCase = true) || topic.category.contains("Parkir", ignoreCase = true) -> Triple(ColorCategoryKendaraanBg, ColorCategoryKendaraanBorder, ColorCategoryKendaraanText)
        topic.category.contains("Etika", ignoreCase = true) || topic.category.contains("Norma", ignoreCase = true) -> Triple(ColorCategoryEtikaBg, ColorCategoryEtikaBorder, ColorCategoryEtikaText)
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
            .testTag("learning_card_${topic.id}")
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(catBg),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${topic.topicNumber}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = catText
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        color = catBg,
                        border = androidx.compose.foundation.BorderStroke(1.dp, catBorder),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = topic.category,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = catText,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }

                Surface(
                    color = Color(0xFFF1F5F9),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Hal. ${topic.pageNumber}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = topic.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = topic.summary,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                color = catBg,
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, catBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = topic.keyPoints,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = catText,
                    lineHeight = 18.sp,
                    modifier = Modifier.padding(12.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Rujukan: ${topic.sourceSection}",
                    fontSize = 11.sp,
                    color = BentoPrimary,
                    fontWeight = FontWeight.SemiBold
                )

                Surface(
                    color = catBg,
                    border = androidx.compose.foundation.BorderStroke(1.dp, catBorder),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { onExplore() }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = "Buka Bab",
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
