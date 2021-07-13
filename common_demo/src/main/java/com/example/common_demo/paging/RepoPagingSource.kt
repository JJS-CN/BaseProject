package com.example.common_demo.paging

import androidx.paging.*
import com.example.common_demo.net.TestService
import kotlinx.coroutines.flow.Flow

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/5.
 */
//初始化页数字段类型和分页具体实体类；
class RepoPagingSource(private val gitHubService: TestService) :
  PagingSource<Int, RepoResponse.Repo>() {

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepoResponse.Repo> {
    return try {
      val page = params.key ?: 1 // set page 1 as default
      val pageSize = params.loadSize
      val repoResponse = gitHubService.searchRepos(page, pageSize)
      val repoItems = repoResponse.items
      val prevKey = if(page > 1) page - 1 else null
      val nextKey = if(repoItems.isNotEmpty()) page + 1 else null
      LoadResult.Page(repoItems, prevKey, nextKey)
    } catch(e: Exception) {
      LoadResult.Error(e)
    }
  }

  override fun getRefreshKey(state: PagingState<Int, RepoResponse.Repo>): Int? = null

  fun getPager(): Pager<Int, RepoResponse.Repo> {
    return Pager(
      config = PagingConfig(10),
      pagingSourceFactory = { this }
    )
  }

}