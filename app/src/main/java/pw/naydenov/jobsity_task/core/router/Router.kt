package pw.naydenov.jobsity_task.core.router

import androidx.lifecycle.LiveData

interface Router {
    fun getFragmentEmitter(): LiveData<OpenFragmentCommand>
    fun showListing()
}