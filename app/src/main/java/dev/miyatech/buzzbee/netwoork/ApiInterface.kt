package dev.miyatech.buzzbee.netwoork

import dev.miyatech.buzzbee.model.BusinessDetailsModel
import dev.miyatech.buzzbee.model.BusinessListModel
import dev.miyatech.buzzbee.model.CommonResponse
import dev.miyatech.buzzbee.model.DiscoverModel
import dev.miyatech.buzzbee.model.LoginResponseModel
import dev.miyatech.buzzbee.model.StateListModel
import dev.miyatech.buzzbee.model.SubCategoryModel
import dev.miyatech.buzzbee.model.VideoResultModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {


    @FormUrlEncoded
    @POST(Api.login)
    fun login(
        @Field("mobile") mobile: String, @Field("password") password: String
    ): Call<LoginResponseModel>

    @FormUrlEncoded
    @POST(Api.login)
    suspend fun postLogin(
        @Field("mobile") mobile: String, @Field("password") password: String
    ): LoginResponseModel


    @GET(Api.statelist)
    suspend fun stateList(): StateListModel

    @FormUrlEncoded
    @POST(Api.district)
    suspend fun districtList(
        @Field("stateid") id: String,
    ): StateListModel

    @FormUrlEncoded
    @POST(Api.cityList)
    suspend fun cityList(
        @Field("districtid") id: String,
    ): StateListModel

    @FormUrlEncoded
    @POST(Api.register)
    suspend fun register(
        @Field("name") name: String,
        @Field("stateid") satateid: String,
        @Field("districtid") districtid: String,
        @Field("cityid") cityid: String,
        @Field("mobile") mobile: String,
        @Field("password") password: String,
        @Field("deviceid") deviceid: String,
    ): CommonResponse

    @GET(Api.videoList)
    suspend fun getVideos(): VideoResultModel

    @GET(Api.categorylist)
    suspend fun getDiscover(): DiscoverModel

    @GET(Api.subcategory)
    suspend fun getCategory(@Query("categoryid") id: String): SubCategoryModel

    @FormUrlEncoded
    @POST(Api.businesslist)
    suspend fun businessList(
        @Field("subcategoryid") id: String,
    ): BusinessListModel

    @FormUrlEncoded
    @POST(Api.businessDetails)
    suspend fun businessDetails(
        @Field("id") id: String,
    ): BusinessDetailsModel

}


