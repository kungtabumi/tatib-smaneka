package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "regulations")
data class RegulationEntity(
    @PrimaryKey val id: String,
    val chapterNumber: String, // "BAB 1", "BAB 2", etc.
    val chapterTitle: String, // "DASAR HUKUM", "HAL MASUK SEKOLAH", "KEWAJIBAN MURID", etc.
    val sectionNumber: String, // "1.1", "2.1", "3.4.A", etc.
    val title: String,
    val fullContent: String,
    val summaryContent: String,
    val category: String, // "Dasar Hukum", "Masuk Sekolah", "Kewajiban", "Seragam", "Larangan", "Hak", "Penanganan", "Lain-lain"
    val pageNumber: Int,
    val keywords: String
)

@Entity(tableName = "violations")
data class ViolationEntity(
    @PrimaryKey val id: String, // "V1_1", "V2_1", "V3_1"
    val tier: String, // "Pelanggaran I (Ringan)", "Pelanggaran II (Sedang)", "Pelanggaran III (Berat)"
    val tierNumber: Int, // 1, 2, 3
    val number: Int,
    val name: String,
    val consequence: String,
    val procedure: String,
    val sourceChapter: String, // "BENTUK-BENTUK PELANGGARAN MURID A. PELANGGARAN I"
    val pageNumber: Int,
    val keywords: String,
    val applicableTo: String = "Semua Murid" // "Semua Murid", "Putra", "Putri", "Kelas X"
)

@Entity(tableName = "handling_procedures")
data class HandlingProcedureEntity(
    @PrimaryKey val id: String,
    val categoryName: String,
    val severityLevel: String, // "Ringan", "Sedang", "Berat"
    val stage: String, // "Tindakan Awal", "Pelanggaran Lanjutan (Berulang)", "Pelanggaran > 5 Kali", "Penanganan Kasus Khusus"
    val steps: String,
    val actorsInvolved: String, // "Tim Tatib, Guru Piket, Guru Wali", "Wali Kelas, BK, Orang Tua", etc.
    val expectedOutcome: String,
    val pageNumber: Int
)

@Entity(tableName = "visual_standards")
data class VisualStandardEntity(
    @PrimaryKey val id: String,
    val title: String,
    val subtitle: String,
    val category: String, // "Seragam", "Sepatu & Kaos Kaki", "Rambut Putra", "Rambut Putri", "Kuku & Aksesoris", "Gadget & HP"
    val permittedRule: String,
    val prohibitedRule: String,
    val details: String,
    val sourceReference: String,
    val pageNumber: Int
)

@Entity(tableName = "learning_topics")
data class LearningTopicEntity(
    @PrimaryKey val id: String,
    val topicNumber: Int,
    val title: String,
    val category: String,
    val summary: String,
    val keyPoints: String, // JSON or newline separated
    val fullReference: String,
    val sourceSection: String,
    val pageNumber: Int,
    val isCompleted: Boolean = false
)

@Entity(tableName = "quiz_questions")
data class QuizQuestionEntity(
    @PrimaryKey val id: String,
    val topicId: String,
    val questionType: String, // "MULTIPLE_CHOICE", "TRUE_FALSE", "SCENARIO"
    val scenario: String? = null,
    val question: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctOptionIndex: Int, // 0 for A, 1 for B, 2 for C, 3 for D
    val explanation: String,
    val sourceCitation: String
)

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey val id: String, // e.g. "REG_3_4", "VIOL_1_1"
    val itemType: String, // "REGULATION", "VIOLATION", "STANDARD", "TOPIC"
    val title: String,
    val subtitle: String,
    val category: String,
    val savedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "reading_history")
data class ReadingHistoryEntity(
    @PrimaryKey val id: String,
    val title: String,
    val subtitle: String,
    val category: String,
    val route: String,
    val lastReadAt: Long = System.currentTimeMillis()
)
