package com.example.common_demo.net

import com.example.common_demo.paging.RepoResponse
import com.module.base.BaseResponse
import com.module.base.BaseDataSource
import com.module.base.network.SERVICE_NAME_USER
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/1.
 */
interface TestService {
  @Headers(SERVICE_NAME_USER)
  @GET("conifg/login")
  suspend fun userLogin(@Query("uid") uid: String): BaseResponse<String>

  @GET("search/repositories?sort=stars&q=Android")
  suspend fun searchRepos(@Query("page") page: Int, @Query("per_page") perPage: Int): RepoResponse

}