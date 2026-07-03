package com.example.ocr

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import android.Manifest
import android.content.Context
import androidx.core.content.FileProvider
import java.io.File

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val helper = OCRHelper()

        setContent {

            var imageUri by remember {

                mutableStateOf<Uri?>(null)

            }

            var text by remember {

                mutableStateOf("")

            }


            val photoUri = remember {
                createImageUri(this@MainActivity)
            }

            val launcher =
                rememberLauncherForActivityResult(

                    contract = ActivityResultContracts.PickVisualMedia()

                ) {

                    imageUri = it

                }

            val cameraLauncher =
                rememberLauncherForActivityResult(
                    ActivityResultContracts.TakePicture()
                ) { success ->

                    if (success) {
                        imageUri = photoUri
                    }

                }

            val cameraPermissionLauncher =
                rememberLauncherForActivityResult(
                    ActivityResultContracts.RequestPermission()
                ) { granted ->

                    if (granted) {
                        cameraLauncher.launch(photoUri)
                    }

                }

            OCRScreen(
                imageUri = imageUri,
                extractedText = text,

                onCameraClick = {
                    cameraPermissionLauncher.launch(
                        Manifest.permission.CAMERA
                    )
                },

                onGalleryClick = {
                    launcher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                },

                onScanClick = {
                    imageUri?.let {

                        helper.scanImage(
                            context = this@MainActivity,
                            uri = it,
                            onSuccess = { text = it },
                            onFailure = { text = it.message ?: "" }
                        )

                    }
                }
            )
        }

    }


    private fun createImageUri(context: Context): Uri {

        val imageFile = File.createTempFile(
            "camera_image",
            ".jpg",
            context.cacheDir
        )

        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            imageFile
        )
    }
}


