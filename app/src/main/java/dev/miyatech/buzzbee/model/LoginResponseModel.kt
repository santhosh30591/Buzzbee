package dev.miyatech.buzzbee.model

import com.google.gson.annotations.SerializedName

open class LoginResult(

    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("mobile") var mobile: String? = null,
    @SerializedName("password") var password: String? = null,
    @SerializedName("stateid") var stateid: String? = null,
    @SerializedName("districtid") var districtid: String? = null,
    @SerializedName("cityid") var cityid: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("device_id") var deviceId: String? = null,
    @SerializedName("business_id") var businessId: String? = null,
    @SerializedName("created_date") var createdDate: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("statename") var statename: String? = null,
    @SerializedName("districtname") var districtname: String? = null,
    @SerializedName("cityname") var cityname: String? = null,
    @SerializedName("imageUrl") var imageUrl: String? = null
)

open class LoginResponseModel(var results: LoginResult = LoginResult()) :
    CommonResponse()