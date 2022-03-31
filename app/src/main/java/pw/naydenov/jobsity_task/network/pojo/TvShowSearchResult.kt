package pw.naydenov.jobsity_task.network.pojo

import com.google.gson.annotations.SerializedName

data class TvShowSearchResult(
    @SerializedName("score")
    var score: Float,
    @SerializedName("show")
    var show: TvShow
) {
}