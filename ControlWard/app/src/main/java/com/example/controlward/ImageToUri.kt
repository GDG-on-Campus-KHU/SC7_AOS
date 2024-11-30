package com.example.controlward

import android.content.Context
import android.net.Uri
import android.widget.Toast
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

fun getFileFromUri(context: Context, uri: Uri): File? {
    val contentResolver = context.contentResolver
    var file: File? = null

    try {
        val inputStream: InputStream? = contentResolver.openInputStream(uri)

        val tempFile = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")
        val outputStream: OutputStream = FileOutputStream(tempFile)

        inputStream?.copyTo(outputStream)

        file = tempFile
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, "파일을 처리하는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
    }

    return file
}

suspend fun uploadImageToImgur(
    imageFile: File?
): String {
    return suspendCoroutine { continuation ->
        val clientId = BuildConfig.imgurClientId

        val client = OkHttpClient()

        val mediaType = "image/jpeg".toMediaTypeOrNull()
        val requestBody = imageFile?.let { RequestBody.create(mediaType, it) }

        val multipartBody = requestBody?.let {
            MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", imageFile.name, it)
                .build()
        }

        val request = multipartBody?.let {
            Request.Builder()
                .url("https://api.imgur.com/3/image")
                .post(it)
                .addHeader("Authorization", "Client-ID $clientId")
                .build()
        }

        request?.let {
            client.newCall(it).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWith(Result.failure(e))
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        val jsonResponse = JSONObject(responseBody ?: "")
                        val imageUrl = jsonResponse.getJSONObject("data").getString("link")
                        continuation.resume(imageUrl)
                    } else {
                        continuation.resumeWith(Result.failure(IOException(response.message)))
                    }
                }
            })
        }
    }
}
