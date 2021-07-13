package com.module.user.ui

import com.alibaba.android.arouter.facade.annotation.Route
import com.common.core.network.exception.BaseHttpException
import com.common.core.ui.BaseActivity
import com.module.base.BaseDataSource
import com.module.base.network.TokenService
import com.module.base.router.ARouteAction
import com.module.user.databinding.ModuleUserActivityLoginBinding
import com.module.user.viewmodel.UserViewModel

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/7.
 */
@Route(path = ARouteAction.User.PATH_LOGIN)
class LoginActivity : BaseActivity<ModuleUserActivityLoginBinding>() {
  private val userViewModel by getViewModel<UserViewModel> {
    hasRegisterData.observe(this@LoginActivity, {
      isRegistered = it
      su(it.toString())
    })
    getCaptchaData.observe(this@LoginActivity, {
      su(it)
    })
    loginData.observe(this@LoginActivity, {
      su(it.toString())
    })
  }
  var isRegistered = false

  override fun initView() {
    super.initView()
    setTitle("登录测试")
    binding.btn1.setOnClickListener {
      val phone = binding.tvPhone.text.toString()
      userViewModel.hasRegister(phone)
    }
    binding.btn2.setOnClickListener {
      if(!isRegistered) {
        val phone = binding.tvPhone.text.toString()
        userViewModel.getCaptcha(1, phone)
      } else {
        showToast("你已注册")
      }
    }
    binding.btn3.setOnClickListener {
      val phone = binding.tvPhone.text.toString()
      val pwd = binding.tvPsw.text.toString()
      val ca = binding.tvCode.text.toString()
      if(!isRegistered) {
        userViewModel.register(phone, pwd, ca)
      } else {
        userViewModel.loginPwd(phone, pwd)
        //RouteAction.APP.goHome()
      }
    }
    binding.btn4.setOnClickListener {
      BaseDataSource<TokenService>(null, TokenService::class.java)
        .enqueue({ refreshToken(com.module.base.manager.YtUserManager.getUserBody()?.refreshToken) })
    }
  }

  fun su(it: String?) {
    binding.tvMessage.text = binding.tvMessage.text.toString() + "\n result：" + it
  }

  fun er(it: BaseHttpException) {
    binding.tvMessage.text = binding.tvMessage.text.toString() + "\n error：" + it.errorMessage
  }
}