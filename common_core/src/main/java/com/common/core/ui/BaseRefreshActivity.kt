package com.common.core.ui

import android.util.Log
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding

/**
 *  Class: 单独提供刷新功能
 *  Other:
 *  Create by jsji on  2021/7/2.
 */
abstract class BaseRefreshActivity : BaseActivity() {
   /* protected var swipeRefresh: SwipeRefreshLayout? = null

    override fun getRootView(): View {
        swipeRefresh = SwipeRefreshLayout(this)
        swipeRefresh!!.setOnRefreshListener {
            refreshData()
        }
        swipeRefresh!!.addView(super.getRootView())
        return swipeRefresh as SwipeRefreshLayout
    }

    abstract fun refreshData()

    override fun dismissLoading() {
        super.dismissLoading()
        Log.e("TAG", "onsuccess2222:6666 ")
        swipeRefresh?.isRefreshing = false
    }*/

}