package com.module.base.dialog

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.animation.Animation
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.common.core.dialog.BaseDialog
import com.module.base.R
import razerdp.util.animation.AlphaConfig
import razerdp.util.animation.AnimationHelper
import razerdp.util.animation.TranslationConfig

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/21.
 */
class SimpleCheckDialog(context: Context?,
                        private val message: String?,
                        private val callbackFun: (OnSimpleCheckCallback.() -> Unit)? = null) :
  BaseDialog(context) {
  init {
    setContentView(R.layout.module_base_dialog_simple_check)
    setBackgroundColor(Color.parseColor("#80000000"))
  }

  override fun onViewCreated(holder: BaseViewHolder) {
    super.onViewCreated(holder)
    holder.setText(R.id.tv_message, message)
    val callback = if(callbackFun == null) {
      null
    } else {
      OnSimpleCheckCallback().apply {
        callbackFun.invoke(this)
      }
    }
    if(callback != null
      && (callback.denyText != null || callback.onDenyCheck != null || callback.grantText != null || callback.onGrantCheck != null)
    ) {
      //callback!=null 或者没设置任何一个时，使用else默认配置
      if(!callback.denyText.isNullOrEmpty()) {
        holder.setText(R.id.tv_deny, callback.denyText)
      }
      if(callback.denyDrawable != null) {
        holder.getView<View>(R.id.tv_deny).background = callback.denyDrawable
      }
      if(callback.onDenyCheck != null) {
        holder.getView<View>(R.id.tv_deny)
          .setOnClickListener {
            callback.onDenyCheck?.invoke(this)
          }
      }
      holder.setGone(R.id.tv_deny, callback.denyText == null && callback.onDenyCheck == null)
      //确认按钮
      if(!callback.grantText.isNullOrEmpty()) {
        holder.setText(R.id.tv_grant, callback.grantText)
      }
      if(callback.grantDrawable != null) {
        holder.getView<View>(R.id.tv_grant).background = callback.grantDrawable
      }
      if(callback.onGrantCheck != null) {
        holder.getView<View>(R.id.tv_grant)
          .setOnClickListener {
            callback.onGrantCheck?.invoke(this)
          }
      }
      holder.setGone(R.id.tv_grant, callback.grantText == null && callback.onGrantCheck == null)

    } else {
      //默认只显示一个确认按钮，点击直接关闭
      holder.setGone(R.id.tv_deny, true)
        .setGone(R.id.tv_grant, false)
        .setText(R.id.tv_grant, "确认")
        .getView<View>(R.id.tv_grant)
        .setOnClickListener {
          dismiss()
        }
    }
  }

  override fun onCreateShowAnimation(): Animation {
    return AnimationHelper.asAnimation()
      .withAlpha(AlphaConfig().from(0.4f).to(1f).duration(150))
      .toShow()
  }

  override fun onCreateDismissAnimation(): Animation {
    return AnimationHelper.asAnimation()
      .withAlpha(AlphaConfig().from(1f).to(0f).duration(150))
      .toDismiss()
  }

}