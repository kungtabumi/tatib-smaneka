package com.example.data.ai

import com.example.BuildConfig
import com.example.data.local.TatibDatabaseSeeder
import com.example.data.model.HandlingProcedureEntity
import com.example.data.model.RegulationEntity
import com.example.data.model.ViolationEntity
import com.example.data.model.VisualStandardEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit
import kotlin.math.max
import kotlin.math.min

// Models for Gemini REST API
data class GeminiRequest(
    val contents: List<GeminiContent>,
    val systemInstruction: GeminiContent? = null,
    val generationConfig: GeminiGenerationConfig? = null
)

data class GeminiContent(
    val parts: List<GeminiPart>,
    val role: String? = null
)

data class GeminiPart(
    val text: String
)

data class GeminiGenerationConfig(
    val temperature: Float = 0.2f,
    val topP: Float = 0.8f,
    val maxOutputTokens: Int = 1000
)

data class GeminiResponse(
    val candidates: List<GeminiCandidate>? = null
)

data class GeminiCandidate(
    val content: GeminiContentResponse? = null
)

data class GeminiContentResponse(
    val parts: List<GeminiPartResponse>? = null
)

data class GeminiPartResponse(
    val text: String? = null
)

interface GeminiRestService {
    @POST("v1beta/models/gemini-3.5-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): GeminiResponse
}

data class AiAnswer(
    val text: String,
    val sourceChapter: String?,
    val sourceSection: String?,
    val sourcePage: Int?,
    val isGrounded: Boolean = true,
    val targetRoute: String? = null
)

class TatibAiEngine {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        })
        .build()

    private val geminiService: GeminiRestService by lazy {
        Retrofit.Builder()
            .baseUrl("https://generativelanguage.googleapis.com/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(GeminiRestService::class.java)
    }

    private val fullDocumentKnowledgeBase: String by lazy {
        buildString {
            appendLine("DOKUMEN UTAMA: TATA TERTIB MURID SMA NEGERI 1 KEPANJEN TAHUN AJARAN 2026 / 2027")
            appendLine("Ditetapkan di Kepanjen, 13 Juli 2026 oleh Kepala SMA Negeri 1 Kepanjen: FADILAH UMI MAISYAROH, M.Pd (Pembina Utama Muda - NIP. 19750827 200012 2 003).")
            appendLine()
            appendLine("--- PERATURAN PER BAB (BAB 1 s.d BAB 7) ---")
            TatibDatabaseSeeder.regulations.forEach { r ->
                appendLine("[${r.chapterNumber} ${r.chapterTitle} - Poin ${r.sectionNumber}] (Halaman ${r.pageNumber}): ${r.title}")
                appendLine(r.fullContent)
                appendLine()
            }
            appendLine("--- 51 BENTUK PELANGGARAN ---")
            TatibDatabaseSeeder.violations.forEach { v ->
                appendLine("[${v.tier} No. ${v.number}] (Halaman ${v.pageNumber}): ${v.name}")
                appendLine("Konsekuensi: ${v.consequence}")
                appendLine("Prosedur: ${v.procedure}")
                appendLine()
            }
            appendLine("--- STANDAR VISUAL & PENAMPILAN ---")
            TatibDatabaseSeeder.visualStandards.forEach { s ->
                appendLine("[${s.category} - ${s.title}] (Halaman ${s.pageNumber}):")
                appendLine("Boleh: ${s.permittedRule}")
                appendLine("Dilarang: ${s.prohibitedRule}")
                appendLine("Detail: ${s.details}")
                appendLine()
            }
            appendLine("--- SOP ALUR PENANGANAN PELANGGARAN ---")
            TatibDatabaseSeeder.handlingProcedures.forEach { s ->
                appendLine("[${s.categoryName} - ${s.stage}] (Halaman ${s.pageNumber}):")
                appendLine(s.steps)
                appendLine("Pihak terlibat: ${s.actorsInvolved}")
                appendLine()
            }
        }
    }

    suspend fun askTatib(question: String): AiAnswer = withContext(Dispatchers.IO) {
        val rawQuery = question.trim()
        if (rawQuery.isBlank()) {
            return@withContext AiAnswer(
                text = "Silakan ketik pertanyaan mengenai Tata Tertib SMANEKA 2026/2027.",
                sourceChapter = null,
                sourceSection = null,
                sourcePage = null,
                isGrounded = false
            )
        }

        val normalized = normalizeQuery(rawQuery)

        // 1. Try smart typo-tolerant direct intent matcher first for instant accurate answers
        val directMatch = tryDirectIntentMatch(normalized, rawQuery)
        if (directMatch != null) {
            return@withContext directMatch
        }

        // 2. Check if Gemini API is available and callable
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Exception) {
            ""
        }

        if (apiKey.isNotBlank() && apiKey != "MY_GEMINI_API_KEY") {
            try {
                val systemPrompt = """Anda adalah Tatib Assistant resmi SMA Negeri 1 Kepanjen (SMANEKA) Tahun Ajaran 2026/2027.
TUGAS ANDA:
Menjawab pertanyaan murid, orang tua, atau guru hanya berdasarkan DOKUMEN TATA TERTIB SMANEKA TAHUN AJARAN 2026/2027 yang disediakan di bawah ini. Pahami segala bentuk salah ketik (typo), singkatan, maupun bahasa santai pengguna dengan cerdas.

ATURAN KETAT:
1. Hanya gunakan informasi yang benar-benar tercantum dalam dokumen ini.
2. Jangan pernah mengarang aturan, sanksi, jam, seragam, atau angka yang tidak ada dalam dokumen.
3. Sebutkan BAB, Poin/Pasal, dan Halaman sumber pada akhir jawaban dengan format:
   📖 Sumber: BAB ... Poin ... (Halaman ...)
4. Jika pertanyaan tidak diatur dalam dokumen, jawablah dengan sopan: "Maaf, ketentuan tersebut tidak ditemukan dalam Pedoman Tata Tertib SMANEKA yang tersedia."
5. Jawaban harus ramah, terstruktur (gunakan bullet point jika relevan), jelas, dan edukatif."""

                val request = GeminiRequest(
                    systemInstruction = GeminiContent(
                        parts = listOf(GeminiPart(text = "$systemPrompt\n\n=== DOKUMEN TATIB SMANEKA ===\n$fullDocumentKnowledgeBase"))
                    ),
                    contents = listOf(
                        GeminiContent(
                            role = "user",
                            parts = listOf(GeminiPart(text = rawQuery))
                        )
                    ),
                    generationConfig = GeminiGenerationConfig(
                        temperature = 0.2f,
                        topP = 0.8f,
                        maxOutputTokens = 800
                    )
                )

                val response = geminiService.generateContent(apiKey, request)
                val responseText = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text

                if (!responseText.isNullOrBlank()) {
                    val citation = extractCitation(responseText)
                    return@withContext AiAnswer(
                        text = responseText,
                        sourceChapter = citation.first ?: "Dokumen Tatib SMANEKA",
                        sourceSection = citation.second ?: "Resmi 2026/2027",
                        sourcePage = citation.third ?: 1,
                        isGrounded = true,
                        targetRoute = "pedoman"
                    )
                }
            } catch (e: Exception) {
                // If network/API error or timeout, smoothly fall back to our advanced typo-tolerant RAG engine
            }
        }

        // 3. Fallback to advanced Typo-Tolerant & Fuzzy N-Gram Knowledge Base Retrieval
        return@withContext performSmartFuzzyRagRetrieval(normalized, rawQuery)
    }

    /**
     * Normalizes Indonesian slang, abbreviations, and common typo patterns.
     */
    private fun normalizeQuery(input: String): String {
        var text = input.lowercase().trim()

        val replacements = mapOf(
            // Slang / informal
            Regex("\\byg\\b") to "yang",
            Regex("\\bdgn\\b") to "dengan",
            Regex("\\butk\\b") to "untuk",
            Regex("\\bklo\\b|\\bkalo\\b|\\bkalau\\b") to "kalau",
            Regex("\\bgmn\\b|\\bgimana\\b") to "bagaimana",
            Regex("\\bapkh\\b") to "apakah",
            Regex("\\btdk\\b|\\bga\\b|\\bgak\\b|\\bnggak\\b|\\bndak\\b") to "tidak",
            Regex("\\bblh\\b") to "boleh",
            Regex("\\bpke\\b|\\bpake\\b|\\bmake\\b") to "memakai",
            Regex("\\bbwa\\b|\\bbawa\\b") to "membawa",
            Regex("\\bbju\\b|\\bbaju\\b") to "baju",

            // Seragam & hari typos
            Regex("\\bsragam\\b|\\bsragamnya\\b|\\bsregam\\b|\\bseragm\\b|\\bsrgham\\b") to "seragam",
            Regex("\\bsenen\\b|\\bsnen\\b") to "senin",
            Regex("\\bseloso\\b|\\bslasa\\b") to "selasa",
            Regex("\\brebo\\b|\\brbu\\b") to "rabu",
            Regex("\\bkemis\\b|\\bkmis\\b") to "kamis",
            Regex("\\bjumatan\\b|\\bjumat\\b|\\bjum'at\\b|\\bjmt\\b") to "jumat",

            // Keterlambatan typos
            Regex("\\bterlmabat\\b|\\btelad\\b|\\btelat\\b|\\btlmbat\\b|\\btlat\\b|\\bktrlambatan\\b") to "terlambat",

            // Sepatu & Kaos kaki typos
            Regex("\\bspatu\\b|\\bsepatuu\\b|\\bspatunya\\b|\\bspt\\b|\\bsneaker\\b|\\bsneakers\\b") to "sepatu",
            Regex("\\bkaoskaki\\b|\\bkaos kki\\b|\\bkaoskki\\b|\\bkaos kakii\\b") to "kaos kaki",
            Regex("\\bdoff\\b|\\bdof\\b|\\bmatte\\b|\\bmat\\b") to "doff",
            Regex("\\bglossy\\b|\\bglosi\\b|\\bmengkilap\\b") to "glossy",
            Regex("\\bputihh\\b|\\bputih polos\\b") to "putih",
            Regex("\\bhitamm\\b|\\bhitam polos\\b") to "hitam",

            // Rambut & Penampilan typos
            Regex("\\brmbut\\b|\\brambt\\b|\\brambutnya\\b") to "rambut",
            Regex("\\bcukur\\b|\\bpotong\\b|\\bpotongan\\b") to "potongan",
            Regex("\\b221\\b|\\b2 2 1\\b|\\bdua dua satu\\b|\\b2-2-1\\b") to "2-2-1",
            Regex("\\bgondrong\\b|\\bgondronk\\b|\\bpanjang\\b") to "gondrong",
            Regex("\\bdiwarna\\b|\\bsemir\\b|\\bcat rambut\\b|\\bcat\\b|\\bwarnai\\b") to "cat rambut",
            Regex("\\bundercut\\b|\\btato rambut\\b|\\bgaris rambut\\b") to "undercut",

            // Gender
            Regex("\\bcwo\\b|\\bcowo\\b|\\bcowok\\b|\\blaki\\b|\\blakilaki\\b|\\blaki-laki\\b|\\bpria\\b|\\bputra\\b") to "putra",
            Regex("\\bcwe\\b|\\bcewe\\b|\\bcewek\\b|\\bwanita\\b|\\bperempuan\\b|\\bputri\\b") to "putri",
            Regex("\\bhijab\\b|\\bjilbab\\b|\\bkerudung\\b") to "jilbab",

            // Gadget & Rokok & Pelanggaran
            Regex("\\bhape\\b|\\bponsel\\b|\\bgadget\\b|\\bgawai\\b|\\bhandphone\\b") to "hp",
            Regex("\\bvep\\b|\\bvpe\\b|\\bvape\\b|\\bpod\\b|\\bliquid\\b|\\bvaping\\b") to "vape",
            Regex("\\broko\\b|\\brokok\\b|\\bmerokok\\b|\\bngerokok\\b") to "rokok",
            Regex("\\bbuly\\b|\\bbulying\\b|\\bbuliying\\b|\\bbullying\\b|\\bperundungan\\b|\\bmalak\\b|\\bdipalak\\b") to "bullying",
            Regex("\\bberantem\\b|\\btawuran\\b|\\bkelahi\\b|\\bberkelahi\\b|\\btarung\\b|\\bjotos\\b") to "perkelahian",
            Regex("\\bknlpot\\b|\\bkenalpot\\b|\\bbrong\\b|\\bbising\\b") to "knalpot brong",
            Regex("\\bjudol\\b|\\bjudi\\b|\\bslot\\b|\\btaruhan\\b") to "judi",
            Regex("\\bbolos\\b|\\bminggat\\b|\\bkabur\\b|\\balpa\\b") to "bolos",
            Regex("\\btato\\b|\\btattoo\\b|\\btindik\\b|\\banting\\b|\\bkalung\\b|\\bgelang\\b") to "aksesoris",
            Regex("\\bskors\\b|\\bskorsing\\b|\\bdikeluarkan\\b|\\bdrop out\\b|\\bdo\\b") to "skorsing",
            Regex("\\bsakit\\b|\\bskit\\b|\\bsuratsakit\\b") to "sakit",
            Regex("\\bortu\\b|\\borangtua\\b") to "orang tua",
            Regex("\\bkasek\\b|\\bkepsek\\b|\\bkepala sekolah\\b") to "kepala sekolah"
        )

        for ((regex, rep) in replacements) {
            text = text.replace(regex, rep)
        }

        return text
    }

    /**
     * Calculates Levenshtein distance between two strings for typo tolerance.
     */
    private fun levenshteinDistance(s1: String, s2: String): Int {
        val dp = Array(s1.length + 1) { IntArray(s2.length + 1) }
        for (i in 0..s1.length) dp[i][0] = i
        for (j in 0..s2.length) dp[0][j] = j

        for (i in 1..s1.length) {
            for (j in 1..s2.length) {
                val cost = if (s1[i - 1] == s2[j - 1]) 0 else 1
                dp[i][j] = min(
                    dp[i - 1][j] + 1,
                    min(dp[i][j - 1] + 1, dp[i - 1][j - 1] + cost)
                )
            }
        }
        return dp[s1.length][s2.length]
    }

    /**
     * Checks if a word is fuzzy-similar to a target keyword (allowing 1-2 character typos).
     */
    private fun isFuzzyMatch(word: String, target: String): Boolean {
        if (word == target) return true
        val wLen = word.length
        val tLen = target.length
        if (wLen < 3 || tLen < 3) return word == target

        // Check stem / prefix for inflected words (e.g. seragam -> seragamnya, sepatu -> sepatunya)
        if (wLen >= 5 && tLen >= 5) {
            if (word.startsWith(target) && (wLen - tLen <= 3)) return true
            if (target.startsWith(word) && (tLen - wLen <= 3)) return true
        }

        val maxLen = max(wLen, tLen)
        val dist = levenshteinDistance(word, target)
        return when {
            maxLen in 3..4 -> dist == 0
            maxLen in 5..7 -> dist <= 1
            else -> dist <= 2
        }
    }

    /**
     * Checks if any token in the query fuzzy-matches any keyword in the target list.
     */
    private fun hasAnyFuzzyMatch(tokens: List<String>, targets: List<String>): Boolean {
        return tokens.any { token ->
            targets.any { target -> isFuzzyMatch(token, target) }
        }
    }

    /**
     * Smart Typo-Tolerant Intent Matcher.
     */
    private fun tryDirectIntentMatch(norm: String, raw: String): AiAnswer? {
        val tokens = norm.split(Regex("[^a-zA-Z0-9\\-]+")).filter { it.isNotBlank() }

        // 1. Waktu Masuk, Bel, & Jam Belajar
        val isTimeQuery = hasAnyFuzzyMatch(tokens, listOf("jam", "waktu", "pukul", "bel", "masuk", "pulang", "jadwal", "menit"))
        if (isTimeQuery && (norm.contains("masuk") || norm.contains("bel") || norm.contains("hadir") || norm.contains("mulai"))) {
            return AiAnswer(
                text = "⏰ **Waktu Hadir & Jam Masuk SMAN 1 Kepanjen**:\n\n" +
                        "• **Bel Masuk**: Berbunyi pukul **06.45 WIB**.\n" +
                        "• **Kewajiban Kehadiran**: Murid wajib hadir di sekolah paling lambat **15 menit sebelum bel masuk (pukul 06.30 WIB)**.\n" +
                        "• **Khusus Hari Jum'at**: Kegiatan diawali dengan kegiatan keagamaan / literasi / senam, dan KBM dimulai pukul **07.30 WIB**.",
                sourceChapter = "BAB 2. HAL MASUK SEKOLAH",
                sourceSection = "Poin 2.1 & 2.3",
                sourcePage = 1,
                targetRoute = "pedoman"
            )
        }

        if (isTimeQuery && (norm.contains("pulang") || norm.contains("belajar") || norm.contains("jadwal") || norm.contains("kbm"))) {
            return AiAnswer(
                text = "📅 **Jadwal Jam Belajar Resmi SMAN 1 Kepanjen 2026/2027**:\n\n" +
                        "• **Senin** : Pukul 06.45 – 15.05 WIB (Diawali Upacara Bendera)\n" +
                        "• **Selasa s.d. Kamis** : Pukul 06.45 – 15.15 WIB\n" +
                        "• **Jum'at** : Pukul 07.30 – 14.40 WIB",
                sourceChapter = "BAB 2. HAL MASUK SEKOLAH",
                sourceSection = "Poin 2.3",
                sourcePage = 1,
                targetRoute = "pedoman"
            )
        }

        // 2. Keterlambatan
        if (hasAnyFuzzyMatch(tokens, listOf("terlambat", "telat", "keterlambatan"))) {
            return AiAnswer(
                text = "⏳ **Prosedur & Sanksi Keterlambatan Murid**:\n\n" +
                        "1. **Terlambat < 15 Menit**:\n" +
                        "   • Wajib melapor kepada Guru Piket / Tim Ketertiban di gerbang.\n" +
                        "   • Mendapatkan Surat Izin Masuk Kelas sebelum mengikuti KBM.\n\n" +
                        "2. **Terlambat > 15 Menit**:\n" +
                        "   • **Tidak diperkenankan masuk pada jam pelajaran pertama**.\n" +
                        "   • Diberikan tugas pembinaan edukatif/sosial di ruang Tim Tatib atau Perpustakaan.\n" +
                        "   • Baru diizinkan masuk kelas pada jam pelajaran kedua setelah ada rekomendasi Guru Piket.\n\n" +
                        "⚠️ Terlambat merupakan **Pelanggaran Kategori I (Ringan No. 1-2)**. Jika berulang > 5 kali, Orang Tua/Wali akan dipanggil resmi ke sekolah.",
                sourceChapter = "BAB 2. HAL MASUK SEKOLAH & PELANGGARAN I",
                sourceSection = "Poin 2.2 & Pelanggaran No. 1-2",
                sourcePage = 1,
                targetRoute = "pelanggaran"
            )
        }

        // 3. Izin, Sakit, Ketidakhadiran
        if (hasAnyFuzzyMatch(tokens, listOf("sakit", "izin", "absen", "alpa", "ketidakhadiran", "dokter"))) {
            return AiAnswer(
                text = "🩺 **Ketentuan Surat Izin & Ketidakhadiran Murid**:\n\n" +
                        "• **Surat Izin Tertulis**: Wajib menyertakan surat izin resmi bertanda tangan Orang Tua/Wali yang diserahkan ke Wali Kelas/Guru Piket.\n" +
                        "• **Sakit Lebih dari 3 Hari**: Wajib melampirkan **Surat Keterangan Sakit dari Dokter** resmi.\n" +
                        "• **Izin Keperluan Keluarga Mendadak**: Maksimal 3 hari kerja dan wajib memberitahukan pihak sekolah terlebih dahulu.\n" +
                        "• **Meninggalkan Kelas/Sekolah Saat KBM**: Harus membawa Surat Izin Meninggalkan Pelajaran yang ditandatangani Guru Pengajar dan Guru Piket.",
                sourceChapter = "BAB 2. HAL MASUK SEKOLAH",
                sourceSection = "Poin 2.4",
                sourcePage = 1,
                targetRoute = "pedoman"
            )
        }

        // 4. Seragam Berdasarkan Hari & Ketentuan Siswi
        val isUniformQuery = hasAnyFuzzyMatch(tokens, listOf("seragam", "baju", "pakaian", "atribut", "pakai", "memakai", "celana", "rok", "lengan", "dasi", "pusar"))
        if (isUniformQuery || tokens.any { it in listOf("senin", "selasa", "rabu", "kamis", "jumat", "pramuka", "batik", "dasi", "pusar") }) {
            if (hasAnyFuzzyMatch(tokens, listOf("dasi", "pusar"))) {
                return AiAnswer(
                    text = "👔 **Standar Pemakaian Dasi & Seragam Siswa Laki-Laki (Putra)**:\n\n" +
                            "• **Standar Panjang Dasi**: Ujung dasi wajib terpasang rapi dengan standar panjang **di bawah pusar** (menjuntai rapi menutupi hingga di bawah pusar, tidak menggantung tinggi).\n" +
                            "• **Ketentuan Seragam Putra**: Kemeja wajib dimasukkan rapi ke dalam celana, mengenakan dasi sekolah resmi, ikat pinggang hitam polos standar berlogo SMANEKA, dan celana panjang model sopan standar (bukan pensil/botol/cutbray).\n" +
                            "• **Waktu Pemakaian Dasi**: Wajib dikenakan saat Upacara Bendera hari Senin dan kegiatan resmi sekolah.\n" +
                            "• **Kerapian**: Terpasang rapi dan pas di bawah kerah kemeja seragam.",
                    sourceChapter = "BAB 3. KEWAJIBAN MURID",
                    sourceSection = "Poin 3.4.B-E & Standar Visual",
                    sourcePage = 2,
                    targetRoute = "standards"
                )
            }

            if (hasAnyFuzzyMatch(tokens, listOf("senin", "upacara"))) {
                return AiAnswer(
                    text = "👔 **Ketentuan Seragam Hari Senin (Upacara Bendera)**:\n\n" +
                            "• **Atasan**: Baju putih lengan panjang (putra & putri) dengan badge OSIS dan lokasi SMAN 1 Kepanjen lengkap terpasang rapi.\n" +
                            "• **Bawahan**: Celana putih (putra) / Rok putih rempel rapi sampai mata kaki (putri).\n" +
                            "• **Kelengkapan**: Topi pet resmi berlogo Tut Wuri Handayani (bagian depan), dasi sekolah (standar panjang dasi di bawah pusar), dan ikat pinggang hitam berlogo SMANEKA (bukan SMP).\n" +
                            "• **Kaki**: Kaos kaki **PUTIH POLOS** minimal 15 cm dari mata kaki dan sepatu bertali **HITAM DOFF**.\n" +
                            "• **Siswi**: Wajib baju atasan lengan panjang dan jilbab segi empat resmi bagi yang berhijab.",
                    sourceChapter = "BAB 3. KEWAJIBAN MURID",
                    sourceSection = "Poin 3.4.A (a)",
                    sourcePage = 2,
                    targetRoute = "standards"
                )
            }

            if (hasAnyFuzzyMatch(tokens, listOf("selasa", "abu"))) {
                return AiAnswer(
                    text = "👔 **Ketentuan Seragam Hari Selasa**:\n\n" +
                            "• **Atasan**: Baju putih lengan panjang (**WAJIB lengan panjang untuk Putra & Putri**) lengkap dengan atribut sekolah.\n" +
                            "• **Bawahan**: Celana abu-abu standar (putra) / Rok abu-abu panjang rempel (putri).\n" +
                            "• **Kelengkapan**: Ikat pinggang hitam berlogo sekolah.\n" +
                            "• **Kaki**: Kaos kaki putih polos 15 cm di atas mata kaki dan sepatu bertali hitam doff.",
                    sourceChapter = "BAB 3. KEWAJIBAN MURID",
                    sourceSection = "Poin 3.4.A (b)",
                    sourcePage = 2,
                    targetRoute = "standards"
                )
            }

            if (hasAnyFuzzyMatch(tokens, listOf("rabu"))) {
                return AiAnswer(
                    text = "👔 **Ketentuan Seragam Hari Rabu**:\n\n" +
                            "• **Atasan**: Baju putih lengan pendek (putra) / baju putih lengan panjang (putri) dengan celana/rok abu-abu.\n" +
                            "• **Siswi (Perempuan)**: SELURUH atasan seragam WAJIB lengan panjang dan rok panjang sampai mata kaki.\n" +
                            "• **Atribut Wajib**: Atribut OSIS lengkap dan ikat pinggang hitam sekolah.\n" +
                            "• **Kaki**: Kaos kaki putih polos minimal 15 cm dan sepatu bertali hitam doff.",
                    sourceChapter = "BAB 3. KEWAJIBAN MURID",
                    sourceSection = "Poin 3.4.A (c)",
                    sourcePage = 2,
                    targetRoute = "standards"
                )
            }

            if (hasAnyFuzzyMatch(tokens, listOf("kamis", "batik"))) {
                return AiAnswer(
                    text = "🎨 **Ketentuan Seragam Hari Kamis (Batik SMANEKA)**:\n\n" +
                            "• **Atasan**: Baju Batik SMANEKA lengan pendek (putra) / baju batik lengan panjang (putri).\n" +
                            "• **Bawahan**: Celana putih rapi (putra) / Rok putih panjang rapi (putri).\n" +
                            "• **Kelengkapan**: Ikat pinggang hitam berlogo SMANEKA.\n" +
                            "• **Kaki**: Kaos kaki putih polos minimal 15 cm dan sepatu bertali hitam doff.",
                    sourceChapter = "BAB 3. KEWAJIBAN MURID",
                    sourceSection = "Poin 3.4.A (d)",
                    sourcePage = 2,
                    targetRoute = "standards"
                )
            }

            if (hasAnyFuzzyMatch(tokens, listOf("jumat", "pramuka", "khas"))) {
                return AiAnswer(
                    text = "🕌 **Ketentuan Seragam Hari Jum'at (Pramuka)**:\n\n" +
                            "• **Atasan**: Baju Pramuka lengan pendek (putra) / baju pramuka lengan panjang (putri) + atribut pramuka lengkap.\n" +
                            "• **Bawahan**: Celana/rok pramuka rapi panjang sampai mata kaki.\n" +
                            "• **Kelengkapan Khusus**: Kaos kaki **HITAM POLOS** minimal 15 cm dari mata kaki.\n" +
                            "• **Sepatu**: Sepatu bertali warna hitam polos doff.",
                    sourceChapter = "BAB 3. KEWAJIBAN MURID",
                    sourceSection = "Poin 3.4.A (e)",
                    sourcePage = 2,
                    targetRoute = "standards"
                )
            }

            // General uniform query
            return AiAnswer(
                text = "👔 **Jadwal & Ketentuan Seragam SMAN 1 Kepanjen**:\n\n" +
                        "👔 **Standar Panjang Dasi**:\n" +
                        "• Ujung dasi wajib menjuntai rapi dengan **panjang di bawah pusar** saat Upacara Bendera & acara resmi.\n\n" +
                        "🧕 **Ketentuan Khusus Siswi (Perempuan)**:\n" +
                        "• **SEMUA SERAGAM ATASAN (Senin s.d. Jumat) WAJIB LENGAN PANJANG**.\n" +
                        "• Rok wajib panjang menutup hingga mata kaki & tidak ketat.\n\n" +
                        "📅 **Jadwal Lengan Seragam Harian**:\n" +
                        "• **Senin**: Baju putih lengan panjang (putra & putri), rok/celana putih, dasi di bawah pusar, topi pet Tut Wuri Handayani.\n" +
                        "• **Selasa**: Baju putih lengan panjang (putra & putri WAJIB lengan panjang), celana/rok abu-abu.\n" +
                        "• **Rabu**: Baju putih lengan pendek (putra) / baju putih lengan panjang (putri), celana/rok abu-abu.\n" +
                        "• **Kamis**: Baju Batik SMANEKA lengan pendek (putra) / baju batik lengan panjang (putri), celana/rok putih.\n" +
                        "• **Jum'at**: Baju Pramuka lengan pendek (putra) / baju pramuka lengan panjang (putri), kaos kaki hitam polos 15 cm.",
                sourceChapter = "BAB 3. KEWAJIBAN MURID",
                sourceSection = "Poin 3.4.A (a-e) & 3.4.B-E",
                sourcePage = 2,
                targetRoute = "standards"
            )
        }

        // 5. Sepatu & Kaos Kaki
        if (hasAnyFuzzyMatch(tokens, listOf("sepatu", "flatshoes", "glossy", "sneakers", "pantofel", "pantovel", "fantovel", "sandal", "selop", "sneaker"))) {
            return AiAnswer(
                text = "👟 **Standar Sepatu & Kaos Kaki SMAN 1 Kepanjen**:\n\n" +
                        "✅ **Sepatu yang DIPERBOLEHKAN / WAJIB**:\n" +
                        "• Sepatu model siswa bertali warna **HITAM POLOS (doff / matte)**.\n" +
                        "• Model pantofel/fantovel hitam polos standar.\n" +
                        "• Kaos kaki minimal **15 cm di atas mata kaki** (Senin-Kamis putih polos, Jumat hitam polos).\n\n" +
                        "❌ **Sepatu yang DILARANG KERAS**:\n" +
                        "• Sepatu warna putih / warna-warni / bercorak garis warna selain hitam (hanya boleh dipakai saat jam pelajaran olahraga).\n" +
                        "• Sepatu model *flatshoes* atau *slip-on* tipis santai tanpa tali.\n" +
                        "• Sepatu berbahan *glossy* (mengkilap mencolok) atau model *boots*.\n" +
                        "• Sandal atau selop ke sekolah tanpa surat keterangan sakit/izin medis.\n" +
                        "• Kaos kaki pendek / semata kaki (< 15 cm) atau kaos kaki bermotif/bergambar.",
                sourceChapter = "BAB 3. KEWAJIBAN MURID",
                sourceSection = "Poin 3.5.A-B & Pelanggaran I No. 15",
                sourcePage = 3,
                targetRoute = "standards"
            )
        }

        if (hasAnyFuzzyMatch(tokens, listOf("kaos kaki", "kaoskaki", "kaos"))) {
            return AiAnswer(
                text = "🧦 **Ketentuan Kaos Kaki SMAN 1 Kepanjen**:\n\n" +
                        "• **Panjang Wajib**: Minimal **15 cm di atas mata kaki** (dilarang menggunakan model semata kaki / *hidden socks*).\n" +
                        "• **Hari Senin s.d. Kamis**: Warna **PUTIH POLOS** tanpa motif / corak mencolok.\n" +
                        "• **Hari Jum'at**: Warna **HITAM POLOS**.",
                sourceChapter = "BAB 3. KEWAJIBAN MURID",
                sourceSection = "Poin 3.5.C",
                sourcePage = 3,
                targetRoute = "standards"
            )
        }

        // 6. Rambut Putra & Standar 2-2-1
        if (hasAnyFuzzyMatch(tokens, listOf("rambut", "cukur", "potongan", "gondrong", "undercut", "semir", "2-2-1")) &&
            (hasAnyFuzzyMatch(tokens, listOf("putra", "laki", "cowok", "pria", "2-2-1")) || !hasAnyFuzzyMatch(tokens, listOf("putri", "cewek", "jilbab")))
        ) {
            return AiAnswer(
                text = "✂️ **Standar Potongan Rambut Siswa Laki-Laki (Ukuran 2-2-1)**:\n\n" +
                        "✅ **Ketentuan Ukuran Resmi 2-2-1**:\n" +
                        "• Samping kiri & kanan: **2 cm** (tidak menutupi daun telinga)\n" +
                        "• Belakang: **2 cm** (tidak menyentuh kerah baju)\n" +
                        "• Atas/Depan: **1 s.d. 2 cm rapi** (tidak menyentuh alis mata di depan)\n\n" +
                        "❌ **Larangan Keras**:\n" +
                        "• Dilarang rambut gondrong / jabrik / mohawk / mullet.\n" +
                        "• Dilarang mengecat / mewarnai / menyemir rambut.\n" +
                        "• Dilarang potongan *undercut* ekstrem, *skin fade*, atau motif garis (*hair tattoo*).\n" +
                        "• Dilarang memakai anting, tindik, gelang, atau kalung.",
                sourceChapter = "BAB 3. KEWAJIBAN MURID",
                sourceSection = "Poin 3.6.A-C",
                sourcePage = 3,
                targetRoute = "standards"
            )
        }

        // 7. Make Up, Kosmetik, Kuku & Siswa Perempuan
        if (hasAnyFuzzyMatch(tokens, listOf("makeup", "make up", "kosmetik", "kutek", "lipstik", "alis", "bulu mata", "eyelash", "kuku", "cat kuku", "nail", "bedak", "blush", "perhiasan")) ||
            (hasAnyFuzzyMatch(tokens, listOf("rambut", "jilbab")) && hasAnyFuzzyMatch(tokens, listOf("putri", "cewek", "wanita", "perempuan", "jilbab")))
        ) {
            return AiAnswer(
                text = "💄 **Ketentuan Make Up, Kuku & Penampilan Siswa Perempuan SMANEKA**:\n\n" +
                        "❌ **Larangan Make Up & Kosmetik (Pelanggaran I No. 23-25)**:\n" +
                        "• **Riasan Tebal / Menor**: Dilarang memakai *make up* tebal, bedak menor, dan *blush on* mencolok.\n" +
                        "• **Lipstik**: Dilarang memakai lipstik merah/mencolok (hanya boleh pelembab bibir/*lip balm* transparan alami).\n" +
                        "• **Alis**: Dilarang mencukur alis, mentato/sulam alis, atau melukis alis secara berlebihan.\n" +
                        "• **Bulu Mata**: Dilarang memakai *eyelash extension* (sambung bulu mata) atau bulu mata palsu.\n" +
                        "• **Kuku**: Dilarang memanjangkan kuku, memakai cat kuku (*kutek*), *nail art*, atau sambung kuku palsu.\n" +
                        "• **Rambut & Aksesoris**: Dilarang *hair extension*, mengecat rambut, memakai perhiasan emas berlebihan, atau tindik telinga bertingkat (>1).\n\n" +
                        "✅ **Standar yang Diperbolehkan / Wajib**:\n" +
                        "• Tampil natural, bersih, dan segar khas pelajar.\n" +
                        "• Diperkenankan pelembab/sunscreen tipis dan bedak tabur transparan untuk merawat kesehatan kulit.\n" +
                        "• Rambut disisir rapi dan diikat/dikuncir satu (bagi non-hijab), atau mengenakan jilbab kain segi empat resmi sekolah (bagi yang berhijab).",
                sourceChapter = "BAB 3. KEWAJIBAN MURID & BENTUK PELANGGARAN I",
                sourceSection = "Poin 3.6.E-H & Pelanggaran I No. 23-25",
                sourcePage = 3,
                targetRoute = "standards"
            )
        }

        // 8. Penggunaan HP / Smartphone
        if (hasAnyFuzzyMatch(tokens, listOf("hp", "handphone", "ponsel", "gadget", "gawai", "locker", "main hp"))) {
            return AiAnswer(
                text = "📱 **Aturan Penyimpanan & Penggunaan HP saat KBM**:\n\n" +
                        "1. **Penyimpanan di Locker Box**: HP wajib disimpan di dalam kotak penyimpanan HP (*locker box*) yang tersedia di tiap kelas dalam keadaan *silent* / non-aktif dan dikunci saat KBM dimulai.\n" +
                        "2. **Izin Penggunaan**: HP **hanya boleh digunakan** jika ada instruksi dan pengawasan langsung dari Guru Mata Pelajaran untuk kegiatan belajar digital.\n" +
                        "3. **Sanksi Pelanggaran**: Mengoperasikan HP saat pelajaran tanpa izin merupakan **Pelanggaran Kategori II (Sedang No. 6)**. HP akan disita sementara dan diserahkan ke Tim Tatib/Wali Kelas.",
                sourceChapter = "BAB 3. KEWAJIBAN MURID & BAB 6 LARANGAN",
                sourceSection = "Poin 3.7 & Pelanggaran II No. 6",
                sourcePage = 3,
                targetRoute = "pedoman"
            )
        }

        // 9. Rokok, Vape, Pod
        if (hasAnyFuzzyMatch(tokens, listOf("rokok", "vape", "pod", "liquid", "merokok", "vaping"))) {
            return AiAnswer(
                text = "🚭 **Ketentuan & Sanksi Tegas Rokok / Vape**:\n\n" +
                        "• **Larangan**: Membawa, menyimpan, atau menghisap rokok konvensional maupun rokok elektrik (*vape/pod*) di lingkungan sekolah maupun di luar sekolah saat masih berseragam.\n" +
                        "• **Kategori Sanksi**: Tergolong **Pelanggaran Kategori II (Sedang No. 5)**.\n" +
                        "• **Konsekuensi Resmi**:\n" +
                        "  1. Barang bukti rokok / *vape* disita sekolah dan tidak dikembalikan.\n" +
                        "  2. Pemanggilan resmi Orang Tua/Wali ke sekolah oleh Tim Tatib dan Wali Kelas.\n" +
                        "  3. Penandatanganan Surat Perjanjian & Komitmen Tertulis bermaterai.\n" +
                        "  4. Pembinaan karakter intensif oleh Guru BK.",
                sourceChapter = "BENTUK PELANGGARAN MURID B. PELANGGARAN II",
                sourceSection = "Nomor 5 (Halaman 7)",
                sourcePage = 7,
                targetRoute = "pelanggaran"
            )
        }

        // 10. Bullying / Perundungan & Kekerasan
        if (hasAnyFuzzyMatch(tokens, listOf("bullying", "perundungan", "perkelahian", "tawuran", "berantem", "ancam", "malak"))) {
            return AiAnswer(
                text = "🛑 **Penanganan Tegas Perundungan (Bullying) & Kekerasan**:\n\n" +
                        "• **Kategori Pelanggaran**: Tindakan *bullying* (fisik, verbal, maupun siber/sosmed) serta perkelahian tergolong **Pelanggaran Kategori III (Berat No. 4-5)**.\n" +
                        "• **Prosedur Penanganan**:\n" +
                        "  1. Penanganan langsung oleh Satuan Tugas TPPK (*Tim Pencegahan dan Penanganan Kekerasan*) dan Tim Tatib.\n" +
                        "  2. Pemanggilan darurat Orang Tua/Wali Murid.\n" +
                        "  3. Penyelenggaraan Konferensi Kasus Khusus dipimpin langsung oleh Kepala Sekolah.\n" +
                        "  4. Sanksi tegas berupa skorsing akademik hingga pengembalian pembinaan kepada Orang Tua (dikeluarkan dari sekolah).",
                sourceChapter = "BENTUK PELANGGARAN MURID C. PELANGGARAN III",
                sourceSection = "Nomor 4-5 (Halaman 8)",
                sourcePage = 8,
                targetRoute = "pelanggaran"
            )
        }

        // 11. Kendaraan & Knalpot Brong
        if (hasAnyFuzzyMatch(tokens, listOf("knalpot", "brong", "motor", "parkir", "helm", "spion", "kendaraan"))) {
            return AiAnswer(
                text = "🏍️ **Ketentuan Kendaraan Bermotor di SMAN 1 Kepanjen**:\n\n" +
                        "• **Parkir**: Sepeda motor wajib diparkir rapi di area parkir resmi sekolah yang telah ditentukan.\n" +
                        "• **Kelengkapan Berkendara**: Wajib memakai Helm SNI dan sepeda motor memiliki kelengkapan standar (kaca spion lengkap, plat nomor aktif).\n" +
                        "• **Larangan Knalpot Brong**: Dilarang keras memakai knalpot tidak standar / bising (*knalpot brong*). Pelanggaran ini merupakan **Pelanggaran Kategori II (Sedang No. 7)**, dan knalpot wajib diganti standar di sekolah sebelum kendaraan diizinkan keluar.",
                sourceChapter = "BENTUK PELANGGARAN B. PELANGGARAN II",
                sourceSection = "Nomor 7 (Halaman 7)",
                sourcePage = 7,
                targetRoute = "pelanggaran"
            )
        }

        // 12. SOP Pelanggaran Berulang (> 5 Kali)
        if (hasAnyFuzzyMatch(tokens, listOf("5 kali", "berulang", "akumulasi", "skorsing", "tahap", "sop"))) {
            return AiAnswer(
                text = "📋 **SOP Penanganan Jika Melakukan Pelanggaran > 5 Kali**:\n\n" +
                        "1. **Pelimpahan Kasus**: Guru Piket/Wali Kelas melimpahkan penanganan ke Tim Ketertiban dan Koordinator BK.\n" +
                        "2. **Panggilan Resmi Orang Tua**: Penerbitan Surat Panggilan Orang Tua resmi ke sekolah untuk konferensi pembinaan.\n" +
                        "3. **Surat Komitmen Khusus**: Murid dan Orang Tua menandatangani Surat Komitmen Perbaikan Karakter Tingkat Lanjut.\n" +
                        "4. **Sanksi Disiplin**: Diberikan sanksi skorsing pembinaan di rumah dengan tugas akademis terstruktur.",
                sourceChapter = "SOP ALUR PENANGANAN PELANGGARAN",
                sourceSection = "Kategori I Tahap 3 (Halaman 9)",
                sourcePage = 9,
                targetRoute = "pelanggaran"
            )
        }

        // 13. Kepala Sekolah & Pengesahan
        if (hasAnyFuzzyMatch(tokens, listOf("kepala sekolah", "kasek", "kepsek", "pengesahan", "ditetapkan", "nip"))) {
            return AiAnswer(
                text = "🏛️ **Pengesahan Dokumen Tata Tertib SMAN 1 Kepanjen**:\n\n" +
                        "• **Tanggal Penetapan**: Ditetapkan di Kepanjen pada **13 Juli 2026**.\n" +
                        "• **Kepala Sekolah Pengesah**: Ibu **FADILAH UMI MAISYAROH, M.Pd** (Pembina Utama Muda, NIP. 19750827 200012 2 003).\n" +
                        "• **Berlaku**: Untuk seluruh murid SMA Negeri 1 Kepanjen Tahun Ajaran 2026/2027.",
                sourceChapter = "BAB 7. LAIN-LAIN & PENGESAHAN",
                sourceSection = "Poin 7.3",
                sourcePage = 5,
                targetRoute = "pedoman"
            )
        }

        return null
    }

    /**
     * Advanced Typo-Tolerant & Fuzzy N-Gram Retrieval Engine across all SQLite/seeded records.
     */
    private fun performSmartFuzzyRagRetrieval(normQuery: String, rawQuery: String): AiAnswer {
        val queryTokens = normQuery.split(Regex("[^a-zA-Z0-9\\-]+")).filter { it.length >= 2 }

        if (queryTokens.isEmpty()) {
            return AiAnswer(
                text = "Silakan masukkan pertanyaan mengenai aturan sekolah, seragam, jam belajar, atau sanksi pelanggaran SMANEKA.",
                sourceChapter = null,
                sourceSection = null,
                sourcePage = null,
                isGrounded = false
            )
        }

        data class ScoredResult(
            val score: Double,
            val reg: RegulationEntity? = null,
            val viol: ViolationEntity? = null,
            val std: VisualStandardEntity? = null,
            val proc: HandlingProcedureEntity? = null
        )

        val results = mutableListOf<ScoredResult>()

        // 1. Score Regulations
        for (r in TatibDatabaseSeeder.regulations) {
            var score = 0.0
            val targetCorpus = "${r.title} ${r.fullContent} ${r.summaryContent} ${r.keywords} ${r.chapterTitle}".lowercase()
            val corpusWords = targetCorpus.split(Regex("[^a-zA-Z0-9\\-]+"))

            for (token in queryTokens) {
                if (targetCorpus.contains(token)) score += 6.0
                if (r.title.lowercase().contains(token)) score += 10.0
                if (r.keywords.lowercase().contains(token)) score += 8.0

                // Fuzzy word match
                if (corpusWords.any { isFuzzyMatch(token, it) }) score += 4.0
            }

            if (normQuery.length >= 5 && targetCorpus.contains(normQuery)) score += 15.0
            if (score > 0) results.add(ScoredResult(score = score, reg = r))
        }

        // 2. Score Violations
        for (v in TatibDatabaseSeeder.violations) {
            var score = 0.0
            val targetCorpus = "${v.name} ${v.consequence} ${v.procedure} ${v.keywords} ${v.tier}".lowercase()
            val corpusWords = targetCorpus.split(Regex("[^a-zA-Z0-9\\-]+"))

            for (token in queryTokens) {
                if (targetCorpus.contains(token)) score += 6.0
                if (v.name.lowercase().contains(token)) score += 12.0
                if (v.keywords.lowercase().contains(token)) score += 8.0

                if (corpusWords.any { isFuzzyMatch(token, it) }) score += 5.0
            }

            if (normQuery.length >= 5 && targetCorpus.contains(normQuery)) score += 15.0
            if (score > 0) results.add(ScoredResult(score = score, viol = v))
        }

        // 3. Score Visual Standards
        for (s in TatibDatabaseSeeder.visualStandards) {
            var score = 0.0
            val targetCorpus = "${s.title} ${s.subtitle} ${s.permittedRule} ${s.prohibitedRule} ${s.details} ${s.category}".lowercase()
            val corpusWords = targetCorpus.split(Regex("[^a-zA-Z0-9\\-]+"))

            for (token in queryTokens) {
                if (targetCorpus.contains(token)) score += 6.0
                if (s.title.lowercase().contains(token)) score += 10.0
                if (s.category.lowercase().contains(token)) score += 8.0

                if (corpusWords.any { isFuzzyMatch(token, it) }) score += 4.0
            }

            if (score > 0) results.add(ScoredResult(score = score, std = s))
        }

        val best = results.maxByOrNull { it.score }

        if (best != null && best.score >= 6.0) {
            best.viol?.let { v ->
                return AiAnswer(
                    text = "Berdasarkan Dokumen Tata Tertib SMANEKA 2026/2027:\n\n" +
                            "⚠️ **${v.name}**\nTergolong ke dalam **${v.tier} (No. ${v.number})**.\n\n" +
                            "📌 **Konsekuensi / Tindak Lanjut**:\n${v.consequence}\n\n" +
                            "📋 **Prosedur Penanganan**:\n${v.procedure}",
                    sourceChapter = v.sourceChapter,
                    sourceSection = "Nomor ${v.number}",
                    sourcePage = v.pageNumber,
                    targetRoute = "pelanggaran"
                )
            }

            best.std?.let { s ->
                return AiAnswer(
                    text = "Berdasarkan Standar Visual SMANEKA pada kategori **${s.category} (${s.title})**:\n\n" +
                            "✅ **Ketentuan Sesuai / Diperbolehkan**:\n${s.permittedRule}\n\n" +
                            "❌ **Larangan / Tidak Sesuai**:\n${s.prohibitedRule}\n\n" +
                            "💡 **Catatan Khusus**:\n${s.details}",
                    sourceChapter = s.sourceReference,
                    sourceSection = s.category,
                    sourcePage = s.pageNumber,
                    targetRoute = "standards"
                )
            }

            best.reg?.let { r ->
                return AiAnswer(
                    text = "Berdasarkan Pedoman Tatib SMANEKA pada **${r.chapterNumber} (${r.chapterTitle})** - Poin ${r.sectionNumber}:\n\n" +
                            "📌 **${r.title}**\n\n\"${r.fullContent}\"",
                    sourceChapter = "${r.chapterNumber}. ${r.chapterTitle}",
                    sourceSection = "Poin ${r.sectionNumber}",
                    sourcePage = r.pageNumber,
                    targetRoute = "pedoman"
                )
            }
        }

        // Helpful response if completely unfamiliar topic
        return AiAnswer(
            text = "Maaf, aturan mengenai \"$rawQuery\" tidak ditemukan secara spesifik dalam Dokumen Pedoman Tata Tertib SMAN 1 Kepanjen Tahun Ajaran 2026/2027.\n\n💡 *Tips: Coba tanyakan topik populer seperti jam masuk, seragam senin-jumat, sepatu hitam doff, rambut 2-2-1, aturan HP, atau sanksi rokok/vape.*",
            sourceChapter = "Pedoman Tatib SMANEKA",
            sourceSection = "Tahun 2026/2027",
            sourcePage = 1,
            isGrounded = false
        )
    }

    private fun extractCitation(text: String): Triple<String?, String?, Int?> {
        var chapter: String? = null
        var section: String? = null
        var page: Int? = null

        val babRegex = Regex("bab\\s*([1-7ivxlcdm]+)", RegexOption.IGNORE_CASE)
        val babMatch = babRegex.find(text)
        if (babMatch != null) {
            chapter = "BAB " + babMatch.groupValues[1].uppercase()
        }

        val poinRegex = Regex("poin\\s*([0-9a-z.\\-]+)", RegexOption.IGNORE_CASE)
        val poinMatch = poinRegex.find(text)
        if (poinMatch != null) {
            section = "Poin " + poinMatch.groupValues[1]
        }

        val halRegex = Regex("halaman\\s*([0-9]+)", RegexOption.IGNORE_CASE)
        val halMatch = halRegex.find(text)
        if (halMatch != null) {
            page = halMatch.groupValues[1].toIntOrNull()
        }

        return Triple(chapter, section, page)
    }
}
