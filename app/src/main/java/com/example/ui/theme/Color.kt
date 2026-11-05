package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Bento Grid SMANEKA Palette
val BentoPrimary = Color(0xFF1D4ED8) // Deep vibrant royal blue
val BentoPrimaryDark = Color(0xFF1E3A8A)
val BentoPrimaryDarker = Color(0xFF0F172A)
val BentoSoftBlue = Color(0xFFDBEAFE)
val BentoPaleBlue = Color(0xFFEFF6FF)
val BentoBorderBlue = Color(0xFFBFDBFE)

// Colorful Category Tokens
val ColorCategorySeragamBg = Color(0xFFE0F2FE)
val ColorCategorySeragamBorder = Color(0xFF7DD3FC)
val ColorCategorySeragamText = Color(0xFF0369A1)

val ColorCategoryWaktuBg = Color(0xFFDCFCE7)
val ColorCategoryWaktuBorder = Color(0xFF86EFAC)
val ColorCategoryWaktuText = Color(0xFF15803D)

val ColorCategoryRambutBg = Color(0xFFF3E8FF)
val ColorCategoryRambutBorder = Color(0xFFD8B4FE)
val ColorCategoryRambutText = Color(0xFF7E22CE)

val ColorCategoryBarangBg = Color(0xFFFEF3C7)
val ColorCategoryBarangBorder = Color(0xFFFCD34D)
val ColorCategoryBarangText = Color(0xFFB45309)

val ColorCategoryKendaraanBg = Color(0xFFFFE4E6)
val ColorCategoryKendaraanBorder = Color(0xFFFDA4AF)
val ColorCategoryKendaraanText = Color(0xFFBE123C)

val ColorCategoryEtikaBg = Color(0xFFCCFBF1)
val ColorCategoryEtikaBorder = Color(0xFF5EEAD4)
val ColorCategoryEtikaText = Color(0xFF0F766E)

val ColorCategorySopBg = Color(0xFFFAE8FF)
val ColorCategorySopBorder = Color(0xFFF0ABFC)
val ColorCategorySopText = Color(0xFFA21CAF)

val ColorCategoryIzinBg = Color(0xFFE0E7FF)
val ColorCategoryIzinBorder = Color(0xFFA5B4FC)
val ColorCategoryIzinText = Color(0xFF4338CA)

// Vibrant UI Accents
val BentoMint = Color(0xFFECFDF5)
val BentoMintBorder = Color(0xFFA7F3D0)
val BentoMintText = Color(0xFF047857)
val BentoGold = Color(0xFFF59E0B)
val BentoGoldSoft = Color(0xFFFEF3C7)
val BentoAmber = Color(0xFFF59E0B)
val BentoEmerald = Color(0xFF10B981)
val BentoCoral = Color(0xFFF97316)
val BentoRose = Color(0xFFE11D48)
val BentoViolet = Color(0xFF8B5CF6)
val BentoCyan = Color(0xFF06B6D4)
val BentoTeal = Color(0xFF14B8A6)

// Gradient Brushes for Colorful Cards
val GradientPelanggaran = Brush.linearGradient(
    colors = listOf(Color(0xFFFF5722), Color(0xFFE91E63), Color(0xFFC2185B))
)
val GradientTanyaAi = Brush.linearGradient(
    colors = listOf(Color(0xFF6366F1), Color(0xFF8B5CF6), Color(0xFFA855F7))
)
val GradientAiAssistant = GradientTanyaAi
val GradientBelajar = Brush.linearGradient(
    colors = listOf(Color(0xFF059669), Color(0xFF10B981), Color(0xFF34D399))
)
val GradientPedomanHero = Brush.linearGradient(
    colors = listOf(Color(0xFF1E3A8A), Color(0xFF2563EB), Color(0xFF3B82F6))
)
val GradientStandarVisual = Brush.linearGradient(
    colors = listOf(Color(0xFF0284C7), Color(0xFF0EA5E9), Color(0xFF06B6D4))
)
val GradientTeacherMode = Brush.linearGradient(
    colors = listOf(Color(0xFF9333EA), Color(0xFFC026D3), Color(0xFFE11D48))
)
val GradientSunInsight = Brush.linearGradient(
    colors = listOf(Color(0xFFFFFBEB), Color(0xFFFEF3C7))
)
val GradientHeaderBadge = Brush.linearGradient(
    colors = listOf(Color(0xFF1E40AF), Color(0xFF3B82F6), Color(0xFF60A5FA))
)
val GradientUserBubble = Brush.linearGradient(
    colors = listOf(Color(0xFF4F46E5), Color(0xFF7C3AED))
)
val GradientTrophy = Brush.linearGradient(
    colors = listOf(Color(0xFFF59E0B), Color(0xFFFBBF24), Color(0xFFFDE047))
)

// Aesthetic Soft White to Blue Gradient for Root Background
val GradientAppBackground = Brush.verticalGradient(
    colors = listOf(
        Color(0xFFFFFFFF), // Pure Crisp White Top
        Color(0xFFF7FAFD), // Ultra-Soft Snow White
        Color(0xFFF0F6FE), // Gentle Sky Azure Mist
        Color(0xFFE2EFFC), // Elegant Pastel Ice Blue
        Color(0xFFD6E8FA)  // Soft SMANEKA Blue Base
    )
)

// High-Contrast Text Tokens
val BentoTextPrimary = Color(0xFF0F172A) // High contrast slate-900 for titles & body
val BentoTextSecondary = Color(0xFF334155) // High contrast slate-700 for subtitles & descriptions
val BentoTextMuted = Color(0xFF64748B) // Slate-500 for small timestamps / tags
val BentoBorderColor = Color(0xFFE2E8F0) // Clean slate-200 border

// Callout / Status High-Contrast Pairs
val BentoPermittedBg = Color(0xFFF0FDF4)
val BentoPermittedBorder = Color(0xFF86EFAC)
val BentoPermittedTitle = Color(0xFF166534)
val BentoPermittedBody = Color(0xFF14532D)

val BentoProhibitedBg = Color(0xFFFFF1F2)
val BentoProhibitedBorder = Color(0xFFFECDD3)
val BentoProhibitedTitle = Color(0xFF9F1239)
val BentoProhibitedBody = Color(0xFF881337)

val BentoWarningBg = Color(0xFFFFFBEB)
val BentoWarningBorder = Color(0xFFFDE68A)
val BentoWarningTitle = Color(0xFF92400E)
val BentoWarningBody = Color(0xFF78350F)

val BentoInfoBg = Color(0xFFEFF6FF)
val BentoInfoBorder = Color(0xFFBFDBFE)
val BentoInfoTitle = Color(0xFF1E40AF)
val BentoInfoBody = Color(0xFF1E3A8A)

// Backward compatible aliases
val SmanekaNavy = Color(0xFF1D4ED8)
val SmanekaBlue = Color(0xFF2563EB)
val SmanekaLightBlue = Color(0xFF38BDF8)
val SmanekaGold = Color(0xFFF59E0B)
val SmanekaEmerald = Color(0xFF10B981)
val SmanekaRed = Color(0xFFEF4444)

val BackgroundLight = Color(0xFFFFFFFF)
val SurfaceLight = Color(0xFFFFFFFF)
val SurfaceVariantLight = Color(0xFFF8FAFC)
val OnSurfaceLight = Color(0xFF0F172A)
val OnSurfaceVariantLight = Color(0xFF334155)

// Dark Scheme Colors (Aligned with White Background requirement)
val SmanekaNavyDark = Color(0xFF1D4ED8)
val SmanekaBlueDark = Color(0xFF2563EB)
val SmanekaGoldDark = Color(0xFFD97706)

val BackgroundDark = Color(0xFFFFFFFF)
val SurfaceDark = Color(0xFFFFFFFF)
val SurfaceVariantDark = Color(0xFFF8FAFC)
val OnSurfaceDark = Color(0xFF0F172A)
val OnSurfaceVariantDark = Color(0xFF334155)

// Status & Badge Colors
val SeverityLightColor = Color(0xFF10B981) // Emerald for Ringan
val SeverityMediumColor = Color(0xFFF59E0B) // Amber for Sedang
val SeverityHeavyColor = Color(0xFFEF4444) // Coral Red for Berat

