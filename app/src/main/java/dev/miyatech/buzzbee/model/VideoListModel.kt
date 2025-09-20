package dev.miyatech.buzzbee.model

open class VideoListModel(
    var id: String = "",
    var title: String = "",
    var description: String = "",
    var thumb_image: String = "",
    var logo: String = "",
    var url: String = "",
    var share: String = "",
    var address: String = "",
    var last_update: String = "",
)

open class VideoResultModel(var results: ArrayList<VideoListModel> = ArrayList()) :
    CommonResponse()