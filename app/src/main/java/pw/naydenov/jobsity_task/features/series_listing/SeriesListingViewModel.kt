package pw.naydenov.jobsity_task.features.series_listing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pw.naydenov.jobsity_task.R
import pw.naydenov.jobsity_task.core.resource_manager.ResourceManager
import javax.inject.Inject

class SeriesListingViewModel @Inject constructor(
    private val res: ResourceManager
): ViewModel() {

    private val _titleTextEmitter = MutableLiveData<String>()
    val titleTextEmitter = _titleTextEmitter as LiveData<String>

    fun onViewReady() {
        _titleTextEmitter.value = "View model injected. " + res.string(R.string.listing_all) + "."
    }

}