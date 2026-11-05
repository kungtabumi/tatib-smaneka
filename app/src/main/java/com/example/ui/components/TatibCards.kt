package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BentoInfoBg
import com.example.ui.theme.BentoInfoBorder
import com.example.ui.theme.BentoInfoTitle
import com.example.ui.theme.BentoInfoBody
import com.example.ui.theme.BentoPaleBlue
import com.example.ui.theme.BentoPermittedBg
import com.example.ui.theme.BentoPermittedBorder
import com.example.ui.theme.BentoPermittedTitle
import com.example.ui.theme.BentoPrimary
import com.example.ui.theme.BentoProhibitedBg
import com.example.ui.theme.BentoProhibitedBorder
import com.example.ui.theme.BentoProhibitedTitle
import com.example.ui.theme.BentoTextPrimary
import com.example.ui.theme.BentoTextSecondary
import com.example.ui.theme.BentoWarningBg
import com.example.ui.theme.BentoWarningBorder
import com.example.ui.theme.BentoWarningTitle
import com.example.ui.theme.SmanekaGold

@Composable
fun SeverityChip(
    tierNumber: Int,
    label: String,
    modifier: Modifier = Modifier
) {
    val (bgColor, borderColor, textColor, icon) = when (tierNumber) {
        1 -> Quadruple(BentoPermittedBg, BentoPermittedBorder, BentoPermittedTitle, Icons.Default.Info)
        2 -> Quadruple(BentoWarningBg, BentoWarningBorder, BentoWarningTitle, Icons.Default.Warning)
        else -> Quadruple(BentoProhibitedBg, BentoProhibitedBorder, BentoProhibitedTitle, Icons.Default.Warning)
    }

    Surface(
        color = bgColor,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .border(1.2.dp, borderColor, RoundedCornerShape(10.dp))
            .testTag("severity_chip_$tierNumber")
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = label,
                color = textColor,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

private data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)

@Composable
fun SourceCitationCard(
    chapter: String?,
    section: String?,
    page: Int?,
    onViewSource: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    if (chapter == null && section == null) return

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = BentoInfoBg
        ),
        modifier = modifier
            .fillMaxWidth()
            .border(1.2.dp, BentoInfoBorder, RoundedCornerShape(14.dp))
            .testTag("source_citation_card")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(BentoPrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.MenuBook,
                        contentDescription = "Sumber",
                        tint = Color.White,
                        modifier = Modifier.size(17.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Dasar Dokumen Resmi",
                        fontSize = 11.sp,
                        color = BentoInfoTitle,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${chapter ?: ""} ${if (section != null) "• $section" else ""} ${if (page != null) "(Hal. $page)" else ""}".trim(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = BentoInfoBody
                    )
                }
            }

            if (onViewSource != null) {
                IconButton(
                    onClick = onViewSource,
                    modifier = Modifier.testTag("btn_view_source_citation")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                        contentDescription = "Buka Sumber",
                        tint = BentoPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
