package dev.miyatech.buzzbee.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.gson.Gson
import dev.miyatech.buzzbee.MainActivity
import dev.miyatech.buzzbee.Screen
import dev.miyatech.buzzbee.model.CommonResponse
import dev.miyatech.buzzbee.model.LoginResponseModel
import dev.miyatech.buzzbee.model.StateList
import dev.miyatech.buzzbee.model.StateListModel
import dev.miyatech.buzzbee.netwoork.Api
import dev.miyatech.buzzbee.netwoork.ApiHelper
import dev.miyatech.buzzbee.netwoork.NetworkResult
import dev.miyatech.buzzbee.netwoork.RetrofitClient
import dev.miyatech.buzzbee.netwoork.isInternetAvailable
import dev.miyatech.buzzbee.ui_components.DBHelper
import kotlinx.coroutines.launch

class ViewModelLogin : ViewModel() {


    var isMobileError = false
    var isPasswordError = false


    fun checkingMobile(mobile: String) {


        isMobileError = !(mobile.length > 6 || mobile.isEmpty())
//        isMobileError = isValidIndianMobileNumber(mobile)
    }

    fun isValidIndianMobileNumber(phoneNumber: String): Boolean {

        var list = arrayOf("6", "7", "8", "9")
        if (phoneNumber.isNotEmpty()) {
            return list.contains(phoneNumber.substring(0, 1))
        } else {
            return list.contains(phoneNumber)
        }

    }

    fun checkingPassword(password: String) {
        isPasswordError = !(password.length > 3 || password.isEmpty())
    }

    fun stateValidaction(name: String): Boolean {

        return name.isEmpty()

    }

    fun submitLoginValidation(

        mobile: String, password: String
    ): Boolean {
        return mobile.length > 6 && password.length > 3
    }


    private val _login: MutableLiveData<NetworkResult<LoginResponseModel>> = MutableLiveData()
    val login: MutableLiveData<NetworkResult<LoginResponseModel>> = _login


    private val apiHelper = ApiHelper(RetrofitClient.api_interface)


    fun submit(
        mobile: String, password: String, context: Context, navController: NavHostController
    ) {
        if (isInternetAvailable(context)) {
            viewModelScope.launch {
                _login.value = NetworkResult.Loading
                try {
                    val response = apiHelper.postLogin(mobile, password)
                    if (response.errorCode == 0) {
                        _login.value = NetworkResult.Success(data = response)
                        val jsonString = Gson().toJson(response)
                        DBHelper(context as MainActivity).saveLogin(
                            logins = jsonString.toString()
                        )
                        navController.navigate(Screen.HomeMain.route) {
                            popUpTo(Screen.Login.route) {
                                inclusive = true
                            } // Clear login from back stack
                        }
                    } else {
                        _login.value = NetworkResult.Error(
                            response.message
                        )
                    }

                } catch (e: Exception) {
                    _login.value = NetworkResult.Error(Api.NetConnectionFailed)
                }
            }
        } else {
            _login.value = NetworkResult.Error(Api.notInterNetConnection)
        }

    }


    fun submitValidation(
        name: String,
        states: String,
        district: String,
        city: String,
        mobile: String,
        password: String,
    ): Boolean {
        return name.length > 2 && states.length > 2 && district.length > 2 && city.length > 2 && mobile.length > 6 && password.length > 3


    }


    val _stateList: MutableLiveData<NetworkResult<StateListModel>> = MutableLiveData()
    val stateList: LiveData<NetworkResult<StateListModel>> = _stateList
    val stateListData: ArrayList<StateList> = arrayListOf()
    fun getStateList(mcontext: Context) {
        viewModelScope.launch {
            _stateList.value = NetworkResult.Loading
            if (isInternetAvailable(mcontext)) {
                try {
                    val response = apiHelper.stateList()
                    stateListData.addAll(response.results)
                    _stateList.value = NetworkResult.Success(data = response)

                } catch (e: Exception) {
                    cityList.value = NetworkResult.Error(Api.NetConnectionFailed)
                }
            } else {
                _stateList.value = NetworkResult.Error(Api.notInterNetConnection)
            }
        }
    }

    val districtList: MutableLiveData<NetworkResult<StateListModel>> = MutableLiveData()

    fun districtList(id: String, mcontext: Context) {

        viewModelScope.launch {
            districtList.value = NetworkResult.Loading
            if (isInternetAvailable(mcontext)) {
                try {
                    val response = apiHelper.districtList(id)
                    districtList.value = NetworkResult.Success(data = response)
                } catch (e: Exception) {
                    cityList.value = NetworkResult.Error(Api.NetConnectionFailed)
                }
            } else {
                districtList.value = NetworkResult.Error(Api.notInterNetConnection)
            }
        }
    }


    val cityList: MutableLiveData<NetworkResult<StateListModel>> = MutableLiveData()
    fun cityList(id: String, mcontext: Context) {

        viewModelScope.launch {
            cityList.value = NetworkResult.Loading
            if (isInternetAvailable(mcontext)) {
                try {
                    val response = apiHelper.cityList(id)
                    cityList.value = NetworkResult.Success(data = response)
                } catch (e: Exception) {
                    cityList.value = NetworkResult.Error(Api.NetConnectionFailed)
                }
            } else {
                cityList.value = NetworkResult.Error(Api.notInterNetConnection)
            }
        }
    }

    val register: MutableLiveData<NetworkResult<CommonResponse>> = MutableLiveData()


    fun registerApi(
        name: String,
        states: String,
        district: String,
        city: String,
        mobile: String,
        password: String,
        deviceid: String,
        context: Context
    ) {
        viewModelScope.launch {
            register.value = NetworkResult.Loading
            if (isInternetAvailable(context)) {
                try {
                    val response = apiHelper.registerApi(
                        name, states, district, city, mobile, password, deviceid
                    )
                    register.value = NetworkResult.Success(data = response)
                } catch (e: Exception) {

                    println("Error is  $e ")
                    register.value = NetworkResult.Error(Api.NetConnectionFailed)
                }
            } else {
                register.value = NetworkResult.Error(Api.notInterNetConnection)
            }
        }
    }

}


