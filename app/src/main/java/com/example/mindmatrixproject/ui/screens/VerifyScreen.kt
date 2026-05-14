package com.example.mindmatrixproject.ui.screens

import android.Manifest
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindmatrixproject.R
import com.example.mindmatrixproject.data.Artisan
import com.example.mindmatrixproject.data.Cooperative
import com.example.mindmatrixproject.data.Toy
import com.example.mindmatrixproject.data.ToyRepository
import com.example.mindmatrixproject.data.VerificationResult
import com.example.mindmatrixproject.ui.scanner.QrScannerView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun VerifyScreen(
    onArtisanClick: (String) -> Unit,
    onCoopClick: (String) -> Unit,
) {
    var idInput by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<VerificationResult?>(null) }
    var scannerOpen by remember { mutableStateOf(false) }
    var wantsScanner by remember { mutableStateOf(false) }
    val cameraPermission = rememberPermissionState(Manifest.permission.CAMERA)

    LaunchedEffect(cameraPermission.status) {
        if (wantsScanner && cameraPermission.status.isGranted()) {
            scannerOpen = true
            wantsScanner = false
        }
    }

    if (scannerOpen) {
        ScannerOverlay(
            hasPermission = cameraPermission.status.isGranted(),
            onClose = { scannerOpen = false },
            onCode = { raw ->
                val digits = raw.filter { it.isDigit() }.take(6)
                if (digits.length == 6) {
                    idInput = digits
                    result = ToyRepository.verifyToy(digits)
                    scannerOpen = false
                }
            },
        )
        return
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 12.dp),
    ) {
        VerifyHeader()
        Spacer(Modifier.height(20.dp))
        InputCard(
            idInput = idInput,
            onIdChange = { idInput = it.filter { ch -> ch.isDigit() }.take(6) },
            onVerify = { result = ToyRepository.verifyToy(idInput) },
            onScan = {
                if (cameraPermission.status.isGranted()) {
                    scannerOpen = true
                } else {
                    wantsScanner = true
                    cameraPermission.launchPermissionRequest()
                }
            },
        )
        Spacer(Modifier.height(20.dp))

        AnimatedVisibility(
            visible = result == null,
            enter = fadeIn(),
            exit = fadeOut(),
        ) { SampleIdsHint() }

        AnimatedVisibility(
            visible = result is VerificationResult.Authentic,
            enter = fadeIn() + slideInVertically { it / 4 },
            exit = fadeOut() + slideOutVertically { -it / 4 },
        ) {
            (result as? VerificationResult.Authentic)?.let { r ->
                Column {
                    AuthenticBanner(r.toy)
                    Spacer(Modifier.height(14.dp))
                    ToyHero(r.toy)
                    Spacer(Modifier.height(14.dp))
                    ArtisanLink(r.artisan, r.cooperative, onClick = { onArtisanClick(r.artisan.id) })
                    Spacer(Modifier.height(12.dp))
                    CoopLink(r.cooperative, onClick = { onCoopClick(r.cooperative.id) })
                    Spacer(Modifier.height(40.dp))
                }
            }
        }

        AnimatedVisibility(
            visible = result is VerificationResult.NotFound || result is VerificationResult.InvalidFormat,
            enter = fadeIn() + slideInVertically { it / 4 },
            exit = fadeOut(),
        ) {
            val message = when (result) {
                VerificationResult.InvalidFormat -> stringResource(R.string.verify_invalid)
                VerificationResult.NotFound -> stringResource(R.string.verify_not_found)
                else -> ""
            }
            ErrorCard(message)
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
private fun PermissionStatus.isGranted(): Boolean = this is PermissionStatus.Granted

@Composable
private fun VerifyHeader() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier
                .size(48.dp)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.16f), CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                Icons.Default.Verified,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
            )
        }
        Spacer(Modifier.width(12.dp))
        Column {
            Text(
                "Authenticity check",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
            )
            Text(
                "Enter the 6-digit ID or scan the QR sticker",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun InputCard(
    idInput: String,
    onIdChange: (String) -> Unit,
    onVerify: () -> Unit,
    onScan: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Column(Modifier.padding(20.dp)) {
            OutlinedTextField(
                value = idInput,
                onValueChange = onIdChange,
                placeholder = { Text("e.g. 100001", color = MaterialTheme.colorScheme.onSurfaceVariant) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                ),
            )
            Spacer(Modifier.height(14.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = onVerify,
                    enabled = idInput.isNotEmpty(),
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                ) {
                    Text(
                        stringResource(R.string.verify_button),
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
                OutlinedButton(
                    onClick = onScan,
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(14.dp),
                ) {
                    Icon(Icons.Default.QrCodeScanner, contentDescription = null)
                    Spacer(Modifier.size(6.dp))
                    Text(
                        stringResource(R.string.verify_scan_qr),
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
        }
    }
}

@Composable
private fun ScannerOverlay(hasPermission: Boolean, onClose: () -> Unit, onCode: (String) -> Unit) {
    Box(Modifier.fillMaxSize().background(Color.Black)) {
        if (hasPermission) {
            QrScannerView(onQrFound = onCode)
        } else {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Camera permission needed to scan", color = Color.White)
            }
        }
        IconButton(
            onClick = onClose,
            modifier = Modifier
                .padding(16.dp)
                .background(Color(0x66000000), CircleShape),
        ) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
        }
        Box(
            Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .align(Alignment.BottomCenter),
        ) {
            Text(
                "Point at the QR sticker on your toy",
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun SampleIdsHint() {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(Modifier.padding(18.dp)) {
            Text(
                "Try a sample",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(Modifier.height(6.dp))
            Text(
                "100001 · 200015 · 300042 · 400077 · 500099",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun ErrorCard(message: String) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE9E9)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Warning, null, tint = Color(0xFFB00020))
            Spacer(Modifier.size(10.dp))
            Text(
                message,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF600000),
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
private fun AuthenticBanner(toy: Toy) {
    val gradient = Brush.linearGradient(
        listOf(Color(0xFF2A9D8F), Color(0xFF52B8A1))
    )
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(gradient)
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(28.dp),
            )
            Spacer(Modifier.size(12.dp))
            Column {
                Text(
                    stringResource(R.string.verify_authentic),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                )
                Text(
                    "ID ${toy.id} · GI tagged",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.9f),
                )
            }
        }
    }
}

@Composable
private fun ToyHero(toy: Toy) {
    val accent = Color(toy.accentHex)
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(
                        Brush.linearGradient(listOf(accent, accent.copy(alpha = 0.7f)))
                    ),
                contentAlignment = Alignment.Center,
            ) { Text(toy.emoji, fontSize = 76.sp) }
            Column(Modifier.padding(18.dp)) {
                Text(
                    toy.name,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    toy.category,
                    style = MaterialTheme.typography.bodyMedium,
                    color = accent,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }
}

@Composable
private fun ArtisanLink(artisan: Artisan, coop: Cooperative, onClick: () -> Unit) {
    val accent = Color(coop.accentHex)
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .size(60.dp)
                    .background(accent.copy(alpha = 0.18f), CircleShape),
                contentAlignment = Alignment.Center,
            ) { Text(artisan.photoEmoji, fontSize = 30.sp) }
            Spacer(Modifier.size(14.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    "Made by",
                    style = MaterialTheme.typography.labelMedium,
                    color = accent,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    artisan.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    "${artisan.yearsOfCraft} yrs · Member of ${coop.name.take(28)}${if (coop.name.length > 28) "…" else ""}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = accent,
                modifier = Modifier.size(20.dp),
            )
        }
    }
}

@Composable
private fun CoopLink(coop: Cooperative, onClick: () -> Unit) {
    val accent = Color(coop.accentHex)
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .size(50.dp)
                    .background(accent.copy(alpha = 0.18f), CircleShape),
                contentAlignment = Alignment.Center,
            ) { Text(coop.emoji, fontSize = 26.sp) }
            Spacer(Modifier.size(14.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    "Cooperative",
                    style = MaterialTheme.typography.labelMedium,
                    color = accent,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    coop.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    coop.locality,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = accent,
                modifier = Modifier.size(20.dp),
            )
        }
    }
}