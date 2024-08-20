package com.yousuf.sample.assesmentApp.imageLoader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream


class BitmapFetcher() {

    fun fetch(url: String): Bitmap {
        val httpClient = OkHttpClient()
        val request: Request = Request.Builder()
            .url(url)
            .build()

        httpClient.newCall(request).execute().body?.let { response ->
            var inputStream: InputStream? = null
            try {
                inputStream = response.byteStream()
                return processStream(response.byteStream())
            } catch (e: Exception) {
                throw RuntimeException("Error processing bitmap", e)
            } finally {
                inputStream?.close()
            }
        } ?: throw RuntimeException("Error retrieving bitmap")
    }

    private fun processStream(instream: InputStream): Bitmap {
        val baos = ByteArrayOutputStream()
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var len = 0
        try {
            // instream is content got from httpentity.getContent()
            while ((instream.read(buffer).also { len = it }) != -1) {
                baos.write(buffer, 0, len)
            }
            baos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val b = baos.toByteArray()
        return BitmapFactory.decodeByteArray(b, 0, b.size)
    }

}