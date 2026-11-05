package com.example.ui.screens.learning

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.example.data.model.QuizQuestionEntity
import com.example.ui.theme.BentoBorderColor
import com.example.ui.theme.BentoGold
import com.example.ui.theme.BentoInfoBg
import com.example.ui.theme.BentoInfoBody
import com.example.ui.theme.BentoInfoBorder
import com.example.ui.theme.BentoInfoTitle
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
fun QuizScreen(
    viewModel: TatibViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val quizState by viewModel.quizState.collectAsState()
    val questions by viewModel.allQuizQuestions.collectAsState()

    if (questions.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Memuat soal kuis...",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        return
    }

    if (quizState.isFinished) {
        QuizResultScreen(
            score = quizState.score,
            totalQuestions = questions.size,
            onRestart = { viewModel.resetQuiz() },
            onBack = onBack,
            modifier = modifier
        )
        return
    }

    val currentQuestion = questions.getOrNull(quizState.currentQuestionIndex) ?: questions.first()
    val progress = (quizState.currentQuestionIndex + 1).toFloat() / questions.size.toFloat()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
            .testTag("quiz_screen_content")
    ) {
        // Bento Top Bar
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("quiz_back_button")
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = BentoPrimary)
                    }
                    Text(
                        text = "Kuis Tata Tertib",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Surface(
                    color = BentoPaleBlue,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.padding(end = 12.dp)
                ) {
                    Text(
                        text = "Skor: ${quizState.score}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = BentoPrimary,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // Progress indicator
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp),
            color = BentoPrimary,
            trackColor = BentoPaleBlue
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Question Tracker Badge
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = BentoPaleBlue,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Soal ${quizState.currentQuestionIndex + 1} dari ${questions.size}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = BentoPrimary,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

            Surface(
                color = Color(0xFFF1F5F9),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = currentQuestion.questionType.replace("_", " "),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BentoTextSecondary,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Scenario/Case study if available
        val scenarioText = currentQuestion.scenario
        if (!scenarioText.isNullOrBlank()) {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = BentoInfoBg
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .border(1.dp, BentoInfoBorder, RoundedCornerShape(14.dp))
                    .padding(bottom = 12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "📋 Studi Kasus / Skenario:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = BentoInfoTitle
                    )
                    Text(
                        text = scenarioText,
                        fontSize = 13.sp,
                        color = BentoInfoBody,
                        lineHeight = 19.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        // Question Box
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .border(1.dp, BentoBorderColor, RoundedCornerShape(18.dp))
        ) {
            Text(
                text = currentQuestion.question,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 22.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 4 Options
        val options = listOf(
            currentQuestion.optionA,
            currentQuestion.optionB,
            currentQuestion.optionC,
            currentQuestion.optionD
        )

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            options.forEachIndexed { index, optionText ->
                val isSelected = quizState.selectedOption == index
                val isCorrectOption = index == currentQuestion.correctOptionIndex

                val (bgColor, borderColor, textColor) = when {
                    quizState.isSubmitted && isCorrectOption -> Triple(
                        BentoPermittedBg,
                        BentoPermittedBorder,
                        BentoPermittedBody
                    )
                    quizState.isSubmitted && isSelected && !quizState.isCorrect -> Triple(
                        BentoProhibitedBg,
                        BentoProhibitedBorder,
                        BentoProhibitedBody
                    )
                    isSelected -> Triple(
                        BentoPaleBlue,
                        BentoPrimary,
                        BentoPrimary
                    )
                    else -> Triple(
                        MaterialTheme.colorScheme.surface,
                        BentoBorderColor,
                        MaterialTheme.colorScheme.onSurface
                    )
                }

                Surface(
                    color = bgColor,
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.2.dp, borderColor),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .clickable(enabled = !quizState.isSubmitted) {
                            viewModel.selectQuizOption(index)
                        }
                        .testTag("quiz_option_$index")
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val optionLabel = ('A' + index).toString()
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isSelected) BentoPrimary
                                    else Color(0xFFF1F5F9)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = optionLabel,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else BentoTextSecondary
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = optionText,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected || (quizState.isSubmitted && isCorrectOption)) FontWeight.Bold else FontWeight.Normal,
                            color = textColor,
                            modifier = Modifier.weight(1f)
                        )
                        if (quizState.isSubmitted) {
                            if (isCorrectOption) {
                                Icon(Icons.Default.Check, contentDescription = null, tint = BentoPermittedTitle)
                            } else if (isSelected && !quizState.isCorrect) {
                                Icon(Icons.Default.Close, contentDescription = null, tint = BentoProhibitedTitle)
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Feedback & Document Citation after submit
        if (quizState.isSubmitted) {
            val isCorrect = quizState.isCorrect
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isCorrect) BentoPermittedBg else BentoProhibitedBg
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .border(
                        1.2.dp,
                        if (isCorrect) BentoPermittedBorder else BentoProhibitedBorder,
                        RoundedCornerShape(16.dp)
                    )
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (isCorrect) Icons.Default.CheckCircle else Icons.Default.Close,
                            contentDescription = null,
                            tint = if (isCorrect) BentoPermittedTitle else BentoProhibitedTitle,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isCorrect) "Jawaban Benar! (+10 Poin)" else "Jawaban Belum Tepat",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = if (isCorrect) BentoPermittedTitle else BentoProhibitedTitle
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = currentQuestion.explanation,
                        fontSize = 13.sp,
                        color = if (isCorrect) BentoPermittedBody else BentoProhibitedBody,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "📖 ${currentQuestion.sourceCitation}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isCorrect) BentoPermittedTitle else BentoProhibitedTitle
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Action Button (Jawab / Lanjut)
        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
            if (!quizState.isSubmitted) {
                Button(
                    onClick = { viewModel.submitQuizAnswer(currentQuestion) },
                    enabled = quizState.selectedOption != null,
                    colors = ButtonDefaults.buttonColors(containerColor = BentoPrimary),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("btn_submit_quiz_answer")
                ) {
                    Text("Periksa Jawaban", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            } else {
                Button(
                    onClick = { viewModel.nextQuizQuestion(questions.size) },
                    colors = ButtonDefaults.buttonColors(containerColor = BentoPrimary),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("btn_next_quiz_question")
                ) {
                    Text(
                        text = if (quizState.currentQuestionIndex + 1 >= questions.size) "Lihat Hasil Akhir" else "Soal Berikutnya",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

@Composable
fun QuizResultScreen(
    score: Int,
    totalQuestions: Int,
    onRestart: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val maxScore = totalQuestions * 10
    val percentage = if (maxScore > 0) (score * 100) / maxScore else 0

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
                .background(BentoPaleBlue),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.EmojiEvents,
                contentDescription = null,
                tint = BentoGold,
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Kuis Selesai!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = "Skor Pemahaman Tata Tertib Anda:",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Surface(
            color = BentoPaleBlue,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.border(1.dp, Color(0xFFBFDBFE), RoundedCornerShape(16.dp))
        ) {
            Text(
                text = "$score / $maxScore ($percentage%)",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = BentoPrimary,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        val evaluation = when {
            percentage >= 80 -> "Luar Biasa! Anda memahami pedoman tata tertib SMANEKA dengan sangat baik."
            percentage >= 60 -> "Bagus! Terus pelajari modul dan bab pedoman untuk pemahaman sempurna."
            else -> "Perlu ditingkatkan. Buka kembali tab Pedoman dan Pelanggaran untuk mengenali aturan resmi."
        }

        Text(
            text = evaluation,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = onRestart,
            colors = ButtonDefaults.buttonColors(containerColor = BentoPrimary),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("btn_restart_quiz")
        ) {
            Icon(Icons.Default.Refresh, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Ulangi Kuis", fontWeight = FontWeight.Bold, color = Color.White)
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = onBack,
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("btn_back_to_menu")
        ) {
            Text("Kembali ke Beranda", color = BentoPrimary, fontWeight = FontWeight.SemiBold)
        }
    }
}
