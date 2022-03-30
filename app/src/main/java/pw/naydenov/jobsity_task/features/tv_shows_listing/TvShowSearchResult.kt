package pw.naydenov.jobsity_task.features.tv_shows_listing

import com.google.gson.annotations.SerializedName

class TvShowSearchResult(
    @SerializedName("score")
    var score: Float,
    @SerializedName("show")
    var show: TvShow
) {
}