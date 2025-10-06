package dev.miyatech.buzzbee.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.miyatech.buzzbee.model.CommonResponse
import dev.miyatech.buzzbee.netwoork.ApiHelper
import dev.miyatech.buzzbee.netwoork.RetrofitClient
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking


class LearningViewModel() : ViewModel() {

    val mubookings: LiveData<CommonResponse> = MutableLiveData()


    init {

    }


    val apiHelper = ApiHelper(RetrofitClient.apiInterface_LocalKart);

    fun getBooking() {

        mubookings as MutableLiveData

        try {

            var mybookings1 = CommonResponse()


            runBlocking {


                var b1 = async { apiHelper.myBookings("5") }
                var b2 = async { apiHelper.myBookings("5") }
                var b3 = async { apiHelper.myBookings("5") }

//                var b2 = apiHelper.myBookings("5")
//                var b3 = apiHelper.myBookings("5")


                println("my Response 1 " + b1.await())
                println("my Response 2 " + b2.await())
                println("my Response 3 " + b3.await())
                mubookings.value = b1.await()
//                println("my Response 2 " + b2)
//                println("my Response 3 " + b3)


            }

//            var job1 = viewModelScope.launch {
//                mybookings = apiHelper.myBookings("5")
//                println("my Response 1 " + mybookings.message)
//
//
//            }
//            var job2 = viewModelScope.launch {
//                mybookings = apiHelper.myBookings("5")
//                println("my Response 2 " + mybookings.message)
//
//
//            }
//            var job3 = viewModelScope.launch {
//                mybookings = apiHelper.myBookings("5")
//                println("my Response 3 " + mybookings.message)
//                mubookings.value = mybookings
//
//            }
//
//


//            var res = CommonResponse(0, "mess")
//            mubookings.value = res

        } catch (e: Exception) {
            println("Error is " + e)
        }

    }

}


