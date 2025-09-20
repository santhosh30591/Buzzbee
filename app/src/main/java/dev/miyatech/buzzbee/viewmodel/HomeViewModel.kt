package dev.miyatech.buzzbee.viewmodel

import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.miyatech.buzzbee.model.BusinessDetailsModel
import dev.miyatech.buzzbee.model.BusinessListModel
import dev.miyatech.buzzbee.model.DiscoverResult
import dev.miyatech.buzzbee.model.SubCategoryModel
import dev.miyatech.buzzbee.model.VideoListModel
import dev.miyatech.buzzbee.model.VideoResultModel
import dev.miyatech.buzzbee.netwoork.Api
import dev.miyatech.buzzbee.netwoork.ApiHelper
import dev.miyatech.buzzbee.netwoork.NetworkResult
import dev.miyatech.buzzbee.netwoork.RetrofitClient
import dev.miyatech.buzzbee.netwoork.isInternetAvailable
import kotlinx.coroutines.launch


class HomeViewModel() : ViewModel() {

    val _discount: MutableLiveData<NetworkResult<ArrayList<DiscoverResult>>> = MutableLiveData()
    val discountState: LiveData<NetworkResult<ArrayList<DiscoverResult>>> = _discount
    var discountStateAll: ArrayList<DiscoverResult> = arrayListOf()


    val _video: MutableLiveData<NetworkResult<VideoResultModel>> = MutableLiveData()
    val video: MutableLiveData<NetworkResult<VideoResultModel>> = _video
    var videoList: ArrayList<VideoListModel> = arrayListOf()
    val apiHelper = ApiHelper(RetrofitClient.api_interface);


    val _latelong: MutableLiveData<Location> = MutableLiveData()
    val latelong: LiveData<Location> = _latelong

    fun home(context: Context) {
        viewModelScope.launch {
            _discount.value = NetworkResult.Loading
            if (isInternetAvailable(context)) {
                try {
                    val response = apiHelper.discover()
                    _discount.value = NetworkResult.Success(data = response.results)
                    discountStateAll = response.results
                } catch (e: Exception) {
                    _discount.value = NetworkResult.Error(Api.NetConnectionFailed)
                }
            } else {
                _discount.value = NetworkResult.Error(Api.notInterNetConnection)
            }
        }
    }


    fun loadingVideos(context: Context) {
        viewModelScope.launch {
            _video.value = NetworkResult.Loading
            if (isInternetAvailable(context)) {
                try {
                    val response = apiHelper.getVideos()
                    _video.value = NetworkResult.Success(data = response)
                    videoList = response.results!!
                } catch (e: Exception) {
                    _video.value = NetworkResult.Error(Api.NetConnectionFailed)
                }
            } else {
                _video.value = NetworkResult.Error(Api.notInterNetConnection)
            }
        }
    }


    fun discoverLoading(context: Context) {
        viewModelScope.launch {
            _discount.value = NetworkResult.Loading
            if (isInternetAvailable(context)) {
                try {
                    val response = apiHelper.discover()
                    _discount.value = NetworkResult.Success(data = response.results)
                    discountStateAll = response.results
                } catch (e: Exception) {
                    _discount.value = NetworkResult.Error(Api.NetConnectionFailed)
                }
            } else {
                _discount.value = NetworkResult.Error(Api.notInterNetConnection)
            }
        }
    }


    val _subCategory: MutableLiveData<NetworkResult<SubCategoryModel>> = MutableLiveData()
    val subCategory: LiveData<NetworkResult<SubCategoryModel>> = _subCategory
    fun subCategoryApi(context: Context, id: String) {
        viewModelScope.launch {
            _subCategory.value = NetworkResult.Loading
            if (isInternetAvailable(context)) {
                try {
                    val response = apiHelper.subCategory(id)


                    _subCategory.value = NetworkResult.Success(data = response)

                } catch (e: Exception) {
                    _subCategory.value = NetworkResult.Error(Api.NetConnectionFailed)
                }
            } else {
                _subCategory.value = NetworkResult.Error(Api.notInterNetConnection)
            }
        }
    }

    val _bisinessList: MutableLiveData<NetworkResult<BusinessListModel>> = MutableLiveData()
    val bisinessList: LiveData<NetworkResult<BusinessListModel>> = _bisinessList
    fun businessListApi(context: Context, id: String) {
        viewModelScope.launch {
            _bisinessList.value = NetworkResult.Loading
            if (isInternetAvailable(context)) {
                try {
                    val response = apiHelper.bussinessList(id)


                    _bisinessList.value = NetworkResult.Success(data = response)

                } catch (e: Exception) {
                    _bisinessList.value = NetworkResult.Error(Api.NetConnectionFailed)
                }
            } else {
                _bisinessList.value = NetworkResult.Error(Api.notInterNetConnection)
            }
        }
    }

    val _details: MutableLiveData<NetworkResult<BusinessDetailsModel>> = MutableLiveData()
    val details: LiveData<NetworkResult<BusinessDetailsModel>> = _details
    fun businessDetailsApi(context: Context, id: String) {
        viewModelScope.launch {
            _details.value = NetworkResult.Loading
            if (isInternetAvailable(context)) {
                try {
                    val response = apiHelper.bussinessDetails(id)
                    _details.value = NetworkResult.Success(data = response)
                } catch (e: Exception) {
                    _details.value = NetworkResult.Error(Api.NetConnectionFailed)
                }
            } else {
                _details.value = NetworkResult.Error(Api.notInterNetConnection)
            }
        }
    }

}

