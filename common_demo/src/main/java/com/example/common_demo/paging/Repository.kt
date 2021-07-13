package com.example.common_demo.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.common_demo.net.DemoDataSource
import kotlinx.coroutines.flow.Flow

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/5.
 */
object Repository {
  private const val PAGE_SIZE = 10

  private val remoteDataSource by lazy {
    DemoDataSource(null)
  }

  fun getPagingData(): Flow<PagingData<RepoResponse.Repo>> {
    return RepoPagingSource(remoteDataSource.getApiService()).getPager().flow
  }

  fun getPagingData2(): Flow<PagingData<RepoResponse.Repo>> {
    return Repo2PagingSource(remoteDataSource.getApiService()).getPager()
  }
}