package pw.naydenov.jobsity_task.network.pojo

import com.google.gson.annotations.SerializedName

data class TvShow(
    @SerializedName("id")
    var id: Int,
    @SerializedName("image")
    var image: TvShowImage,
    @SerializedName("name")
    var name: String,
    @SerializedName("genres")
    var genres: List<String>,
    @SerializedName("schedule")
    var schedule: Schedule,
    @SerializedName("summary")
    var summary: String
)