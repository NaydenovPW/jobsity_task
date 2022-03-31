package pw.naydenov.jobsity_task.core.router

import androidx.lifecycle.LiveData
import pw.naydenov.jobsity_task.network.pojo.TvShow

interface Router {
    fun getFragmentEmitter(): LiveData<OpenFragmentCommand>
    fun tvShowsListing()
    fun tvShowInfo(show: TvShow)
}