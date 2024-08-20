package com.yousuf.sample.assesmentApp

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.yousuf.sample.assesmentApp.imageLoader.ImageLoader
import com.yousuf.sample.assesmentApp.imageLoader.into
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart() {
        super.onStart()
        loadImage()
    }

    private fun loadImage() {
        lifecycleScope.launch {
            val imageView = findViewById<ImageView>(R.id.image)
            ImageLoader.get()
                .load(URL)
                .into(imageView)
        }
    }

    companion object {
        private const val URL = "https://lh6.ggpht.com/9SZhHdv4URtBzRmXpnWxZcYhkgTQurFuuQ8OR7WZ3R7fyTmha77dYkVvcuqMu3DLvMQ=w300"
    }
}
//
//ImageLoader -- Singleton
//
//        map (request, )