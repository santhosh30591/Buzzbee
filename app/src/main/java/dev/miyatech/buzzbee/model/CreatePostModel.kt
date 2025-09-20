package dev.miyatech.buzzbee.model


data class PostModel(
    var title: String = "",
    var description: String = "",
//    var image: Bitmap? = null,
    var isExpand: Boolean = false,
//    var file: File? = null,
    var bitmapString: String = ""
)

