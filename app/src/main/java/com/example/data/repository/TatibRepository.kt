package com.example.data.repository

import com.example.data.local.TatibDao
import com.example.data.model.BookmarkEntity
import com.example.data.model.HandlingProcedureEntity
import com.example.data.model.LearningTopicEntity
import com.example.data.model.QuizQuestionEntity
import com.example.data.model.ReadingHistoryEntity
import com.example.data.model.RegulationEntity
import com.example.data.model.ViolationEntity
import com.example.data.model.VisualStandardEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TatibRepository(private val dao: TatibDao) {

    val allRegulations: Flow<List<RegulationEntity>> = dao.getAllRegulations()
    val allViolations: Flow<List<ViolationEntity>> = dao.getAllViolations()
    val allProcedures: Flow<List<HandlingProcedureEntity>> = dao.getAllHandlingProcedures()
    val allStandards: Flow<List<VisualStandardEntity>> = dao.getAllVisualStandards()
    val allLearningTopics: Flow<List<LearningTopicEntity>> = dao.getAllLearningTopics()
    val allQuizQuestions: Flow<List<QuizQuestionEntity>> = dao.getAllQuizQuestions()
    val allBookmarks: Flow<List<BookmarkEntity>> = dao.getAllBookmarks()
    val recentReadingHistory: Flow<List<ReadingHistoryEntity>> = dao.getRecentReadingHistory()

    fun getRegulationsByChapter(chapter: String): Flow<List<RegulationEntity>> =
        dao.getRegulationsByChapter(chapter)

    fun getViolationsByTier(tierNumber: Int): Flow<List<ViolationEntity>> =
        dao.getViolationsByTier(tierNumber)

    fun getVisualStandardsByCategory(category: String): Flow<List<VisualStandardEntity>> =
        dao.getVisualStandardsByCategory(category)

    fun isBookmarkedFlow(id: String): Flow<Boolean> = dao.isBookmarkedFlow(id)

    suspend fun toggleBookmark(
        id: String,
        itemType: String,
        title: String,
        subtitle: String,
        category: String
    ) {
        if (dao.isBookmarked(id)) {
            dao.removeBookmark(id)
        } else {
            dao.addBookmark(
                BookmarkEntity(
                    id = id,
                    itemType = itemType,
                    title = title,
                    subtitle = subtitle,
                    category = category
                )
            )
        }
    }

    suspend fun recordReadingHistory(
        id: String,
        title: String,
        subtitle: String,
        category: String,
        route: String
    ) {
        dao.insertOrUpdateReadingHistory(
            ReadingHistoryEntity(
                id = id,
                title = title,
                subtitle = subtitle,
                category = category,
                route = route,
                lastReadAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun updateTopicProgress(topicId: String, completed: Boolean) {
        dao.updateTopicCompletion(topicId, completed)
    }

    fun smartSearchRegulations(query: String): Flow<List<RegulationEntity>> {
        val cleanQuery = query.trim().lowercase()
        return dao.getAllRegulations().map { list ->
            if (cleanQuery.isBlank()) list
            else {
                list.filter { item ->
                    item.title.lowercase().contains(cleanQuery) ||
                    item.fullContent.lowercase().contains(cleanQuery) ||
                    item.summaryContent.lowercase().contains(cleanQuery) ||
                    item.keywords.lowercase().contains(cleanQuery) ||
                    item.chapterNumber.lowercase().contains(cleanQuery) ||
                    item.sectionNumber.lowercase().contains(cleanQuery) ||
                    matchesSynonyms(cleanQuery, item.keywords)
                }
            }
        }
    }

    fun smartSearchViolations(query: String): Flow<List<ViolationEntity>> {
        val cleanQuery = query.trim().lowercase()
        return dao.getAllViolations().map { list ->
            if (cleanQuery.isBlank()) list
            else {
                list.filter { item ->
                    item.name.lowercase().contains(cleanQuery) ||
                    item.consequence.lowercase().contains(cleanQuery) ||
                    item.procedure.lowercase().contains(cleanQuery) ||
                    item.keywords.lowercase().contains(cleanQuery) ||
                    item.tier.lowercase().contains(cleanQuery) ||
                    matchesSynonyms(cleanQuery, item.keywords)
                }
            }
        }
    }

    private fun matchesSynonyms(query: String, keywords: String): Boolean {
        val kw = keywords.lowercase()
        val synonymMap = mapOf(
            "baju" to listOf("seragam", "pakaian"),
            "pakaian" to listOf("seragam", "baju", "batik"),
            "celana" to listOf("seragam", "rok", "pakaian"),
            "sepatu" to listOf("alas kaki", "doff", "flatshoes"),
            "kets" to listOf("sepatu"),
            "kaos kaki" to listOf("putih polos", "hitam polos", "15 cm"),
            "kaoskaki" to listOf("kaos kaki"),
            "rambut" to listOf("potongan 2-2-1", "gondrong", "cat warna", "kuncir"),
            "cukur" to listOf("rambut", "potongan 2-2-1", "alis"),
            "telat" to listOf("terlambat", "06.45", "piket tatib"),
            "bolos" to listOf("meninggalkan kelas", "tanpa izin", "kantin"),
            "hp" to listOf("smartphone", "kotak penyimpanan", "kbm"),
            "handphone" to listOf("hp", "kotak penyimpanan"),
            "rokok" to listOf("vape", "nikotin", "pod"),
            "vape" to listOf("rokok", "nikotin"),
            "judi" to listOf("taruhan", "online", "kartu"),
            "sanksi" to listOf("konsekuensi", "pembinaan", "surat komitmen", "skorsing"),
            "poin" to listOf("pelanggaran i", "pelanggaran ii", "pelanggaran iii", "tingkat pelanggaran"),
            "hukum" to listOf("dasar hukum", "uud 1945", "uu 20 2003"),
            "guru wali" to listOf("pembinaan individual", "pelanggaran i"),
            "bk" to listOf("bimbingan konseling", "pemanggilan", "pembinaan edukatif")
        )

        for ((synKey, synValues) in synonymMap) {
            if (query.contains(synKey)) {
                for (target in synValues) {
                    if (kw.contains(target)) return true
                }
            }
        }
        return false
    }
}
