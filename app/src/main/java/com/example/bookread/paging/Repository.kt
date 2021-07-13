package com.example.bookread.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.bookread.net.AppDataSource
import kotlinx.coroutines.flow.Flow

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/5.
 */
object Repository {
  private const val PAGE_SIZE = 10

  private val remoteDataSource by lazy {
    AppDataSource(null)
  }

  fun getPagingData(): Flow<PagingData<RepoResponse.Repo>> {
    return RepoPagingSource(remoteDataSource.getApiService()).getPager().flow
  }

  fun getPagingData2(): Flow<PagingData<RepoResponse.Repo>> {
    return Repo2PagingSource(remoteDataSource.getApiService()).getPager()
  }
}