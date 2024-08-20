package com.yousuf.sample.assesmentApp.imageLoader

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.collection.LruCache
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class ImageLoader {

    private val cache = LruCache<String, Bitmap>(10)
    private val fetcher = BitmapFetcher()

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun load(url: String): Bitmap {
        return GlobalScope.async(Dispatchers.IO) {
            cache[url] ?:
             fetchImageFromNetwork(url).also { cache.put(url, it) }
        }.await()
    }

    private fun fetchImageFromNetwork(url: String) : Bitmap {
        return fetcher.fetch(url)
    }

    companion object {
        @Volatile private var instance: ImageLoader? = null

        fun get() =
            instance ?: synchronized(this) {
                instance ?: ImageLoader().also { instance = it }
            }

    }
}

fun Bitmap.into(imageView: ImageView) {
    imageView.setImageBitmap(this)
}