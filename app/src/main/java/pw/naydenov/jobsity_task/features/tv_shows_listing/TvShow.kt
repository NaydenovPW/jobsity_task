package pw.naydenov.jobsity_task.features.tv_shows_listing

import com.google.gson.annotations.SerializedName

data class TvShow(
    @SerializedName("id")
    var id: Int,
    @SerializedName("image")
    var image: TvShowImage,
    @SerializedName("name")
    var name: String
) {
}