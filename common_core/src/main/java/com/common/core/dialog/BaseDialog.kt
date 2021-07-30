package com.common.core.dialog

import android.content.Context
import android.graphics.Color
import android.view.View
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import razerdp.basepopup.BasePopupWindow

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/20.
 */
open class BaseDialog(context: Context?) : BasePopupWindow(context) {
  init {
    this.setBackgroundColor(Color.TRANSPARENT)
  }

  lateinit var holder: BaseViewHolder

  override fun onViewCreated(contentView: View) {
    super.onViewCreated(contentView)
    holder = BaseViewHolder(contentView)
    onViewCreated(holder)
  }

 open fun onViewCreated(holder: BaseViewHolder) {

  }
}