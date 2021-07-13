package com.common.core.ui.web

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import com.common.core.R
import com.common.core.databinding.CommonCoreLayoutFrameEmptyBinding
import com.common.core.ui.BaseActivity

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/12.
 */
class BaseWebViewActivity : BaseActivity<CommonCoreLayoutFrameEmptyBinding>() {
  companion object {
    val KEY_URL = "KEY_URL"

    //val KEY_TITLE = "KEY_TITLE"
    fun launch(context: Context, url: String?) {
      if(url.isNullOrEmpty()) {
        return
      }
      val intent = Intent(context, BaseWebViewActivity::class.java)
      intent.putExtra(KEY_URL, url)
      //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      context.startActivity(intent)
    }
  }

  lateinit var webFragment: BaseWebFragment


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    webFragment = createWebFragment()
    supportFragmentManager.beginTransaction().replace(R.id.frame_layout, webFragment)
      .commitAllowingStateLoss()
  }

  open fun createWebFragment(): BaseWebFragment {
    val webFragment = BaseWebFragment()
    webFragment.arguments = intent.extras
    return webFragment
  }

  override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
    if(webFragment.onKeyDown(keyCode, event)) {
      return true
    }
    return super.onKeyDown(keyCode, event)
  }
}