package com.common.core.ui

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import com.common.core.R
import com.common.core.constants.SchemeConstants
import com.common.core.databinding.CommonCoreLayoutFrameEmptyBinding
import com.common.core.ui.web.BaseWebViewActivity
import com.tencent.smtt.utils.j
import com.tencent.smtt.utils.o
import java.lang.Exception


/**
 *  Class: 透明需要交由外部处理
 *  Other:
 *  Create by jsji on  2021/7/12.
 */
abstract class BaseSchemeActivity : BaseActivity<CommonCoreLayoutFrameEmptyBinding>() {


  init {
    needAutoTitleBar = false
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val processed = baseDispatch(intent.data)
    dispatchAfter(processed)
    try{
      val contentView = (window.decorView as ViewGroup).getChildAt(0)
      contentView.setOnClickListener {
        finish()
      }
    }catch(e:Exception){
      e.printStackTrace()
    }
  }

  //分发处理具体内容
  private fun baseDispatch(data: Uri?): Boolean {
    if(data != null) {
      println("SchemeDispatch: $data")
      //处理默认提供的内容
      when(data.authority) {
        SchemeConstants.SCHEME_OPENURL_INAPP -> {
          openUrlInApp(data)
        }
        SchemeConstants.SCHEME_OPENURL_INBROWSER -> {
          openUrlInBrowser(data)
        }
        else -> {
          //如果不在默认实现范围之内，交由外部实现处理
          return dispatch(data)
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
   * @return true:已处理
   */
  abstract fun dispatch(data: Uri): Boolean

  /**
   * 提示用户更新版本
   */
  open fun showUpdateTip() {
    setContentView(R.layout.common_core_layout_version_update)
  }

  fun openUrlInApp(uri: Uri) {
    //打开app内部浏览器
    val webUrl = uri.getQueryParameter("url")
    val title = uri.getQueryParameter("title")
    if(webUrl.isNullOrEmpty()) {
      return
    }
    //这里建议由外部重写
    BaseWebViewActivity.launch(this, webUrl)

    //MoreActivity.start(this, title, content, url, iconUrl, "1" == share, "scheme")
  }

  fun openUrlInBrowser(uri: Uri) {
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