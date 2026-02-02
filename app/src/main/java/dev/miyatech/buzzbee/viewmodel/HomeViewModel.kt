package dev.miyatech.buzzbee.viewmodel

import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.miyatech.buzzbee.model.BusinessDetailsModel
import dev.miyatech.buzzbee.model.BusinessListModel
import dev.miyatech.buzzbee.model.DashboardLiveResult
import dev.miyatech.buzzbee.model.DashboardModel
import dev.miyatech.buzzbee.model.DiscoverResult
import dev.miyatech.buzzbee.model.NotificationDetails
import dev.miyatech.buzzbee.model.NotificationListModel
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
    val dashboard: LiveData<NetworkResult<DashboardModel>> = MutableLiveData()


    val dashboardLive: LiveData<DashboardLiveResult> = MutableLiveData()

    val notificationList: MutableLiveData<NetworkResult<NotificationListModel>> = MutableLiveData()
//    private val notificationDetails = MutableStateFlow<NetworkResult<NotificationDetails>?>(null)
    val notificationDetails: LiveData<NetworkResult<NotificationDetails>> = MutableLiveData()//    val notificationDetails1: StateFlow<NetworkResult<NotificationDetails>> =notificationDetails

//    val notificationDetails: LiveData<NetworkResult<NotificationDetails>?> get() = notificationDetails

    val discountState: LiveData<NetworkResult<ArrayList<DiscoverResult>>> = _discount
    var discountStateAll: ArrayList<DiscoverResult> = arrayListOf()

    val notificationCount: MutableLiveData<String> = MutableLiveData("0")

    val _video: MutableLiveData<NetworkResult<VideoResultModel>> = MutableLiveData()
    val video: MutableLiveData<NetworkResult<VideoResultModel>> = _video
    var videoList: ArrayList<VideoListModel> = arrayListOf()
    val apiHelper = ApiHelper(RetrofitClient.api_interface);


    val _latelong: MutableLiveData<Location> = MutableLiveData()
    val latelong: LiveData<Location> = _latelong


    fun home(context: Context, id: String) {
        dashboard as MutableLiveData<NetworkResult<DashboardModel>>
        viewModelScope.launch {
            if (notificationCount.value!!.length == 0) {
                dashboard.value = NetworkResult.Loading
            }

            if (isInternetAvailable(context)) {
                try {
                    val response = apiHelper.dashboard(id)
                    notificationCount.value = response.results.notification_count
                    dashboard.value = NetworkResult.Success(data = response)

                } catch (e: Exception) {
                    dashboard.value = NetworkResult.Error(Api.NetConnectionFailed)
                }
            } else {
                dashboard.value = NetworkResult.Error(Api.notInterNetConnection)
            }
        }
    }


    fun homeLiveData(context: Context, id: String) {
        dashboardLive as MutableLiveData<DashboardLiveResult>
        viewModelScope.launch {

            if (isInternetAvailable(context)) {
                try {

//                    while (true) {
                        val response = apiHelper.dashboardLive(id)
                        notificationCount.value = response.results.notification_count
                        dashboardLive.value = response.results
//                        delay(4000)
//                    }


                } catch (e: Exception) {
                    dashboardLive.value = DashboardLiveResult()
                }
            }
        }
    }

    fun getNotificatiopnList1(context: Context, userid: String) {
        notificationList as MutableLiveData<NetworkResult<NotificationListModel>>
        viewModelScope.launch {

            notificationList.value = NetworkResult.Loading

            if (isInternetAvailable(context)) {
                try {
                    val response = apiHelper.notificationList(userid)
                    notificationList.value = NetworkResult.Success(data = response)

                } catch (e: Exception) {
                    notificationList.value = NetworkResult.Error(Api.NetConnectionFailed)
                }
            } else {
                notificationList.value = NetworkResult.Error(Api.notInterNetConnection)
            }
        }
    }

    fun getNotificatiopnDetails(context: Context, id: String) {
        notificationDetails as MutableLiveData<NetworkResult<NotificationDetails>>
        viewModelScope.launch {

            notificationDetails.value = NetworkResult.Loading

            if (isInternetAvailable(context)) {
                try {
                    val response = apiHelper.notificationDetails(id)
                    notificationDetails.value = NetworkResult.Success(data = response)

                } catch (e: Exception) {
                    notificationDetails.value = NetworkResult.Error(Api.NetConnectionFailed)
                }
            } else {
                notificationDetails.value = NetworkResult.Error(Api.notInterNetConnection)
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

