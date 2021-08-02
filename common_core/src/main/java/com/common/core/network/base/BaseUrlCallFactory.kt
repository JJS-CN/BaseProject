package com.common.core.network.base

import android.util.Log
import androidx.annotation.Nullable
import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Request


/**
 *  Class:
 *  Other: apiService中添加headers，然后判断这个参数来处理url的动态更换
 *  Create by jsji on  2021/7/1.
 */
class BaseUrlCallFactory(val baseUrl: String, val delegate: Call.Factory) : Call.Factory {
  companion object {
    //添加Header的时候，作为key使用。例子：NAME_BASE_URL_KEY_PREFIX + ":" + "USER"
    const val NAME_BASE_URL_KEY_PREFIX = "BaseUrlName"
  }

  private val urlMap = HashMap<String, String>()

  /**
   * @param serviceName 服务器名称，需要和HEADERS添加的一致，newCall中自动处理替换逻辑
   */
  fun addServiceUrlPair(serviceName: String, url: String) {
    urlMap[serviceName] = url
  }

  override fun newCall(request: Request): Call {
    val baseUrlName = request.header(NAME_BASE_URL_KEY_PREFIX)
    val url = urlMap[baseUrlName]
    if(baseUrlName != null && url != null) {
      val newUrl = request.url.toString().replace(baseUrl, url).toHttpUrl()
      val newRequest: Request = request.newBuilder().url(newUrl).build()
      return delegate.newCall(newRequest);
    }
    return delegate.newCall(request);
  }
}