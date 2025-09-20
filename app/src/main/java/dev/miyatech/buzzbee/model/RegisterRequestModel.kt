package dev.miyatech.buzzbee.model

data class StateList(var id: String = "", var name: String = "", var image: String = "")


data class StateListModel(var results: ArrayList<StateList> = arrayListOf()) :
    CommonResponse()