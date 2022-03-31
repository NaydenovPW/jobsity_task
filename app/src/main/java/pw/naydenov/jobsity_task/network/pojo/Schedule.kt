package pw.naydenov.jobsity_task.network.pojo

import com.google.gson.annotations.SerializedName

data class Schedule(
    @SerializedName("time")
    var time: String,
    @SerializedName("days")
    var days: List<String>
) {
}