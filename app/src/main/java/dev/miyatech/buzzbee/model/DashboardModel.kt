package dev.miyatech.buzzbee.model


open class DashboardModel(
    var results: DashboardResult = DashboardResult()
) : CommonResponse()



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


open class DashboardLiveModel(
    var results: DashboardLiveResult = DashboardLiveResult()
) : CommonResponse()

open class DashboardLiveResult(
    var notification_count: String? = "0",
    var date_time: String? = "",
    var news: String? = " this is sample news",
    var max_val: Int = 0,
    var min_val: Int = 0
)
