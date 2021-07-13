package com.example.bookread.net

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.common.core.network.base.BaseViewModel
import com.example.bookread.paging.RepoResponse
import com.example.bookread.paging.Repository
import kotlinx.coroutines.flow.Flow

/**
 *  Class:
 *  Other:
 *  Create by jsji on  2021/7/1.
 */
class WeatherViewModel : BaseViewModel() {

  private val remoteDataSource by lazy {
    AppDataSource(this)
  }

  val forecastsBeanLiveData = MutableLiveData<String>()

  fun getWeather(city: String) {
    remoteDataSource.enqueueLoading({
      userLogin(city)
    }) {
      onSuccess {
        if(!it.isNullOrEmpty()) {
          forecastsBeanLiveData.value = it
        }
      }
    }
  }

  fun getPagingData(): Flow<PagingData<RepoResponse.Repo>> {
    return Repository.getPagingData2().cachedIn(viewModelScope)
  }

}
