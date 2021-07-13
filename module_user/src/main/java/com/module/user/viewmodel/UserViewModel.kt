package com.module.user.viewmodel

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.RegexUtils
import com.common.core.network.base.BaseViewModel
import com.module.base.UserData
import com.module.base.manager.YtUserManager
import com.module.user.R
import com.module.user.enum.LOGIN_TYPE
import com.module.user.net.UserDataSource

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/7.
 */
class UserViewModel : BaseViewModel() {
  val userDataSource by lazy {
    UserDataSource(this)
  }
  val hasRegisterData = MutableLiveData<Boolean>()
  val loginData = MutableLiveData<UserData>()
  val getCaptchaData = MutableLiveData<String>()

  fun hasRegister(phone: String) {
    if(!RegexUtils.isMobileSimple(phone)) {
      showToast(R.string.module_user_toast_error_phone_format)
      return
    }
    userDataSource.enqueueLoading({
      hasRegister(1, phone)
    }) {
      onSuccess {
        hasRegisterData.value = it
      }
    }
  }

  fun getCaptcha(type: Int, phone: String) {
    if(!RegexUtils.isMobileSimple(phone)) {
      showToast(R.string.module_user_toast_error_phone_format)
      return
    }
    userDataSource.enqueueLoading({
      getCaptcha(type, phone)
    }) {
      onSuccess {
        getCaptchaData.value = it
      }
    }
  }

  fun register(phone: String, pwd: String, captcha: String) {
    if(!RegexUtils.isMobileSimple(phone)) {
      showToast(R.string.module_user_toast_error_phone_format)
      return
    }
    if(pwd.length < 6) {
      showToast(R.string.module_user_toast_error_pwd_less6)
      return
    }
    if(captcha.isEmpty()) {
      showToast(R.string.module_user_toast_error_captcha_empty)
      return
    }
    login(LOGIN_TYPE.register, phone, pwd, captcha)
  }

  fun loginPwd(phone: String, pwd: String) {
    if(!RegexUtils.isMobileSimple(phone)) {
      showToast(R.string.module_user_toast_error_phone_format)
      return
    } else if(pwd.length < 6) {
      showToast(R.string.module_user_toast_error_pwd_less6)
      return
    }
    login(LOGIN_TYPE.mp, phone, pwd, null)
  }

  fun loginCaptcha(phone: String, captcha: String) {
    if(!RegexUtils.isMobileSimple(phone)) {
      showToast(R.string.module_user_toast_error_phone_format)
      return
    } else if(captcha.isEmpty()) {
      showToast(R.string.module_user_toast_error_captcha_empty)
      return
    }
    login(LOGIN_TYPE.mp, phone, null, captcha)
  }

  private fun login(loginType: LOGIN_TYPE, phone: String, pwd: String?, captcha: String?) {
    userDataSource.enqueueLoading({
      login(loginType.name, phone, pwd, captcha)
    }) {
      onSuccess {
        loginData.value = it
        YtUserManager.putUserBody(it)
      }
    }
  }
}