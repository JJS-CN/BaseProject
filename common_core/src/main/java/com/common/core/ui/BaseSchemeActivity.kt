package com.common.core.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import com.common.core.constants.BaseSchemeActions
import com.common.core.ui.web.BaseWebViewActivity
import java.lang.Exception


/**
 *  Class: 透明需要交由外部处理
 *  Other:
 *  Create by jsji on  2021/7/12.
 */
abstract class BaseSchemeActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val processed = baseDispatch(intent.data)
    dispatchAfter(processed)
    try {
      val contentView = (window.decorView as ViewGroup).getChildAt(0)
      contentView.setOnClickListener {
        finish()
      }
    } catch(e: Exception) {
      e.printStackTrace()
    }
  }

  //分发处理具体内容
  private fun baseDispatch(data: Uri?): Boolean {
    if(data != null) {
      println("SchemeDispatch: $data")
      //先交由外部实现处理
      val isPatch = dispatch(data)
      if(!isPatch) {
        //处理默认提供的内容
        val authority = data.authority
        when(authority?.let { BaseSchemeActions.valueOf(it) }) {
          BaseSchemeActions.openUrlInApp -> {
            openUrlInApp(data)
          }
          BaseSchemeActions.openUrlInBrowser -> {
            openUrlInBrowser(data)
          }
          else -> {
            //如果不在默认实现范围之内
            return isPatch
          }
        }
      }
    } else {
      println("SchemeDispatch: uri is null")
    }
    return true
  }

  fun dispatchAfter(isProcessed: Boolean) {
    if(isProcessed) {
      //已处理，关闭
      finish()
    } else {
      //未处理，认为是低版本收到了对应内容。进行更新提示
      showUpdateTip()
    }
  }

  /***
   * @return true:已处理,不进行默认的网页判断，直接关闭activity
   */
  abstract fun dispatch(data: Uri): Boolean

  /**
   * 提示用户更新版本
   */
  open fun showUpdateTip() {
    setContent {
      Text(text = "版本过低，请前往各大应用市场更新版本！")
    }
  }

  open fun openUrlInApp(uri: Uri) {
    //打开app内部浏览器
    val webUrl = uri.getQueryParameter("url")
    if(webUrl.isNullOrEmpty()) {
      return
    }
    //这里建议由外部重写
    BaseWebViewActivity.launch(this, webUrl)

  }

  open fun openUrlInBrowser(uri: Uri) {
    //打开外部浏览器
    val webUrl = uri.getQueryParameter("url")
    if(webUrl.isNullOrEmpty()) {
      return
    }
    val intentBroswer = Intent(Intent.ACTION_VIEW, uri)
    intentBroswer.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(intentBroswer)
  }

}