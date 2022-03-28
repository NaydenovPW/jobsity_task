package pw.naydenov.jobsity_task.di

import dagger.Module
import dagger.Provides
import pw.naydenov.jobsity_task.core.resource_manager.ResourceManager
import pw.naydenov.jobsity_task.core.resource_manager.ResourceManagerImpl
import pw.naydenov.jobsity_task.core.router.Router
import pw.naydenov.jobsity_task.core.router.RouterImpl
import javax.inject.Singleton

@Module
class CoreModule {

    @Provides
    @Singleton
    fun provideResourceManager(): ResourceManager = ResourceManagerImpl()

    @Provides
    @Singleton
    fun provideRouter(): Router = RouterImpl()

}