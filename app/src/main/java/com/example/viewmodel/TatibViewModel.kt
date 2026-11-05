package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ai.AiAnswer
import com.example.data.ai.TatibAiEngine
import com.example.data.local.AppDatabase
import com.example.data.model.BookmarkEntity
import com.example.data.model.HandlingProcedureEntity
import com.example.data.model.LearningTopicEntity
import com.example.data.model.QuizQuestionEntity
import com.example.data.model.ReadingHistoryEntity
import com.example.data.model.RegulationEntity
import com.example.data.model.ViolationEntity
import com.example.data.model.VisualStandardEntity
import com.example.data.repository.TatibRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ChatMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val sender: MessageSender,
    val text: String,
    val sourceChapter: String? = null,
    val sourceSection: String? = null,
    val sourcePage: Int? = null,
    val targetRoute: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)

enum class MessageSender {
    USER, AI
}

data class QuizSessionState(
    val currentQuestionIndex: Int = 0,
    val selectedOption: Int? = null,
    val isSubmitted: Boolean = false,
    val isCorrect: Boolean = false,
    val score: Int = 0,
    val isFinished: Boolean = false
)

class TatibViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application, viewModelScope)
    private val repository = TatibRepository(database.tatibDao())
    private val aiEngine = TatibAiEngine()

    // Base Data Flows
    val allRegulations: StateFlow<List<RegulationEntity>> = repository.allRegulations
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allViolations: StateFlow<List<ViolationEntity>> = repository.allViolations
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allProcedures: StateFlow<List<HandlingProcedureEntity>> = repository.allProcedures
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allStandards: StateFlow<List<VisualStandardEntity>> = repository.allStandards
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allLearningTopics: StateFlow<List<LearningTopicEntity>> = repository.allLearningTopics
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allQuizQuestions: StateFlow<List<QuizQuestionEntity>> = repository.allQuizQuestions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val bookmarks: StateFlow<List<BookmarkEntity>> = repository.allBookmarks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val readingHistory: StateFlow<List<ReadingHistoryEntity>> = repository.recentReadingHistory
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // UI States
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedChapter = MutableStateFlow("Semua BAB")
    val selectedChapter: StateFlow<String> = _selectedChapter.asStateFlow()

    private val _selectedViolationTier = MutableStateFlow(0) // 0 = Semua, 1 = I (Ringan), 2 = II (Sedang), 3 = III (Berat)
    val selectedViolationTier: StateFlow<Int> = _selectedViolationTier.asStateFlow()

    // AI Chat State
    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(
        listOf(
            ChatMessage(
                sender = MessageSender.AI,
                text = "Halo! Saya Tatib Assistant SMANEKA. Ada yang ingin kamu tanyakan mengenai peraturan sekolah, seragam, jam belajar, atau sanksi pelanggaran?",
                sourceChapter = "Pedoman Tatib SMANEKA 2026/2027",
                sourceSection = "Resmi",
                sourcePage = 1
            )
        )
    )
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _isAiThinking = MutableStateFlow(false)
    val isAiThinking: StateFlow<Boolean> = _isAiThinking.asStateFlow()

    // Quiz State
    private val _quizState = MutableStateFlow(QuizSessionState())
    val quizState: StateFlow<QuizSessionState> = _quizState.asStateFlow()

    // Filtered Regulations
    val filteredRegulations: StateFlow<List<RegulationEntity>> = combine(
        allRegulations,
        _searchQuery,
        _selectedChapter
    ) { list, query, chapter ->
        val q = query.trim().lowercase()
        list.filter { item ->
            val matchChapter = if (chapter == "Semua BAB") true else item.chapterNumber == chapter
            val matchQuery = if (q.isBlank()) true else {
                item.title.lowercase().contains(q) ||
                item.fullContent.lowercase().contains(q) ||
                item.summaryContent.lowercase().contains(q) ||
                item.keywords.lowercase().contains(q) ||
                item.sectionNumber.lowercase().contains(q)
            }
            matchChapter && matchQuery
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Filtered Violations
    val filteredViolations: StateFlow<List<ViolationEntity>> = combine(
        allViolations,
        _searchQuery,
        _selectedViolationTier
    ) { list, query, tier ->
        val q = query.trim().lowercase()
        list.filter { item ->
            val matchTier = if (tier == 0) true else item.tierNumber == tier
            val matchQuery = if (q.isBlank()) true else {
                item.name.lowercase().contains(q) ||
                item.consequence.lowercase().contains(q) ||
                item.procedure.lowercase().contains(q) ||
                item.keywords.lowercase().contains(q) ||
                item.tier.lowercase().contains(q)
            }
            matchTier && matchQuery
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Actions
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSelectedChapter(chapter: String) {
        _selectedChapter.value = chapter
    }

    fun setSelectedViolationTier(tier: Int) {
        _selectedViolationTier.value = tier
    }

    fun toggleBookmark(
        id: String,
        itemType: String,
        title: String,
        subtitle: String,
        category: String
    ) {
        viewModelScope.launch {
            repository.toggleBookmark(id, itemType, title, subtitle, category)
        }
    }

    fun isItemBookmarked(id: String): Boolean {
        return bookmarks.value.any { it.id == id }
    }

    fun recordReading(id: String, title: String, subtitle: String, category: String, route: String) {
        viewModelScope.launch {
            repository.recordReadingHistory(id, title, subtitle, category, route)
        }
    }

    fun askAiAssistant(question: String) {
        val q = question.trim()
        if (q.isBlank()) return

        val userMsg = ChatMessage(
            sender = MessageSender.USER,
            text = q
        )
        _chatMessages.value = _chatMessages.value + userMsg
        _isAiThinking.value = true

        viewModelScope.launch {
            val answer: AiAnswer = aiEngine.askTatib(q)
            val aiMsg = ChatMessage(
                sender = MessageSender.AI,
                text = answer.text,
                sourceChapter = answer.sourceChapter,
                sourceSection = answer.sourceSection,
                sourcePage = answer.sourcePage,
                targetRoute = answer.targetRoute
            )
            _chatMessages.value = _chatMessages.value + aiMsg
            _isAiThinking.value = false
        }
    }

    fun clearChat() {
        _chatMessages.value = listOf(
            ChatMessage(
                sender = MessageSender.AI,
                text = "Percakapan dibersihkan. Ada hal lain mengenai aturan SMANEKA yang ingin kamu ketahui?",
                sourceChapter = "Pedoman Tatib SMANEKA",
                sourceSection = "Resmi",
                sourcePage = 1
            )
        )
    }

    // Quiz Actions
    fun selectQuizOption(index: Int) {
        if (_quizState.value.isSubmitted) return
        _quizState.value = _quizState.value.copy(selectedOption = index)
    }

    fun submitQuizAnswer(currentQuestion: QuizQuestionEntity) {
        val selected = _quizState.value.selectedOption ?: return
        val isCorrect = selected == currentQuestion.correctOptionIndex
        val newScore = if (isCorrect) _quizState.value.score + 10 else _quizState.value.score

        _quizState.value = _quizState.value.copy(
            isSubmitted = true,
            isCorrect = isCorrect,
            score = newScore
        )
    }

    fun nextQuizQuestion(totalQuestions: Int) {
        val nextIdx = _quizState.value.currentQuestionIndex + 1
        if (nextIdx >= totalQuestions) {
            _quizState.value = _quizState.value.copy(isFinished = true)
        } else {
            _quizState.value = _quizState.value.copy(
                currentQuestionIndex = nextIdx,
                selectedOption = null,
                isSubmitted = false,
                isCorrect = false
            )
        }
    }

    fun resetQuiz() {
        _quizState.value = QuizSessionState()
    }
}
