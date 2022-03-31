package pw.naydenov.jobsity_task.features.episode_info

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import pw.naydenov.jobsity_task.CustomApplication
import pw.naydenov.jobsity_task.R
import pw.naydenov.jobsity_task.di.ViewModelFactory
import pw.naydenov.jobsity_task.features.tv_show_info.TvShowInfoFragment
import pw.naydenov.jobsity_task.network.pojo.Episode
import pw.naydenov.jobsity_task.network.pojo.TvShow
import javax.inject.Inject

class EpisodeInfoFragment: Fragment() {

    lateinit var episode: Episode

    private var name: AppCompatTextView? = null
    private var seasonNumber: AppCompatTextView? = null
    private var episodeNumber: AppCompatTextView? = null
    private var summary: AppCompatTextView? = null
    private var image: AppCompatImageView? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: EpisodeInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_episode_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CustomApplication.instance().daggerCoreComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[EpisodeInfoViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()
        findViews()
        fillViews()
    }

    private fun findViews() {
        name = view?.findViewById(R.id.fragment_episode_info_name)
        seasonNumber = view?.findViewById(R.id.fragment_episode_info_season)
        episodeNumber = view?.findViewById(R.id.fragment_episode_info_number)
        summary = view?.findViewById(R.id.fragment_episode_info_summary_text)
        image = view?.findViewById(R.id.fragment_episode_info_image)
    }

    private fun fillViews() {
        name?.text = episode.name
        seasonNumber?.text = String.format(getString(R.string.season_number), episode.season)
        episodeNumber?.text = String.format(getString(R.string.episode_number), episode.number)
        image?.let {
            Glide
                .with(requireContext())
                .load(episode.image.medium)
                .error(R.drawable.no_image)
                .into(it)
        }
        summary?.text = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Html.fromHtml(episode.summary, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(episode.summary)
        }
    }

    companion object {
        fun instance(episode: Episode): EpisodeInfoFragment {
            val newInstance = EpisodeInfoFragment()
            newInstance.episode = episode
            return newInstance
        }
    }

}