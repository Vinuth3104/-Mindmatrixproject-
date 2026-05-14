package com.example.mindmatrixproject.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindmatrixproject.data.Toy
import com.example.mindmatrixproject.data.ToyRepository
import com.example.mindmatrixproject.ui.components.StaggeredItem

@Composable
fun CatalogScreen(onToyClick: (String) -> Unit) {
    val all = remember { ToyRepository.allToys() }
    val categories = remember { listOf("All") + ToyRepository.categories() }
    var selected by remember { mutableStateOf("All") }
    val visible = remember(selected) {
        if (selected == "All") all else all.filter { it.category == selected }
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Text(
            "Curated authentic pieces",
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
            fontWeight = FontWeight.Bold,
        )
        Text(
            "Each piece is GI-tagged and made with natural lac.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 20.dp),
        )
        CategoryRow(categories, selected) { selected = it }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            itemsIndexed(visible, key = { _, t -> t.id }) { index, toy ->
                StaggeredItem(index) {
                    CatalogTile(toy, onClick = { onToyClick(toy.id) })
                }
            }
        }
    }
}

@Composable
private fun CategoryRow(categories: List<String>, selected: String, onSelect: (String) -> Unit) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(categories.size) { idx ->
            val cat = categories[idx]
            val isSel = cat == selected
            Box(
                Modifier
                    .background(
                        if (isSel) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.surface,
                        RoundedCornerShape(50),
                    )
                    .clickable { onSelect(cat) }
                    .padding(horizontal = 16.dp, vertical = 10.dp),
            ) {
                Text(
                    cat,
                    style = MaterialTheme.typography.labelLarge,
                    color = if (isSel) Color.White else MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }
}

@Composable
private fun CatalogTile(toy: Toy, onClick: () -> Unit) {
    val accent = Color(toy.accentHex)
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(
                        Brush.linearGradient(listOf(accent, accent.copy(alpha = 0.7f)))
                    ),
                contentAlignment = Alignment.Center,
            ) { Text(toy.emoji, fontSize = 56.sp) }
            Column(Modifier.padding(14.dp)) {
                Box(
                    Modifier
                        .background(accent.copy(alpha = 0.16f), RoundedCornerShape(50))
                        .padding(horizontal = 8.dp, vertical = 3.dp),
                ) {
                    Text(
                        toy.category,
                        style = MaterialTheme.typography.labelMedium,
                        color = accent,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
                Spacer(Modifier.height(6.dp))
                Text(
                    toy.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "₹${toy.priceInr}",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(Modifier.weight(1f))
                    Box(
                        Modifier
                            .size(7.dp)
                            .background(Color(0xFF3B9A8E), CircleShape),
                    )
                    Spacer(Modifier.size(4.dp))
                    Text(
                        "Verified",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color(0xFF3B9A8E),
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
        }
    }
}