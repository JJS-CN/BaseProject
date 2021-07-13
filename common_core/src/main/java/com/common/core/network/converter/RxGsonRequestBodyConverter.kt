package com.common.core.network.converter

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.Buffer
import retrofit2.Converter
import java.io.IOException
import java.io.OutputStreamWriter
import java.io.Writer
import java.nio.charset.Charset

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/7.
 */
class RxGsonRequestBodyConverter<T>(val gson: Gson, val adapter: TypeAdapter<T>) :
  Converter<T, RequestBody> {


  companion object {
    private val MEDIA_TYPE: MediaType = "application/json".toMediaType()
    private val UTF_8 = Charset.forName("UTF-8")
  }

  override fun convert(value: T): RequestBody {
    val buffer = Buffer()
    val writer: Writer = OutputStreamWriter(buffer.outputStream(), UTF_8)
    val jsonWriter = gson.newJsonWriter(writer)
    adapter.write(jsonWriter, value)
    jsonWriter.close()
    return buffer.readByteString().toRequestBody(MEDIA_TYPE)
  }

}