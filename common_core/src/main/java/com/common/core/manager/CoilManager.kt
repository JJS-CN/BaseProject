package com.common.core.manager

import android.app.Application
import android.os.Build
import coil.Coil
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.util.CoilUtils
import okhttp3.OkHttpClient

/**
 *  Class:初始化Coil一些基本配置和默认占位图
 *  Other: 支持gif
 *  Create by jsji on  2021/7/2.
 */
object CoilManager {
    fun init(app: Application, placeholderId: Int) {
        val imageLoader = ImageLoader.Builder(app)
            .crossfade(true)
            .okHttpClient {
                OkHttpClient.Builder()
                    .cache(CoilUtils.createDefaultCache(app))
                    .build()
            }
            .placeholder(placeholderId)
            .componentRegistry {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder(app))
                } else {
                    add(GifDecoder())
                }
            }
            .build()
        Coil.setImageLoader(imageLoader)
    }
}