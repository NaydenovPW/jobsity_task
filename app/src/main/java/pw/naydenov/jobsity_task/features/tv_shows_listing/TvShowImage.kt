package pw.naydenov.jobsity_task.features.tv_shows_listing

import com.google.gson.annotations.SerializedName

data class TvShowImage(
    @SerializedName("medium")
    var medium: String,
    @SerializedName("original")
    var original: String
) {
}