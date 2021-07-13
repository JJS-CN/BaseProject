package com.example.bookread.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.common.core.paging.BasePagingSource
import com.example.bookread.net.TestService

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/5.
 */
class Repo2PagingSource(private val gitHubService: TestService) :
  BasePagingSource<RepoResponse.Repo>() {
  override suspend fun loadCall(page: Int, size: Int): List<RepoResponse.Repo> {
    return gitHubService.searchRepos(page, size).items
  }
}