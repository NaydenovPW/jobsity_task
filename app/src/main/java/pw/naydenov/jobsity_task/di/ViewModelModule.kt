package pw.naydenov.jobsity_task.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pw.naydenov.jobsity_task.features.tv_show_info.TvShowInfoViewModel
import pw.naydenov.jobsity_task.features.tv_shows_listing.TvShowsListingViewModel
import javax.inject.Singleton

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @Singleton
    @ViewModelKey(TvShowsListingViewModel::class)
    abstract fun tvShowsListingViewModel(viewModel: TvShowsListingViewModel): ViewModel

    @Binds
    @IntoMap
    @Singleton
    @ViewModelKey(TvShowInfoViewModel::class)
    abstract fun tvShowInfoViewModel(viewModel: TvShowInfoViewModel): ViewModel
}