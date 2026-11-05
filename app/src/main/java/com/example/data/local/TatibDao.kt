package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.data.model.BookmarkEntity
import com.example.data.model.HandlingProcedureEntity
import com.example.data.model.LearningTopicEntity
import com.example.data.model.QuizQuestionEntity
import com.example.data.model.ReadingHistoryEntity
import com.example.data.model.RegulationEntity
import com.example.data.model.ViolationEntity
import com.example.data.model.VisualStandardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TatibDao {

    // Regulations
    @Query("SELECT * FROM regulations ORDER BY pageNumber ASC, sectionNumber ASC")
    fun getAllRegulations(): Flow<List<RegulationEntity>>

    @Query("SELECT * FROM regulations WHERE chapterNumber = :chapterNumber ORDER BY sectionNumber ASC")
    fun getRegulationsByChapter(chapterNumber: String): Flow<List<RegulationEntity>>

    @Query("SELECT * FROM regulations WHERE id = :id LIMIT 1")
    suspend fun getRegulationById(id: String): RegulationEntity?

    @Query("""
        SELECT * FROM regulations 
        WHERE title LIKE '%' || :query || '%' 
           OR fullContent LIKE '%' || :query || '%' 
           OR summaryContent LIKE '%' || :query || '%' 
           OR keywords LIKE '%' || :query || '%' 
           OR sectionNumber LIKE '%' || :query || '%' 
           OR chapterTitle LIKE '%' || :query || '%'
        ORDER BY pageNumber ASC
    """)
    fun searchRegulations(query: String): Flow<List<RegulationEntity>>

    @Query("SELECT DISTINCT chapterNumber FROM regulations ORDER BY pageNumber ASC")
    fun getAllChapterNumbers(): Flow<List<String>>

    // Violations
    @Query("SELECT * FROM violations ORDER BY tierNumber ASC, number ASC")
    fun getAllViolations(): Flow<List<ViolationEntity>>

    @Query("SELECT * FROM violations WHERE tierNumber = :tierNumber ORDER BY number ASC")
    fun getViolationsByTier(tierNumber: Int): Flow<List<ViolationEntity>>

    @Query("SELECT * FROM violations WHERE id = :id LIMIT 1")
    suspend fun getViolationById(id: String): ViolationEntity?

    @Query("""
        SELECT * FROM violations 
        WHERE name LIKE '%' || :query || '%' 
           OR consequence LIKE '%' || :query || '%' 
           OR keywords LIKE '%' || :query || '%' 
           OR tier LIKE '%' || :query || '%' 
           OR procedure LIKE '%' || :query || '%'
        ORDER BY tierNumber ASC, number ASC
    """)
    fun searchViolations(query: String): Flow<List<ViolationEntity>>

    // Handling Procedures (SOP)
    @Query("""
        SELECT * FROM handling_procedures 
        ORDER BY 
            CASE 
                WHEN categoryName LIKE '%III%' OR severityLevel = 'Berat' THEN 3
                WHEN categoryName LIKE '%II%' OR severityLevel = 'Sedang' THEN 2
                WHEN categoryName LIKE '%I%' OR severityLevel = 'Ringan' THEN 1
                ELSE 4 
            END ASC,
            id ASC
    """)
    fun getAllHandlingProcedures(): Flow<List<HandlingProcedureEntity>>

    // Visual Standards
    @Query("SELECT * FROM visual_standards ORDER BY category ASC")
    fun getAllVisualStandards(): Flow<List<VisualStandardEntity>>

    @Query("SELECT * FROM visual_standards WHERE category = :category")
    fun getVisualStandardsByCategory(category: String): Flow<List<VisualStandardEntity>>

    // Learning Topics & Quizzes
    @Query("SELECT * FROM learning_topics ORDER BY topicNumber ASC")
    fun getAllLearningTopics(): Flow<List<LearningTopicEntity>>

    @Query("SELECT * FROM learning_topics WHERE id = :id LIMIT 1")
    suspend fun getLearningTopicById(id: String): LearningTopicEntity?

    @Query("UPDATE learning_topics SET isCompleted = :completed WHERE id = :id")
    suspend fun updateTopicCompletion(id: String, completed: Boolean)

    @Query("SELECT * FROM quiz_questions ORDER BY id ASC")
    fun getAllQuizQuestions(): Flow<List<QuizQuestionEntity>>

    @Query("SELECT * FROM quiz_questions WHERE topicId = :topicId")
    fun getQuizQuestionsByTopic(topicId: String): Flow<List<QuizQuestionEntity>>

    // Bookmarks
    @Query("SELECT * FROM bookmarks ORDER BY savedAt DESC")
    fun getAllBookmarks(): Flow<List<BookmarkEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE id = :id)")
    fun isBookmarkedFlow(id: String): Flow<Boolean>

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE id = :id)")
    suspend fun isBookmarked(id: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBookmark(bookmark: BookmarkEntity)

    @Query("DELETE FROM bookmarks WHERE id = :id")
    suspend fun removeBookmark(id: String)

    // Reading History
    @Query("SELECT * FROM reading_history ORDER BY lastReadAt DESC LIMIT 10")
    fun getRecentReadingHistory(): Flow<List<ReadingHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateReadingHistory(history: ReadingHistoryEntity)

    @Query("DELETE FROM reading_history")
    suspend fun clearReadingHistory()

    // Seeding & Bulk Inserts
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRegulations(items: List<RegulationEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertViolations(items: List<ViolationEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProcedures(items: List<HandlingProcedureEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStandards(items: List<VisualStandardEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLearningTopics(items: List<LearningTopicEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizQuestions(items: List<QuizQuestionEntity>)

    @Query("SELECT COUNT(*) FROM regulations")
    suspend fun getRegulationsCount(): Int

    @Query("SELECT COUNT(*) FROM violations")
    suspend fun getViolationsCount(): Int
}
