package com.module.base.image

import android.util.Log
import android.widget.ImageView
import coil.Coil
import coil.imageLoader
import coil.loadAny
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.blankj.utilcode.util.ConvertUtils
import com.common.core.extensions.dp2px
import com.module.base.APPAction
import com.module.base.R
import com.xiaojinzi.component.support.OnRouterSuccess
import kotlinx.coroutines.flow.callbackFlow

/**
 *  Class: 基于coil图片加载库，对imageview进行方法扩展
 *  Other: 主要处理占位图的统一设置问题
 *  Create by jsji on  2021/7/2.
 */
fun ImageView.loadPhoto(any: Any?) {
  this.loadAppAny(any) {
    crossfade(true)
  }
}

//所有图片加载最好都走这里
private fun ImageView.loadAppAny(data: Any?,
                                 builder: ImageRequest.Builder.() -> Unit): coil.request.Disposable { /* compiled code */
  var url = data
  if(data != null) {
    if(data is String) {
      if(data.startsWith(APPAction.Consts.OSS_PHOTO_CLUB_NAME)) {
        //统一拦截附加前缀
        url = APPAction.Consts.picService + data
      }
    }
  }
  return this.loadAny(url, this.context.imageLoader, builder)
}

fun String.toPhotoUrl(): String {
  if(this.isNotEmpty()) {
    if(this.startsWith(APPAction.Consts.OSS_PHOTO_CLUB_NAME)) {
      //统一拦截附加前缀
      return APPAction.Consts.picService + this
    }
  }
  return this
}


fun ImageView.loadAvatar(any: Any?) {
  this.loadAppAny(any) {
    crossfade(true)
    placeholder(R.mipmap.module_base_img_touxiang_little)
    error(R.mipmap.module_base_img_touxiang_little)
    transformations(CircleCropTransformation())
  }
}

fun ImageView.loadRound(any: Any?, @androidx.annotation.Px radius: Float) {
  this.loadAppAny(any) {
    crossfade(true)
    placeholder(R.mipmap.module_base_img_book_fengmian)
    error(R.mipmap.module_base_img_book_fengmian)
    transformations(RoundedCornersTransformation(radius.dp2px()))
  }
}


fun ImageView.loadBook(any: Any?) {
  this.loadAppAny(any) {
    crossfade(true)
    placeholder(R.mipmap.module_base_img_book_fengmian)
    error(R.mipmap.module_base_img_book_fengmian)
    transformations(CircleCropTransformation())
  }
}


