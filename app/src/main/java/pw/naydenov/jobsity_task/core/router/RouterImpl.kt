package pw.naydenov.jobsity_task.core.router

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pw.naydenov.jobsity_task.features.episode_info.EpisodeInfoFragment
import pw.naydenov.jobsity_task.features.tv_show_info.TvShowInfoFragment
import pw.naydenov.jobsity_task.features.tv_shows_listing.TvShowsListingFragment
import pw.naydenov.jobsity_task.network.pojo.Episode
import pw.naydenov.jobsity_task.network.pojo.TvShow

class RouterImpl: Router {

    private val openScreenEmitter = MutableLiveData<OpenFragmentCommand>()

    override fun getFragmentEmitter(): LiveData<OpenFragmentCommand> {
        return openScreenEmitter as LiveData<OpenFragmentCommand>
    }

    override fun tvShowsListing() {
        openScreenEmitter.value = OpenFragmentCommand(
            fragment = TvShowsListingFragment(),
            addToBackStack = false,
            replace = true
        )
    }

    override fun tvShowInfo(show: TvShow) {
        openScreenEmitter.value = OpenFragmentCommand(
            fragment = TvShowInfoFragment.instance(show),
            addToBackStack = true,
            replace = true
        )
    }

    override fun episodeInfo(episode: Episode) {
        openScreenEmitter.value = OpenFragmentCommand(
            fragment = EpisodeInfoFragment.instance(episode),
            addToBackStack = true,
            replace = true
        )
    }

}