package dev.miyatech.buzzbee.netwoork


class ApiHelper(private val apiService: ApiInterface) {


    fun login(mobile: String, password: String) = apiService.login(mobile, password)

    suspend fun postLogin(mobile: String, password: String) = apiService.postLogin(mobile, password)
    suspend fun stateList() = apiService.stateList()
    suspend fun districtList(id: String) = apiService.districtList(id)
    suspend fun cityList(id: String) = apiService.cityList(id)
    suspend fun registerApi(
        name: String,
        states: String,
        district: String,
        city: String,
        mobile: String,
        password: String,
        deviceid: String,
    ) = apiService.register(name, states, district, city, mobile, password, deviceid)


    suspend fun getVideos() = apiService.getVideos()
    suspend fun discover() = apiService.getDiscover()
    suspend fun subCategory(id: String) = apiService.getCategory(id)
    suspend fun bussinessList(id: String) = apiService.businessList(id)
    suspend fun bussinessDetails(id: String) = apiService.businessDetails(id)


}
