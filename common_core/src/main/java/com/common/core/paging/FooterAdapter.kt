package com.common.core.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.common.core.R

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/2.
 */

// 这部分只是创建一个简单的loadmoreadapter，并且传入一个重试的回调
class FooterAdapter(val retryCallback: () -> Unit) :
  LoadStateAdapter<FooterAdapter.PagingFootViewHolder>() {
  var isFirst: Boolean = true
  override fun onCreateViewHolder(parent: ViewGroup,
                                  loadState: LoadState): PagingFootViewHolder {
    val view =
      LayoutInflater.from(parent.context).inflate(R.layout.common_core_load_more, parent, false)
    val holder = PagingFootViewHolder(view)
    holder.llRetry.setOnClickListener {
      retryCallback()
    }
    return holder
  }

  override fun onBindViewHolder(holderPagingFoot: PagingFootViewHolder, loadState: LoadState) {
    holderPagingFoot.llLoading.isVisible = loadState is LoadState.Loading
    holderPagingFoot.llRetry.isVisible = loadState is LoadState.Error
    holderPagingFoot.llNothing.isVisible = loadState is LoadState.NotLoading
  }

  override fun displayLoadStateAsItem(loadState: LoadState): Boolean {
    //return super.displayLoadStateAsItem(loadState)
    val display =
      loadState is LoadState.Loading || loadState is LoadState.Error || !isFirst && loadState is LoadState.NotLoading
    isFirst = false;
    return display
  }

  class PagingFootViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val llLoading: ViewGroup = itemView.findViewById(R.id.ll_loading)
    val llRetry: ViewGroup = itemView.findViewById(R.id.ll_retry)
    val llNothing: ViewGroup = itemView.findViewById(R.id.ll_nothing)
  }
}
