package pw.naydenov.jobsity_task.network.pojo

import com.google.gson.annotations.SerializedName

data class Episode(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("number")
    var number: Int,
    @SerializedName("season")
    var season: Int,
    @SerializedName("summary")
    var summary: String,
    @SerializedName("image")
    var image: TvShowImage

) {
}