package dev.miyatech.buzzbee.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.miyatech.buzzbee.model.PostModel
import dev.miyatech.buzzbee.model.StateList


class ManageByBusinessViewModel() : ViewModel() {

    val _postList: MutableLiveData<ArrayList<PostModel>> = MutableLiveData(arrayListOf())
    val createPostDate: MutableLiveData<String> = MutableLiveData("12-12-2024 to 10-09-2025")
    val fromDate: MutableLiveData<String> = MutableLiveData("From Date")
    val toDate: MutableLiveData<String> = MutableLiveData("To Date")
    val postTypes: MutableLiveData<String> = MutableLiveData("")

    val postList: LiveData<ArrayList<PostModel>> = _postList;

    val postType: ArrayList<StateList> = arrayListOf()

    fun getPostType() {

        postType.clear()
        var dig_array = arrayListOf<StateList>()
        dig_array.add(StateList("1", "Daily", ""))
        dig_array.add(StateList("2", "Weekly", ""))
        dig_array.add(StateList("3", "Month", ""))
        dig_array.add(StateList("4", "Festival", ""))
        postType.addAll(dig_array);
    }


    fun addPost1(title: String, desc: String, img: String) {
        val currentList = _postList.value ?: arrayListOf()
        currentList.add(PostModel(title, desc, false, img))
        _postList.value = currentList;
//        postList.add(PostModel(title, desc, img))

    }

    fun removePost(model: PostModel) {
        val currentList = _postList.value ?: arrayListOf()
        currentList.remove(model)
        _postList.value = currentList
    }


    fun updateDate(f:String,t: String) {
        createPostDate.value = f.toString() + " to " + t.toString()
    }

    fun updateFromDate(date: String) {
        fromDate.value = date
    }

    fun updateToDate(date: String) {
        toDate.value = date
    }

    fun updateType(date: String) {
        postTypes.value = date
    }


    fun updatePost(p: Int, model: PostModel) {
        val currentList = _postList.value ?: arrayListOf()
        currentList.set(p, model)
        _postList.value = currentList
    }


    fun temp(): ArrayList<PostModel> {

        var img =
            "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"


        var arrayList = ArrayList<PostModel>()
        arrayList.add(
            PostModel(
                "Title ", "Sample title ", false, img
            )
        )
        arrayList.add(
            PostModel(
                "Title ", "Sample title ", false, img
            )
        )
        arrayList.add(
            PostModel(
                "Title ", "Sample title ", false, img
            )
        )




        return arrayList;
    }


}

