package com.module.base.dialog

import android.graphics.drawable.Drawable
import com.tencent.smtt.utils.o

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/21.
 */
open class OnSimpleCheckCallback(
  internal var onDenyCheck: ((SimpleCheckDialog) -> Unit?)? = null,
  internal var onGrantCheck: ((SimpleCheckDialog) -> Unit?)? = null,
  internal var denyText: String? = null,
  internal var grantText: String? = null,
  internal var denyDrawable: Drawable? = null,
  internal var grantDrawable: Drawable? = null,
) {
  /**
   * 左侧的取消
   */
  fun onDeny(block: (dialog: SimpleCheckDialog) -> Unit?) {
    this.onDenyCheck = block
  }

  fun onDeny(denyText: String?, block: (dialog: SimpleCheckDialog) -> Unit?) {
    this.onDenyCheck = block
    this.denyText = denyText
  }

  fun onDeny(denyText: String?,
             denyDrawable: Drawable?,
             block: (dialog: SimpleCheckDialog) -> Unit?) {
    this.onDenyCheck = block
    this.denyText = denyText
    this.denyDrawable = denyDrawable
  }

  /**
   * 右侧的确认
   */
  fun onGrant(block: (dialog: SimpleCheckDialog) -> Unit?) {
    this.onGrantCheck = block
  }

  fun onGrant(grantText: String?, block: (dialog: SimpleCheckDialog) -> Unit?) {
    this.onGrantCheck = block
    this.grantText = grantText
  }

  fun onGrant(grantText: String?,
              grantDrawable: Drawable?,
              block: (dialog: SimpleCheckDialog) -> Unit?) {
    this.onGrantCheck = block
    this.grantText = grantText
    this.grantDrawable = grantDrawable
  }
}