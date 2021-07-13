package com.example.common_demo

import android.net.Uri
import com.common.core.ui.BaseSchemeActivity

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/12.
 */
class DemoSchemeActivity : BaseSchemeActivity() {
  override fun dispatch(data: Uri): Boolean {
    return false
  }
}