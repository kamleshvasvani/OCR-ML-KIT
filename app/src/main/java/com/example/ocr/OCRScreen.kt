package com.example.ocr

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun OCRScreen(
    imageUri: Uri?,
    extractedText: String,
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit,
    onScanClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            text = "Smart OCR Scanner",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Button(
                onClick = onCameraClick,
                modifier = Modifier.weight(1f)
            ) {
                Text("📷 Camera")
            }

            Button(
                onClick = onGalleryClick,
                modifier = Modifier.weight(1f)
            ) {
                Text("🖼 Gallery")
            }

        }

        Spacer(modifier = Modifier.height(20.dp))

        imageUri?.let {

            AsyncImage(
                model = it,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onScanClick,
            enabled = imageUri != null,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Scan Text")

        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "OCR Result",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = if (extractedText.isEmpty()) "No Text" else extractedText,
                modifier = Modifier.padding(16.dp)
            )

        }

    }

}