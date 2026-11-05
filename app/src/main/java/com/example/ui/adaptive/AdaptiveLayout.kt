package com.example.ui.adaptive

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Window Width classification for adaptive Android layouts.
 * - CompactSmall: Very narrow phones (< 360.dp)
 * - Compact: Standard smartphones (360.dp .. 599.dp)
 * - Medium: Foldables unfolded, large phones in landscape, small tablets (600.dp .. 839.dp)
 * - Expanded: Tablets, Chromebooks, desktop (>= 840.dp)
 */
enum class WindowWidthClass {
    CompactSmall,
    Compact,
    Medium,
    Expanded
}

/**
 * Window Height classification for adaptive Android layouts.
 * - Compact: Phones in landscape orientation (< 480.dp)
 * - Medium: Standard phone portrait (480.dp .. 899.dp)
 * - Expanded: Tall phones, tablets, desktop (>= 900.dp)
 */
enum class WindowHeightClass {
    Compact,
    Medium,
    Expanded
}

/**
 * Comprehensive device and window size metrics calculated dynamically via BoxWithConstraints.
 * Works across all phone screen sizes, aspect ratios, orientations, multi-window & foldables.
 */
data class WindowSizeInfo(
    val widthClass: WindowWidthClass,
    val heightClass: WindowHeightClass,
    val widthDp: Dp,
    val heightDp: Dp,
    val isLandscape: Boolean,
    val isCompactSmall: Boolean,
    val isMediumOrExpanded: Boolean,
    val useNavigationRail: Boolean,
    val contentHorizontalPadding: Dp,
    val maxContentWidth: Dp = 760.dp
)

val LocalWindowSizeInfo = staticCompositionLocalOf {
    WindowSizeInfo(
        widthClass = WindowWidthClass.Compact,
        heightClass = WindowHeightClass.Medium,
        widthDp = 390.dp,
        heightDp = 844.dp,
        isLandscape = false,
        isCompactSmall = false,
        isMediumOrExpanded = false,
        useNavigationRail = false,
        contentHorizontalPadding = 16.dp,
        maxContentWidth = 760.dp
    )
}

/**
 * Root Adaptive Layout provider.
 * Automatically measures available space using BoxWithConstraints without hardcoded pixel values
 * or reliance on static screen measurements, supporting dynamic foldables, multi-window and rotations.
 */
@Composable
fun ProvideAdaptiveLayout(
    content: @Composable () -> Unit
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val width = maxWidth
        val height = maxHeight
        val isLandscape = width > height

        val widthClass = when {
            width < 360.dp -> WindowWidthClass.CompactSmall
            width < 600.dp -> WindowWidthClass.Compact
            width < 840.dp -> WindowWidthClass.Medium
            else -> WindowWidthClass.Expanded
        }

        val heightClass = when {
            height < 480.dp -> WindowHeightClass.Compact
            height < 900.dp -> WindowHeightClass.Medium
            else -> WindowHeightClass.Expanded
        }

        // On medium/expanded screens (e.g. >= 600.dp, foldables or phones in landscape),
        // use NavigationRail on the side for superior ergonomics and vertical space preservation.
        val useRail = width >= 600.dp

        val horizontalPadding = when (widthClass) {
            WindowWidthClass.CompactSmall -> 12.dp
            WindowWidthClass.Compact -> 16.dp
            WindowWidthClass.Medium -> 24.dp
            WindowWidthClass.Expanded -> 32.dp
        }

        val maxContentWidth = when (widthClass) {
            WindowWidthClass.CompactSmall, WindowWidthClass.Compact -> 600.dp
            WindowWidthClass.Medium -> 840.dp
            WindowWidthClass.Expanded -> 1080.dp
        }

        val windowInfo = WindowSizeInfo(
            widthClass = widthClass,
            heightClass = heightClass,
            widthDp = width,
            heightDp = height,
            isLandscape = isLandscape,
            isCompactSmall = widthClass == WindowWidthClass.CompactSmall,
            isMediumOrExpanded = widthClass == WindowWidthClass.Medium || widthClass == WindowWidthClass.Expanded,
            useNavigationRail = useRail,
            contentHorizontalPadding = horizontalPadding,
            maxContentWidth = maxContentWidth
        )

        CompositionLocalProvider(LocalWindowSizeInfo provides windowInfo) {
            content()
        }
    }
}

/**
 * Adaptive container that centers content and limits maximum reading width
 * on wide screens (tablets, landscape, foldables) while filling 100% of the screen
 * on compact smartphones.
 */
@Composable
fun AdaptiveScreenContainer(
    modifier: Modifier = Modifier,
    maxWidth: Dp = 760.dp,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = maxWidth)
        ) {
            content()
        }
    }
}
