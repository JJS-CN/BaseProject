package com.example.common_demo

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.common.core.paging.FooterAdapter
import com.common.core.ui.BaseRefreshActivity
import com.example.common_demo.databinding.ActivityCommonDemoBinding
import com.example.common_demo.net.WeatherViewModel
import com.example.common_demo.paging.RepoAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : BaseRefreshActivity<ActivityCommonDemoBinding>() {
  private val weatherViewModel by getViewModel<WeatherViewModel> {
    forecastsBeanLiveData.observe(this@MainActivity, {
      Log.e("TAG", "onsuccess3333: ")
      ToastUtils.showShort("结果：" + it)
    })
  }
  val repoAdapter = RepoAdapter()

  override fun initView() {
    super.initView()
    setTitle("个人中心")
    binding.tvName.setOnClickListener {
      val uri = Uri.parse("fungox://openUrlInApp?url=http://www.baidu.com")
      val viewIntent = Intent(Intent.ACTION_VIEW, uri)
      viewIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
      startActivity(viewIntent)
    }
    lifecycleScope.launch {
      weatherViewModel.getPagingData()
        .collect { pagingData -> repoAdapter.submitData(pagingData) }
    }
    binding.rv.layoutManager = LinearLayoutManager(this)
    binding.rv.adapter = repoAdapter.withLoadStateFooter(FooterAdapter { repoAdapter.retry() })


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

  }


  @Synchronized
  private fun log(msg: String) {
    val newLog = "[${Thread.currentThread().name}]-${msg}"
    Log.e("TAG", newLog)
  }

  override fun refreshData() {
    Log.e("TAG", "onsuccess2222:4444 ")
    repoAdapter.refresh()
    dismissLoading()
  }


}