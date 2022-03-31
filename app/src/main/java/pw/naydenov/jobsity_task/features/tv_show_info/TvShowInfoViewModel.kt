package pw.naydenov.jobsity_task.features.tv_show_info

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pw.naydenov.jobsity_task.core.router.Router
import pw.naydenov.jobsity_task.network.NetworkApiInterface
import pw.naydenov.jobsity_task.network.pojo.Episode
import pw.naydenov.jobsity_task.network.pojo.Season
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class TvShowInfoViewModel @Inject constructor(
    private val router: Router,
    private val network: NetworkApiInterface
): ViewModel() {

    private val _episodesEmitter = MutableLiveData<List<Season>>()
    val episodesEmitter = _episodesEmitter as LiveData<List<Season>>

    fun loadEpisodes(tvShowId: Int) {
        network
            .getEpisodes(tvShowId)
            .enqueue(object: Callback<List<Episode>>{
                override fun onResponse(
                    call: Call<List<Episode>>,
                    response: Response<List<Episode>>
                ) {
                    response.body()?.let { episodesBySeasons(it) }
                }

                override fun onFailure(call: Call<List<Episode>>, t: Throwable) {
                    t.printStackTrace()
                }

            })
    }

    fun episodesBySeasons(episodes: List<Episode>) {
        Log.e("TAG", "episodesBySeasons() CALLED")
        val seasonsMap = HashMap<Int, ArrayList<Episode>>()
        for (episode in episodes) {
            if (seasonsMap.containsKey(episode.season)) {
                seasonsMap.get(episode.season)?.add(episode)
            } else {
                val list = ArrayList<Episode>()
                list.add(episode)
                seasonsMap.put(episode.season, list)
            }
        }
        val seasonsList = ArrayList<Season>()
        for (seasonNumber in seasonsMap.keys) {
            seasonsList.add(Season(seasonNumber, seasonsMap.get(seasonNumber) as List<Episode>))
        }
        _episodesEmitter.value = seasonsList
    }

    fun onEpisodeClick(episode: Episode) {
        router.episodeInfo(episode)
    }

}