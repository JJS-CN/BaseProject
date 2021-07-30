package com.library.oss.manager

import android.content.Context
import com.alibaba.sdk.android.oss.*
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider
import com.alibaba.sdk.android.oss.model.PutObjectRequest
import com.alibaba.sdk.android.oss.model.PutObjectResult
import com.library.oss.data.ResultData
import java.io.File
import java.security.MessageDigest

/**
 * Class:
 * Other:
 * Create by jsji on  2021/7/14.
 */
class OssUploadFileManager {
  fun uploadFile(context: Context,
                 bucketName: String,
                 clubName: String,
                 onlyKey: String,
                 path: String,
                 accessKeyId: String,
                 accessKeySecret: String,
                 securityToken: String,
                 block: ((result: ResultData) -> Unit)) {
    val file = File(path)
    if(!file.exists()) {
      block.invoke(ResultData(path, false, "文件不存在"))
      return
    }
    val name = file.name
    val suffix = name.substring(name.lastIndexOf(".") + 1)

    //文件名为 用户uid_文件名称格式 防止不同用户有相同文件名字覆盖掉
    val nameMd5 = md5(onlyKey + System.currentTimeMillis().toString())
    val fileName = "$clubName/$nameMd5.$suffix"
    println("待上传文件path: $path")
    println("name: $fileName")
    // 构造上传请求。
    val put = PutObjectRequest(bucketName, fileName, path)

// 异步上传时可以设置进度回调。
    put.setProgressCallback(object : OSSProgressCallback<PutObjectRequest?> {
      override fun onProgress(request: PutObjectRequest?, currentSize: Long, totalSize: Long) {
        println("currentSize: $currentSize totalSize: $totalSize")
      }
    })
    val endpoint = "https://oss-cn-hangzhou.aliyuncs.com"

// 推荐使用OSSAuthCredentialsProvider。token过期可以及时更新。
    val credentialProvider: OSSCredentialProvider = OSSStsTokenCredentialProvider(
      accessKeyId,
      accessKeySecret,
      securityToken
    )

// 配置类如果不设置，会有默认配置。
    val conf = ClientConfiguration()
    conf.setConnectionTimeout(15 * 1000) // 连接超时，默认15秒。
    conf.setSocketTimeout(15 * 1000) // socket超时，默认15秒。
    conf.setMaxConcurrentRequest(5) // 最大并发请求数，默认5个。
    conf.setMaxErrorRetry(2) // 失败后最大重试次数，默认2次。
    val oss: OSS = OSSClient(context, endpoint, credentialProvider)
    val task = oss.asyncPutObject(put,
      object : OSSCompletedCallback<PutObjectRequest?, PutObjectResult?> {
        override fun onSuccess(request: PutObjectRequest?, result: PutObjectResult?) {
          //真实文件地址
          println("UploadSuccess: $fileName")
          block.invoke(ResultData(fileName, true, "上传成功"))
        }


        override fun onFailure(request: PutObjectRequest?,
                               clientExcepion: ClientException?,
                               serviceException: ServiceException?) {
          clientExcepion?.printStackTrace()
          block.invoke(ResultData(path, false, "上传失败"))
        }
      })
  }

  fun uploadFileList(context: Context,
                     bucketName: String,
                     clubName: String,
                     onlyKey: String,
                     paths: List<String>,
                     accessKeyId: String,
                     accessKeySecret: String,
                     securityToken: String,
                     block: (allSuccess: Boolean, results: ArrayList<ResultData>) -> Unit
  ) {
    val results = ArrayList<ResultData>()
    for(i in paths.indices) {
      val path = paths[i]
      if(path.isNullOrEmpty()) {
        //空
        results.add(ResultData("", false, "图片地址为空"))
        checkResultList(results, paths.size, block)
      } else if(path.startsWith(clubName)) {
        //上传成功的
        results.add(ResultData(path, true, "上传成功"))
        checkResultList(results, paths.size, block)
      } else {
        //其他
        uploadFile(
          context,
          bucketName,
          clubName,
          onlyKey + i, //由于for循环很快，内部时间戳可能未变化，所以这里附加下标
          path,
          accessKeyId,
          accessKeySecret,
          securityToken) {
          results.add(it)
          checkResultList(results, paths.size, block)
        }
      }
    }
  }

  private fun checkResultList(results: ArrayList<ResultData>,
                              maxSize: Int,
                              block: (allSuccess: Boolean, results: ArrayList<ResultData>) -> Unit) {
    if(results.size == maxSize) {
      var isAllSuccess = true
      results.forEach {
        if(!it.isSuccess) {
          isAllSuccess = false
          return@forEach
        }
      }
      block.invoke(isAllSuccess, results)
    }
  }

  private fun md5(value: String): String {
    val bytes = MessageDigest.getInstance("MD5").digest(value.toByteArray())
    return bytes.hex()
  }

  private fun ByteArray.hex(): String {
    return joinToString("") { "%02X".format(it) }
  }

}