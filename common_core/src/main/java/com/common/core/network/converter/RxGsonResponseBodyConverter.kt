package com.common.core.network.converter

import android.util.Base64
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Converter
import java.io.IOException

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/7.
 */
class RxGsonResponseBodyConverter<T>(gson: Gson, adapter: TypeAdapter<T>) :
  Converter<ResponseBody, T?> {
  private val gson: Gson
  private val adapter: TypeAdapter<T>

  override fun convert(value: ResponseBody): T {
    //把responsebody转为string
    var response = value.string()
    /*if(response != null && response.startsWith("{")) {
      val baseResponse: BaseResponse = gson.fromJson(response, BaseResponse::class.java)
      // 这里只是为了检测code是否!=1,所以只解析HttpStatus中的字段,因为只要code和message就可以了
      if(!baseResponse.isSuccess()) {
        value.close()
        //抛出一个RuntimeException, 这里抛出的异常会到subscribe的onError()方法中统一处理
        val errorMessage: String = baseResponse.getMessage()
        throw ApiException(baseResponse.getCode(), errorMessage)
      } else if(baseResponse.hasEncryption) {
        //如果需要base64解密
        try {
          val `object` = JSONObject(response)
          val data = `object`.getString("data")
          val decode = decodeEPG(data)
          if(decode != null) {
            if(decode.startsWith("{")) {
              val dataJson = JSONObject(decode)
              `object`.put("data", dataJson)
            } else if(decode.startsWith("[")) {
              val dataJson = JSONArray(decode)
              `object`.put("data", dataJson)
            } else {
              `object`.put("data", decode)
            }
          } else {
            `object`.put("data", null)
          }
          response = `object`.toString()
        } catch(e: JSONException) {
          e.printStackTrace()
        }
      }
    }*/
    return try {
      adapter.fromJson(response)
    } finally {
      value.close()
    }
  }

  //解密epg数据
  /*fun decodeEPG(content: String?): String? {
    val result: ByteArray
    var resolveContent: String? = null
    try {
      val buffer = StringBuffer(content)
      buffer.delete(0, 2)
      buffer.deleteCharAt(20)
      result = Base64.decode(buffer.toString(), Base64.DEFAULT)
      resolveContent = String(result)
    } catch(e: Exception) {
      e.printStackTrace()
    }
    return resolveContent
  }*/

  init {
    this.gson = gson
    this.adapter = adapter
  }

}
