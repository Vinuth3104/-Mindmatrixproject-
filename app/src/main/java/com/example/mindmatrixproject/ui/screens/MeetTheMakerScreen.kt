package com.example.mindmatrixproject.ui.screens

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.net.Uri
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.mindmatrixproject.R
import com.example.mindmatrixproject.data.Artisan
import com.example.mindmatrixproject.data.Cooperative
import com.example.mindmatrixproject.data.ToyRepository
import com.example.mindmatrixproject.ui.components.StaggeredItem
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun MeetTheMakerScreen(
    onCoopClick: (String) -> Unit,
    onArtisanClick: (String) -> Unit,
) {
    val coops = remember { ToyRepository.allCooperatives() }
    val context = LocalContext.current

    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            StaggeredItem(0) { Header() }
        }
        item {
            StaggeredItem(1) { OsmMap(coops, onCoopClick) }
        }
        itemsIndexed(coops, key = { _, c -> c.id }) { idx, coop ->
            StaggeredItem(idx + 2) {
                CooperativeCard(
                    coop = coop,
                    members = ToyRepository.artisansFor(coop.id),
                    onCoopClick = { onCoopClick(coop.id) },
                    onArtisanClick = onArtisanClick,
                    onOpenInMaps = { openInMaps(context, coop) },
                )
            }
        }
        item {
            StaggeredItem(coops.size + 2) { Disclaimer() }
        }
    }
}

@Composable
private fun Header() {
    Column(Modifier.padding(top = 4.dp)) {
        Text(
            stringResource(R.string.makers_title),
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(6.dp))
        Text(
            stringResource(R.string.makers_intro),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun OsmMap(coops: List<Cooperative>, onCoopClick: (String) -> Unit) {
    val context = LocalContext.current
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth().height(320.dp),
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(24.dp)),
            factory = { ctx ->
                MapView(ctx).apply {
                    setTileSource(TileSourceFactory.MAPNIK)
                    setMultiTouchControls(true)
                    controller.setZoom(13.5)
                    controller.setCenter(GeoPoint(12.6515, 77.2090))

                    coops.forEach { coop ->
                        val marker = Marker(this).apply {
                            position = GeoPoint(coop.lat, coop.lng)
                            title = coop.name
                            subDescription = coop.locality
                            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                            icon = circleMarkerDrawable(ctx, Color(coop.accentHex))
                            setOnMarkerClickListener { _, _ ->
                                onCoopClick(coop.id)
                                true
                            }
                        }
                        overlays.add(marker)
                    }
                }
            },
            update = { view ->
                view.invalidate()
            },
        )
    }
}

private fun circleMarkerDrawable(context: Context, color: Color): GradientDrawable {
    val sizeDp = 30
    val density = context.resources.displayMetrics.density
    val sizePx = (sizeDp * density).toInt()
    return GradientDrawable().apply {
        shape = GradientDrawable.OVAL
        setColor(android.graphics.Color.argb(
            (color.alpha * 255).toInt(),
            (color.red * 255).toInt(),
            (color.green * 255).toInt(),
            (color.blue * 255).toInt(),
        ))
        setStroke((3 * density).toInt(), android.graphics.Color.WHITE)
        setSize(sizePx, sizePx)
    }
}

@Composable
private fun CooperativeCard(
    coop: Cooperative,
    members: List<Artisan>,
    onCoopClick: () -> Unit,
    onArtisanClick: (String) -> Unit,
    onOpenInMaps: () -> Unit,
) {
    val accent = Color(coop.accentHex)
    Card(
        onClick = onCoopClick,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(
                        Brush.linearGradient(listOf(accent, accent.copy(alpha = 0.6f)))
                    )
            )
            Column(Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier
                            .size(52.dp)
                            .background(accent.copy(alpha = 0.18f), CircleShape),
                        contentAlignment = Alignment.Center,
                    ) { Text(coop.emoji, fontSize = 26.sp) }
                    Spacer(Modifier.size(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(
                            coop.name,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            coop.type,
                            style = MaterialTheme.typography.bodySmall,
                            color = accent,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = accent,
                    )
                }

                Spacer(Modifier.height(12.dp))
                DetailRow("Address", coop.address)
                DetailRow("Open", coop.openHours)
                DetailRow("Contact", coop.publicContact)

                if (members.isNotEmpty()) {
                    Spacer(Modifier.height(14.dp))
                    Text(
                        "Featured members",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        members.forEach { ArtisanChip(it, accent, onClick = { onArtisanClick(it.id) }) }
                    }
                }

                Spacer(Modifier.height(14.dp))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(accent.copy(alpha = 0.14f), RoundedCornerShape(14.dp))
                        .clickable(onClick = onOpenInMaps)
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = accent,
                        modifier = Modifier.size(18.dp),
                    )
                    Spacer(Modifier.size(8.dp))
                    Text(
                        "Open in Maps",
                        style = MaterialTheme.typography.labelLarge,
                        color = accent,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.weight(1f),
                    )
                    Icon(
                        Icons.AutoMirrored.Filled.OpenInNew,
                        contentDescription = null,
                        tint = accent,
                        modifier = Modifier.size(16.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun ArtisanChip(artisan: Artisan, accent: Color, onClick: () -> Unit) {
    Row(
        Modifier
            .background(accent.copy(alpha = 0.12f), RoundedCornerShape(50))
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            Modifier
                .size(26.dp)
                .background(accent.copy(alpha = 0.25f), CircleShape),
            contentAlignment = Alignment.Center,
        ) { Text(artisan.photoEmoji, fontSize = 14.sp) }
        Spacer(Modifier.size(6.dp))
        Text(
            artisan.name,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth().padding(vertical = 3.dp)) {
        Text(
            label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.width(78.dp),
        )
        Text(
            value,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
private fun Disclaimer() {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            "Cooperatives shown are real, publicly-listed Channapatna craft entities. Featured members are illustrative artisan profiles, not real individuals. Map data: © OpenStreetMap contributors.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(14.dp),
        )
    }
}

private fun openInMaps(context: Context, coop: Cooperative) {
    val uri = Uri.parse("geo:${coop.lat},${coop.lng}?q=${Uri.encode(coop.name + ", " + coop.address)}")
    val intent = Intent(Intent.ACTION_VIEW, uri).apply {
        setPackage("com.google.android.apps.maps")
    }
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        context.startActivity(Intent(Intent.ACTION_VIEW, uri))
    }
}
