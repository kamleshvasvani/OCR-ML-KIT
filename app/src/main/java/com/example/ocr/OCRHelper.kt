package com.example.ocr

import android.content.Context
import android.net.Uri
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class OCRHelper {

    private val recognizer =
        TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    fun scanImage(
        context: Context,
        uri: Uri,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {

        val image = InputImage.fromFilePath(context, uri)

        recognizer.process(image)
            .addOnSuccessListener {

                onSuccess(it.text)

            }
            .addOnFailureListener {

                onFailure(it)

            }

    }
}