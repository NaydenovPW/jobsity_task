package pw.naydenov.jobsity_task.di

import dagger.Component
import pw.naydenov.jobsity_task.MainActivity
import pw.naydenov.jobsity_task.features.episode_info.EpisodeInfoFragment
import pw.naydenov.jobsity_task.features.tv_show_info.TvShowInfoFragment
import pw.naydenov.jobsity_task.features.tv_shows_listing.TvShowsListingFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [
    CoreModule::class,
    ViewModelModule::class
])
interface CoreComponent {

    fun inject(activity: MainActivity)
    fun inject(fragment: TvShowsListingFragment)
    fun inject(fragment: TvShowInfoFragment)
    fun inject(fragment: EpisodeInfoFragment)

}