package com.read.bookclub.ui

import android.media.Image
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.common.core.ui.BaseSplashActivity
import com.common.core.widget.AppTitleBar
import com.module.base.router.RouteAction
import com.read.bookclub.net.AppDataSource
import com.xiaojinzi.component.anno.RouterAnno
import org.w3c.dom.Text

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/8.
 */
@ExperimentalCoilApi
@RouterAnno(hostAndPath = RouteAction.User.PATH_SPLASH)
class SplashActivity : BaseSplashActivity() {

  //用来标识他是一个有状态的数据，类似livedata观察者模式
  var titles = mutableStateOf("1234")
  override fun onSplash(savedInstanceState: Bundle?) {


    setContent {
      Column() {
        AppTitleBar(activity = this@SplashActivity, title = titles.value)
        Image(
          painter = rememberImagePainter("https://img0.baidu.com/it/u=312301072,1324966529&fm=26&fmt=auto&gp=0.jpg"),
          contentDescription = null,
          modifier = Modifier
            .size(128.dp)
            .padding(10.dp)
        )
        Button(onClick = {
          print("点击按钮")
          titles.value = "12"
          AppDataSource(null).enqueue({ getAudioConf() }) {
            onStart {
              Log.e("3333", "onStart")
            }
            onSuccessIO {
              Log.e("3333", "onSuccessIO:" + it.toString())
            }
            onSuccess {
              Log.e("3333", "onSuccess:" + it.toString())
            }
            onApiError {
              Log.e("3333", "onApiError:" + it.errorMessage)
            }
            onError {
              Log.e("3333", "onError:" + it.message)
            }
            onFinally {
              Log.e("3333", "onFinally")
            }

          }
        }) {
          Text(text = "点击")
        }
      }
    }

  }

}
