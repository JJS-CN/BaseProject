package com.example.common_demo.paging

import com.common.core.paging.BasePagingSource
import com.example.common_demo.net.TestService

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