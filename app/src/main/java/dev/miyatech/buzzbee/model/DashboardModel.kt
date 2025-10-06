package dev.miyatech.buzzbee.model


open class DashboardModel(
    var results: DashboardResult = DashboardResult()
) :
    CommonResponse()


open class DashboardResult(
    var notification_count: String = "",
    var ads_banner: String = "",
    var slides: ArrayList<DashboardSlides> = ArrayList()
)


open class DashboardSlides(
    var id: String = "",
    var name: String = "",
    var imageUrl: String = ""
)
