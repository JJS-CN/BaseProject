package com.common.core.network.converter

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type
import java.util.*

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/7.
 */
class RxGsonConverterFactory private constructor(gson: Gson?) : Converter.Factory() {
  private val gson: Gson
  fun getGson(): Gson {
    return gson
  }

  override fun responseBodyConverter(type: Type?, annotations: Array<Annotation?>?,
                                     retrofit: Retrofit?): Converter<ResponseBody, *> {
    val adapter = gson.getAdapter(TypeToken.get(type))
    return RxGsonResponseBodyConverter(gson, adapter)
  }

  override fun requestBodyConverter(type: Type?,
                                    parameterAnnotations: Array<Annotation?>?,
                                    methodAnnotations: Array<Annotation?>?,
                                    retrofit: Retrofit?): Converter<*, RequestBody> {
    val adapter = gson.getAdapter(TypeToken.get(type))
    return RxGsonRequestBodyConverter(gson, adapter)
  }

  companion object {
    fun create(): RxGsonConverterFactory {
      val gsonBuilder = GsonBuilder()
        /*.registerTypeAdapter(Int::class.javaPrimitiveType, IntTypeAdapter())
        .registerTypeAdapter(Int::class.java, IntTypeAdapter())
        .registerTypeAdapter(Double::class.javaPrimitiveType, DoubleTypeAdapter())
        .registerTypeAdapter(Double::class.java, DoubleTypeAdapter())*/
      if(typeAdapter != null) {
        for((key, value) in typeAdapter) {
          gsonBuilder.registerTypeAdapter(key, value)
        }
      }
      return create(gsonBuilder.create())
    }

    private val typeAdapter: HashMap<Class<*>?, Any?>? = HashMap()
    fun addTypeAdapter(cla: Class<*>?, obj: Any?) {
      //供外部添加解释器
      typeAdapter!![cla] = obj
    }

    fun create(gson: Gson?): RxGsonConverterFactory {
      return RxGsonConverterFactory(gson)
    }
  }

  init {
    if(gson == null) {
      throw NullPointerException("gson == null")
    }
    this.gson = gson
  }
}