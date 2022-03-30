package pw.naydenov.jobsity_task.di

import dagger.Component
import pw.naydenov.jobsity_task.MainActivity
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

}