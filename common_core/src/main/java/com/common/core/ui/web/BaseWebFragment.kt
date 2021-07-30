package com.common.core.ui.web

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.*
import com.common.core.databinding.CommonCoreFragmentWebviewBinding
import com.common.core.manager.TBSManager
import com.common.core.ui.BaseFragment
import com.tencent.smtt.sdk.*

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/12.
 */
class BaseWebFragment : BaseFragment<CommonCoreFragmentWebviewBinding>() {
  override fun initView() {
    super.initView()
    initWebView()
    activity?.application?.let { TBSManager.init(it) }
    initHardwareAccelerate()
    val url = arguments?.getString(BaseWebViewActivity.KEY_URL)
    println("webFragment load:$url")
    url?.let { binding.web.loadUrl(it) }
    //清理缓存和历史（非必须）
    binding.web.clearCache(true);
    binding.web.clearHistory();
  }

  //启用硬件加速：由于硬件加速自身并非完美无缺，
  // 所以Android提供选项来打开或者关闭硬件加速，默认是关闭。
  // 可以在4个级别上打开或者关闭硬件加速
  fun initHardwareAccelerate() {
    try {
      //Window级别硬件加速
      activity?.getWindow()
        ?.setFlags(
          WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
          WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
        )
    } catch(e: Exception) {
    }
  }


  fun initWebView() {
    val webSettings = binding.web.getSettings();
    //webSettings.javaScriptEnabled = true;//支持js 提示过期
    //设置可以访问文件
    webSettings.setAllowFileAccess(true);
    //设置支持缩放
    webSettings.setBuiltInZoomControls(true);
    webSettings.setUseWideViewPort(true);
    webSettings.setLoadWithOverviewMode(true);
    webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
    binding.web.setVerticalScrollBarEnabled(false);
    binding.web.setHorizontalScrollBarEnabled(false);
    webSettings.setAppCacheEnabled(true);
    webSettings.setDomStorageEnabled(true);
    webSettings.supportMultipleWindows();
    webSettings.setAllowContentAccess(true);
    webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
    webSettings.setSavePassword(true);
    webSettings.setSaveFormData(true);
    webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      webSettings.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
    }
//        myWebView.requestFocusFromTouch();//如果webView中需要用户手动输入用户名、密码或其他，则webview必须设置支持获取手势焦点
    webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //允许js弹窗

    binding.web.webViewClient = object : WebViewClient() {
      override fun onPageStarted(webView: WebView?, url: String?, bitmap: Bitmap?) {
        super.onPageStarted(webView, url, bitmap)
      }

      override fun onPageFinished(webView: WebView?, url: String?) {
        super.onPageFinished(webView, url)
      }

      override fun shouldOverrideUrlLoading(webView: WebView?, url: String?): Boolean {
        webView?.loadUrl(url)
        return true;
      }

      override fun onLoadResource(p0: WebView?, p1: String?) {
        super.onLoadResource(p0, p1)
      }

    }
    binding.web.webChromeClient = object : WebChromeClient() {
      override fun onReceivedTitle(webView: WebView?, title: String?) {
        super.onReceivedTitle(webView, title)
        activity?.setTitle(title)
      }
    }


    // 注意一定要放在WebView加载后，不然只会管理内核的缓存
    CookieSyncManager.createInstance(activity?.application);
    CookieSyncManager.getInstance().sync();

  }

  fun onKeyDown(keyCode: Int): Boolean {
    //如果不做任何处理，浏览网页，点击系统“Back”键，整个Browser会调用finish()而结束自身，
    // 如果希望浏览的网 页回退而不是推出浏览器，需要在当前Activity中处理并消费掉该Back事件。
    if(keyCode == KeyEvent.KEYCODE_BACK && binding.web.canGoBack()) {
      binding.web.goBack();
      return true;
    }
    return false
  }

  override fun onResume() {
    super.onResume()
    binding.web.onResume()
  }

  override fun onPause() {
    super.onPause()
    binding.web.onPause()
  }

  override fun onDestroy() {
    super.onDestroy()
    binding.root.removeAllViews()
    binding.web.destroy()
  }
}