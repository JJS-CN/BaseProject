package com.module.base.image

import android.widget.ImageView
import coil.loadAny
import coil.transform.CircleCropTransformation

/**
 *  Class: 基于coil图片加载库，对imageview进行方法扩展
 *  Other: 主要处理占位图的统一设置问题
 *  Create by jsji on  2021/7/2.
 */
fun ImageView.loadImg(any: Any) {
  this.loadAny(any) {
    crossfade(true)
    transformations(CircleCropTransformation())
  }
}

fun ImageView.loadUser(any: Any) {
  this.loadAny(any) {
    crossfade(true)
    transformations(CircleCropTransformation())
  }
}

fun ImageView.loadBook(any: Any) {
  this.loadAny(any) {
    crossfade(true)
    transformations(CircleCropTransformation())
  }
}
