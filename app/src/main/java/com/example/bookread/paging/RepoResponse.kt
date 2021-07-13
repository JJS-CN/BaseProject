package com.example.bookread.paging

import androidx.recyclerview.widget.ConcatAdapter
import com.module.base.BaseResponse

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/1.
 */
class RepoResponse {

  val items: List<Repo> = emptyList()

  data class Repo(
    val id: Int,
    val name: String,
    val description: String?,
    val starCount: Int
  )

}