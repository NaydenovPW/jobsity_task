package pw.naydenov.jobsity_task.core.router

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pw.naydenov.jobsity_task.features.tv_shows_listing.TvShowsListingFragment

class RouterImpl: Router {

    private val openScreenEmitter = MutableLiveData<OpenFragmentCommand>()

    override fun tvShowsListing() {
        openScreenEmitter.value = OpenFragmentCommand(
            fragment = TvShowsListingFragment(),
            addToBackStack = false,
            replace = true
        )
    }

    override fun getFragmentEmitter(): LiveData<OpenFragmentCommand> {
        return openScreenEmitter as LiveData<OpenFragmentCommand>
    }

}