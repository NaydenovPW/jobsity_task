package pw.naydenov.jobsity_task.network

import pw.naydenov.jobsity_task.network.pojo.Episode
import pw.naydenov.jobsity_task.network.pojo.TvShow
import pw.naydenov.jobsity_task.network.pojo.TvShowSearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkApiInterface {

    @GET("shows")
    fun getTvShows(@Query("page") page: String) : Call<List<TvShow>>

    @GET("search/shows")
    fun searchTvShows(@Query("q") query: String) : Call<List<TvShowSearchResult>>

    @GET("shows/{showId}/episodes")
    fun getEpisodes(@Path("showId") showId: Int) : Call<List<Episode>>

}