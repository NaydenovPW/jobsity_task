package pw.naydenov.jobsity_task.network

import pw.naydenov.jobsity_task.features.tv_shows_listing.TvShow
import pw.naydenov.jobsity_task.features.tv_shows_listing.TvShowSearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApiInterface {

    @GET("shows")
    fun getTvShows(@Query("page") page: String) : Call<List<TvShow>>

    @GET("search/shows")
    fun searchTvShows(@Query("q") query: String) : Call<List<TvShowSearchResult>>

}