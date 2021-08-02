package com.common.core.paging

import android.renderscript.Sampler
import androidx.paging.*
import kotlinx.coroutines.flow.Flow

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/5.
 */
abstract class BasePagingSource<Value : Any> : PagingSource<Int, Value>() {
  override fun getRefreshKey(state: PagingState<Int, Value>): Int? {
    return null
  }

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Value> {
    return try {
      val page = params.key ?: 1 // set page 1 as default
      val pageSize = params.loadSize
      val repoItems = loadCall(page, pageSize)
      val prevKey = if(page > 1) page - 1 else null
      val nextKey = if(repoItems.isNotEmpty()) page + 1 else null
      LoadResult.Page(repoItems, prevKey, nextKey)
    } catch(e: Exception) {
      LoadResult.Error(e)
    }
  }

  abstract suspend fun loadCall(page: Int, size: Int): List<Value>
}
