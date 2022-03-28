package pw.naydenov.jobsity_task.core.router

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pw.naydenov.jobsity_task.features.series_listing.SeriesListingFragment

class RouterImpl: Router {

    private val openScreenEmitter = MutableLiveData<OpenFragmentCommand>()

    override fun showListing() {
        openScreenEmitter.value = OpenFragmentCommand(
            fragment = SeriesListingFragment(),
            addToBackStack = false,
            replace = true
        )
    }

    override fun getFragmentEmitter(): LiveData<OpenFragmentCommand> {
        return openScreenEmitter as LiveData<OpenFragmentCommand>
    }

}