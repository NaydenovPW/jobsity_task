package pw.naydenov.jobsity_task.features.tv_shows_listing

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import pw.naydenov.jobsity_task.CustomApplication
import pw.naydenov.jobsity_task.R
import pw.naydenov.jobsity_task.di.ViewModelFactory
import javax.inject.Inject
import androidx.recyclerview.widget.GridLayoutManager
import android.content.pm.ActivityInfo
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.EditText
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import pw.naydenov.jobsity_task.network.pojo.TvShow
import pw.naydenov.jobsity_task.network.pojo.TvShows
import java.util.concurrent.TimeUnit

class TvShowsListingFragment : Fragment() {

    private var search: EditText? = null
    private var recycler: RecyclerView? = null
    private var progress: View? = null
    private val disposables = CompositeDisposable()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: TvShowsListingViewModel

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
        viewModel = ViewModelProvider(this, viewModelFactory)[TvShowsListingViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()
        findViews()
        initAdapter()
        subscribeToViewModel()
        setSearchEmitter()
    }

    private fun findViews() {
        search = view?.findViewById(R.id.fragment_tv_shows_listing_search)
        recycler = view?.findViewById(R.id.fragment_tv_shows_listing_recycler)
        progress = view?.findViewById(R.id.fragment_tv_shows_listing_progress)
    }

    private fun initAdapter() {
        recycler?.let { recycler ->
            val mLayoutManager: RecyclerView.LayoutManager =
                GridLayoutManager(context, calculateSpanCount())
            recycler.layoutManager = mLayoutManager

            recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val canScrollDown = recyclerView.canScrollVertically(1)
//                    val canScrollUp = recyclerView.canScrollVertically(-1)
                    if (!canScrollDown) {
                        viewModel.onBottomReached()
                    }
//                    if (!canScrollUp) {
//                        viewModel.onTopReached()
//                    }
                }
            })
        }
    }

    private fun subscribeToViewModel() {
        viewModel.tvShowsEmitter.observe(viewLifecycleOwner, { tvShows -> setTvShows(tvShows) })
        viewModel.loadingStateEmitter.observe(
            viewLifecycleOwner,
            { isLoading -> setLoadingState(isLoading) })
    }

    private fun setSearchEmitter() {
        Log.e("TAG", "setSearchEmitter() search is [$search]")
        search?.let { search ->
            disposables.add(
                Observable
                    .create<String> {
                        Log.e("TAG", "ObservableEmitter() ")
                        search.addTextChangedListener(object: TextWatcher{
                            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                            override fun afterTextChanged(p0: Editable?) {
                                Log.e("TAG", "afterTextChanged: ")
                                it.onNext(p0.toString())
                            }
                        })
                    }
                    .debounce(1700, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { searchText -> viewModel.onSearchInput(searchText) },
                        { error -> error.printStackTrace() }
                    )
            )
        }
    }

    private fun setTvShows(tvShows: TvShows) {
        val adapter = recycler?.adapter
        if (adapter == null) {
            recycler?.adapter = TvShowsListingAdapter(
                tvShows.tvShows,
                object : TvShowsListingAdapter.TvShowClickListener {
                    override fun onItemClick(tvShow: TvShow) {
                        viewModel.onTvShowClicked(tvShow)
                    }
                })
        } else {
            (recycler?.adapter as TvShowsListingAdapter).addItems(tvShows.tvShows, !tvShows.append)
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        if (isLoading) progress?.visibility = View.VISIBLE
        else progress?.visibility = View.GONE
    }

    private fun calculateSpanCount() =
        if (resources.configuration.orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) PORTRAIT_SPAN_COUNT
        else LANDSCAPE_SPAN_COUNT


    override fun onStop() {
        disposables.clear()
        super.onStop()
    }

    companion object {
        private const val PORTRAIT_SPAN_COUNT = 2
        private const val LANDSCAPE_SPAN_COUNT = 4
    }

}