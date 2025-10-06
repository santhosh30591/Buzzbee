package dev.miyatech.buzzbee.model

import com.google.gson.annotations.SerializedName


open class NotificationListModel(
    var results: ArrayList<NotificationResult> = ArrayList()
) : CommonResponse()


open class NotificationDetails(
    var results: NotificationResult = NotificationResult()
) : CommonResponse()


open class NotificationResult(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("content_type") var contentType: String? = null,
    @SerializedName("scheduled_date") var scheduledDate: String? = null,
    @SerializedName("scheduled_time") var scheduledTime: String? = null,
    @SerializedName("stateId") var stateId: String? = null,
    @SerializedName("districtId") var districtId: String? = null,
    @SerializedName("status") var status: Int? = null,
    @SerializedName("error_content") var errorContent: String? = null,
    @SerializedName("photo") var photo: String? = null,
    @SerializedName("created_date") var createdDate: String? = null,
    @SerializedName("is_delete") var isDelete: Int? = null
)
