package pw.naydenov.jobsity_task.features.tv_show_info

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import pw.naydenov.jobsity_task.CustomApplication
import pw.naydenov.jobsity_task.R
import pw.naydenov.jobsity_task.di.ViewModelFactory
import pw.naydenov.jobsity_task.network.pojo.Season
import pw.naydenov.jobsity_task.network.pojo.TvShow
import java.lang.StringBuilder
import javax.inject.Inject

class TvShowInfoFragment : Fragment() {

    lateinit var tvShow: TvShow

    private var showName: AppCompatTextView? = null
    private var airTitle: AppCompatTextView? = null
    private var airDays: AppCompatTextView? = null
    private var genres: AppCompatTextView? = null
    private var summary: AppCompatTextView? = null
    private var image: AppCompatImageView? = null
    private var list: LinearLayoutCompat? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: TvShowInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_show_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CustomApplication.instance().daggerCoreComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[TvShowInfoViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()
        findViews()
        fillViews()
        subscribeToViewModel()
        Log.e("TAG", "onStar() CALLED ")
        viewModel.loadEpisodes(tvShow.id)
    }

    private fun findViews() {
        showName = view?.findViewById(R.id.fragment_show_info_name)
        airTitle = view?.findViewById(R.id.fragment_show_info_air_title)
        airDays = view?.findViewById(R.id.fragment_show_info_air_days)
        genres = view?.findViewById(R.id.fragment_show_info_genres_text)
        summary = view?.findViewById(R.id.fragment_show_info_summary_text)
        image = view?.findViewById(R.id.fragment_show_info_image)
        list = view?.findViewById(R.id.fragment_show_info_linear)
    }

    private fun fillViews() {
        showName?.text = tvShow.name
        airTitle?.text = String.format(getString(R.string.air_time_title), tvShow.schedule.time)
        val days = StringBuilder()
        for (day in tvShow.schedule.days) days.append("$day\n")
        airDays?.text = days.toString()
        val genresBuilder = StringBuilder()
        for (genre in tvShow.genres) genresBuilder.append("$genre\n")
        genres?.text = genresBuilder.toString()
        image?.let {
            Glide
                .with(requireContext())
                .load(tvShow.image.medium)
                .error(R.drawable.no_image)
                .into(it)
        }
        summary?.text = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Html.fromHtml(tvShow.summary, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(tvShow.summary)
        }
    }

    private fun subscribeToViewModel() {
        viewModel.episodesEmitter.observe(viewLifecycleOwner, { seasons -> showEpisodes(seasons) })
    }

    private fun showEpisodes(seasons: List<Season>) {
        Log.e("TAG", "showEpisodes() CALLED")
        list?.let {
            for (season in seasons) {
                Log.e("TAG", "Season [${season.number}]")
                val seasonView = layoutInflater.inflate(R.layout.view_episode, null, false)

                if (seasonView.layoutParams is FrameLayout.LayoutParams) {
                    val p: ViewGroup.MarginLayoutParams =
                        seasonView.layoutParams as (FrameLayout.LayoutParams)
                    p.setMargins(16, 32, 16, 0)
                    seasonView.requestLayout()
                }

                val leftText = seasonView.findViewById<TextView>(R.id.view_episode_start_text)
                leftText.text = "Season #${season.number}"
                seasonView.isClickable = false
                seasonView.isFocusable = false
                seasonView.findViewById<TextView>(R.id.view_episode_end_text).visibility = View.GONE
                it.addView(seasonView)
                for (episode in season.episodes) {
                    val episodeView = layoutInflater.inflate(R.layout.view_episode, null, false)
                    episodeView.isClickable = true
                    episodeView.isFocusable = true
                    episodeView.setOnClickListener { viewModel.onEpisodeClick(episode) }
                    val leftText = episodeView.findViewById<TextView>(R.id.view_episode_start_text)
                    leftText.text = "${episode.number}"
                    episodeView.findViewById<TextView>(R.id.view_episode_end_text).text = episode.name
                    it.addView(episodeView)
                }
            }
        }
    }

    companion object {
        fun instance(tvShow: TvShow): TvShowInfoFragment {
            val newInstance = TvShowInfoFragment()
            newInstance.tvShow = tvShow
            return newInstance
        }
    }

}