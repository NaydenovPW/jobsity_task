package pw.naydenov.jobsity_task.features.tv_shows_listing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pw.naydenov.jobsity_task.core.resource_manager.ResourceManager
import pw.naydenov.jobsity_task.core.router.Router
import pw.naydenov.jobsity_task.network.NetworkApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class TvShowsListingViewModel @Inject constructor(
    private val res: ResourceManager,
    private val network: NetworkApiInterface,
    private val router: Router
) : ViewModel() {

    private var page: Int = 0
    private var isPageBottomReached = false
    private var isListingEndReached = false
    private var isSearch = false

    private val _tvShowsEmitter = MutableLiveData<TvShows>()
    val tvShowsEmitter = _tvShowsEmitter as LiveData<TvShows>

    private val _loadingStateEmitter = MutableLiveData<Boolean>()
    val loadingStateEmitter = _loadingStateEmitter as LiveData<Boolean>

    init {
        getTvShows(page)
    }

    fun getTvShows(page: Int) {
        _loadingStateEmitter.value = true
        network.getTvShows("$page").enqueue(object : Callback<List<TvShow>> {
            override fun onResponse(call: Call<List<TvShow>>, response: Response<List<TvShow>>) {
                _loadingStateEmitter.value = false
                isPageBottomReached = false
                response.body()?.let { tvShows ->
                    _tvShowsEmitter.value = TvShows(tvShows, isSearch)
                }
            }

            override fun onFailure(call: Call<List<TvShow>>, t: Throwable) {
                isListingEndReached = true
                _loadingStateEmitter.value = false
                t.printStackTrace()
            }
        })
    }

    fun onTvShowClicked(id: Int) {

    }

    fun onSearchInput(request: String) {
        if (request.isEmpty() || request.isBlank()) {
            isSearch = false
            _tvShowsEmitter.value = (TvShows(ArrayList<TvShow>(), true))
            getTvShows(page)
        } else {
            isSearch = true
            page = 0
            _loadingStateEmitter.value = true
            network
                .searchTvShows(request)
                .enqueue(object : Callback<List<TvShowSearchResult>> {
                    override fun onResponse(call: Call<List<TvShowSearchResult>>, response: Response<List<TvShowSearchResult>>) {
                        _loadingStateEmitter.value = false
                        response.body()?.let { searchResult ->
                            _tvShowsEmitter.value = TvShows(searchResultToShows(searchResult), isSearch)
                        }
                    }

                    override fun onFailure(call: Call<List<TvShowSearchResult>>, t: Throwable) {
                        _loadingStateEmitter.value = false
                        t.printStackTrace()
                    }

                })
        }
    }

    fun searchResultToShows(searchResult: List<TvShowSearchResult>): List<TvShow> {
        val tvShowsList = ArrayList<TvShow>()
        for (result in searchResult) {
            tvShowsList.add(result.show)
        }
        return tvShowsList
    }

    fun onBottomReached() {
        if (!isPageBottomReached && !isListingEndReached && !isSearch) {
            isPageBottomReached = true
            page += 1
            getTvShows(page)
        }
    }

}