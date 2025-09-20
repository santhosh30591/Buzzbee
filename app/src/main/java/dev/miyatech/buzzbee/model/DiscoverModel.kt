package dev.miyatech.buzzbee.model

import com.google.gson.annotations.SerializedName

open class DiscoverResult(var id: String = "", var category: String = "", var imageUrl: String = "")

open class DiscoverModel(var results: ArrayList<DiscoverResult> = ArrayList()) :
    CommonResponse()


open class SubCategoryModel(var results: ArrayList<SubCategoryResult> = ArrayList()) :
    CommonResponse()

open class SubCategoryResult(
    var id: String = "",
    var subcategory: String = "",
    var imageUrl: String = "",
    var categoryid: String = ""
)


open class BusinessListModel(var result: ArrayList<BusinessResult> = ArrayList()) :
    CommonResponse()

open class BusinessResult(
    var id: String = "",
    var names: String = "",
    var img: String = "",
    var email: String = "",
    var mobile: String = ""
)


open class BusinessDetailsModel(var result: BusinessDetailsResult = BusinessDetailsResult()) :
    CommonResponse()

open class BusinessDetailsResult(
    var id: String = "",
    var name: String = "",
    var regNumber: String = "",
    var type: String = "",
    var status: String = "",
    var categoryid: String = "",
    var categoryname: String = "",
    var subcategoryid: String = "",
    var mobile: String = "",
    var altMobile: String = "",
    var whatsapp: String = "",
    var email: String = "",
    var website: String = "",
    var address1: String = "",
    var address2: String = "",
    @SerializedName("address3") var address3: String = "",
    @SerializedName("locality") var locality: String = "",
    @SerializedName("area") var area: String = "",
    @SerializedName("taluk") var taluk: String = "",
    @SerializedName("pincode") var pincode: String = "",
    @SerializedName("landmark") var landmark: String = "",
    @SerializedName("stateid") var stateid: String = "",
    @SerializedName("districtid") var districtid: String = "",
    @SerializedName("geo_address") var geoAddress: String = "",
    @SerializedName("geo_location") var geoLocation: String = "",
    @SerializedName("latitude") var latitude: String = "",
    @SerializedName("longitude") var longitude: String = "",
    @SerializedName("inc_name") var incName: String = "",
    @SerializedName("inc_designation") var incDesignation: String = "",
    @SerializedName("inc_mobile") var incMobile: String = "",
    var incWhatsapp: String = "",
    var incEmail: String = "",
    var businessId: String = "",
    var imageUrl: String = "",
    var bannerbg: String = "https://static.toiimg.com/thumb/msid-123563339,imgsize-2166969,width-400,resizemode-4/123563339.jpg",


    )
