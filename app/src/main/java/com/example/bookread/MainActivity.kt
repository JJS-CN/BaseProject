package com.example.bookread

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.common.core.paging.FooterAdapter
import com.common.core.ui.BaseRefreshActivity
import com.example.bookread.databinding.ActivityMainBinding
import com.example.bookread.net.WeatherViewModel
import com.example.bookread.paging.RepoAdapter
import com.library.share.manager.ThirdLoginManager
import com.module.base.router.ARouteAction
import com.module.user.ui.LoginActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

@Route(path = ARouteAction.APP.PATH_HOME)
class MainActivity : BaseRefreshActivity<ActivityMainBinding>() {
  private val weatherViewModel by getViewModel<WeatherViewModel> {
    forecastsBeanLiveData.observe(this@MainActivity, {
      Log.e("TAG", "onsuccess3333: ")
      ToastUtils.showShort("结果：" + it)
    })
  }
  val repoAdapter = RepoAdapter()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    /*LiveEventBus.get<String>(EventsConstants.TOKEN_REFRESH)
      .observe(this, {

      })*/
  }

  override fun initView() {
    super.initView()
    setTitle("个人中心")
    binding.tvName.setOnClickListener {
      val intent = Intent(ActivityUtils.getTopActivity(), LoginActivity::class.java);
      intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
      ActivityUtils.getTopActivity().startActivity(intent)
    }
    binding.rv.layoutManager = LinearLayoutManager(this)

    binding.rv.adapter = repoAdapter.withLoadStateFooter(FooterAdapter
    { repoAdapter.retry() })

    lifecycleScope.launch {
      weatherViewModel.getPagingData()
        .collect { pagingData -> repoAdapter.submitData(pagingData) }
    }
    /*repoAdapter.addLoadStateListener {
      when(it.refresh) {
        is LoadState.NotLoading -> {
          binding.rv.visibility = View.VISIBLE
        }
        is LoadState.Loading -> {
          binding.rv.visibility = View.INVISIBLE
        }
        is LoadState.Error -> {
          val state = it.refresh as LoadState.Error
          Toast.makeText(this, "Load Error: ${state.error.message}", Toast.LENGTH_SHORT).show()
        }
      }
    }*/

  }

  override fun onResume() {
    super.onResume()
    //BuglyManager.init(application, "", true)
  }

  override fun initData() {
    super.initData()
    weatherViewModel.getWeather("")
    repoAdapter.refresh()
  }

  override fun refreshData() {
    //在这里请求网络，通过网络的dismissLoading触发取消
    initData()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    ThirdLoginManager.onActivityResult(this, requestCode, resultCode, data)
  }


  @Synchronized
  private fun log(msg: String) {
    val newLog = "[${Thread.currentThread().name}]-${msg}"
    Log.e("TAG", newLog)
  }

}