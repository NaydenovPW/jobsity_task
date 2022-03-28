package pw.naydenov.jobsity_task.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pw.naydenov.jobsity_task.features.series_listing.SeriesListingViewModel

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SeriesListingViewModel::class)
    abstract fun seriesListingViewModel(viewModel: SeriesListingViewModel): ViewModel
}