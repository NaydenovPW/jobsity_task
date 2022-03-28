package pw.naydenov.jobsity_task.features.series_listing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import pw.naydenov.jobsity_task.CustomApplication
import pw.naydenov.jobsity_task.R
import pw.naydenov.jobsity_task.di.ViewModelFactory
import javax.inject.Inject

class SeriesListingFragment: Fragment() {

    private var title: AppCompatTextView? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: SeriesListingViewModel
    //val viewModel: SeriesListingViewModel = ViewModelProvider(this).get(SeriesListingViewModel::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_show_listing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CustomApplication.instance().daggerCoreComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[SeriesListingViewModel::class.java]
        findViews()
        subscribeToViewModel()
        viewModel.onViewReady()
    }

    private fun findViews() {
        title = view?.findViewById(R.id.fragment_show_listing_title)
    }

    private fun subscribeToViewModel() {
        viewModel.titleTextEmitter.observe(this.viewLifecycleOwner, { text -> title?.text = text })
    }

}