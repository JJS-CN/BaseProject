package com.library.imagedetail.ui

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import com.common.core.ui.BaseActivity
import com.drawable.library.databinding.LibraryImagedetailActivityPhotoDetailsBinding

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/29.
 */
class PhotoDetailsActivity : BaseActivity<LibraryImagedetailActivityPhotoDetailsBinding>() {
  companion object {
    fun start(context: Context?, sharedElement: View) {
      val intent = Intent(context, PhotoDetailsActivity::class.java)
      if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && context is Activity) {
        sharedElement.transitionName = "123"
        val options = ActivityOptions.makeSceneTransitionAnimation(
          context, sharedElement, "123")
        context.startActivity(intent, options.toBundle())
      } else {
        context?.startActivity(intent)
      }
    }
  }

  override fun initView() {
    super.initView()
  }
}