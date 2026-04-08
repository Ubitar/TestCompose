package com.ubitar.testcompose.ui.learning.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun LearningScreen(
    title: String,
    summary: String,
    onBack: (() -> Unit)? = null,
    content: @Composable ColumnScopeLike.() -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF5EFE4)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (onBack != null) {
                BackPill(onBack = onBack)
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = summary,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            ColumnScopeLikeImpl.content()
        }
    }
}

@Composable
fun SectionCard(
    title: String,
    body: String
) {
    Card {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Text(text = body, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun EntryCard(
    title: String,
    description: String,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(width = 60.dp, height = 8.dp)
                    .background(color, RoundedCornerShape(999.dp))
            )
            Text(text = title, style = MaterialTheme.typography.titleLarge)
            Text(text = description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun BackPill(onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .background(Color(0xFF203A43), RoundedCornerShape(999.dp))
            .clickable(onClick = onBack)
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Text(text = "返回首页", color = Color.White)
    }
}

@Composable
fun TinyTag(text: String, color: Color = Color(0xFF2B6F63)) {
    Box(
        modifier = Modifier
            .background(color, RoundedCornerShape(999.dp))
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(text = text, color = Color.White, style = MaterialTheme.typography.labelMedium)
    }
}

@Composable
fun CodeLikeLine(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1F2933), RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        Text(text = text, color = Color(0xFFF7F4EA))
    }
}

@Composable
fun ActionButton(
    text: String,
    onClick: () -> Unit,
    color: Color = Color(0xFF26547C)
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 12.dp)
    ) {
        Text(text = text, color = Color.White, style = MaterialTheme.typography.titleSmall)
    }
}

interface ColumnScopeLike {
    @Composable
    fun section(title: String, body: String)

    @Composable
    fun block(content: @Composable () -> Unit)
}

private object ColumnScopeLikeImpl : ColumnScopeLike {
    @Composable
    override fun section(title: String, body: String) {
        SectionCard(title = title, body = body)
    }

    @Composable
    override fun block(content: @Composable () -> Unit) {
        content()
    }
}
