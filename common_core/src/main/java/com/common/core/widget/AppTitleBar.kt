package com.common.core.widget

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.common.core.R

/**
 *  Class:
 *  Other: 通用标题栏
 *  Create by jsji on  2021/7/30.
 */
@Composable
fun AppTitleBar(
  activity: Activity,
  title: String,
  needBottomLine: Boolean? = true,
  @DrawableRes actionIcon: Int? = null,
  onActionBack: (() -> Unit)? = {},
  onClickBack: (() -> Unit)? = null) {
  Column(Modifier.height(30.dp)) {
    TopAppBar(title = {
      Text(
        text = title,
        fontFamily = FontFamily.Default,
        fontSize = 18.sp,
        color = Color.Black
      )
    },
      navigationIcon = {
        IconButton(onClick = {
          if(onClickBack == null) {
            activity.onBackPressed()
          } else {
            onClickBack.invoke()
          }
        }) {
          Icon(
            painter = painterResource(id = R.mipmap.common_base_titlebar_back),
            contentDescription = "back"
          )
        }
      }, actions = {
        if(actionIcon != null && onActionBack != null) {
          IconButton(onClick = onActionBack) {
            Icon(
              painter = painterResource(id = actionIcon),
              contentDescription = "back"
            )
          }
        }
      })
    if(needBottomLine == true) {
      Spacer(
        modifier = Modifier
          .height(0.5.dp)
          .background(color = Color.LightGray))
    }
  }
}

