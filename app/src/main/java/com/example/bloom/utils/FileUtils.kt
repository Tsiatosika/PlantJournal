package com.example.bloom.utils

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object FileUtils {
    fun saveImageToInternalStorage(uri: Uri, context: Context): String {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri)
        val directory = File(context.filesDir, "plant_images")

        if (!directory.exists()) {
            directory.mkdirs()
        }

        val file = File(directory, "${System.currentTimeMillis()}.jpg")
        val outputStream = FileOutputStream(file)

        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }

        return file.absolutePath
    }

    fun formatTimestamp(timestamp: Long): String {
        return SimpleDateFormat("dd MMMM yyyy à HH:mm", Locale.FRENCH).format(Date(timestamp))
    }

    fun formatDetailedTimestamp(timestamp: Long): String {
        return SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.FRENCH).format(Date(timestamp))
    }
}