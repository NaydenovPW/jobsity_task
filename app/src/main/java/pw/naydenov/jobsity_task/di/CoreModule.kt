package pw.naydenov.jobsity_task.di

import dagger.Module
import dagger.Provides
import pw.naydenov.jobsity_task.CustomApplication
import pw.naydenov.jobsity_task.core.resource_manager.ResourceManager
import pw.naydenov.jobsity_task.core.resource_manager.ResourceManagerImpl
import pw.naydenov.jobsity_task.core.router.Router
import pw.naydenov.jobsity_task.core.router.RouterImpl
import pw.naydenov.jobsity_task.network.NetworkApiInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import okhttp3.OkHttpClient

import okhttp3.logging.HttpLoggingInterceptor




@Module
class CoreModule {

    @Provides
    @Singleton
    fun provideResourceManager(): ResourceManager = ResourceManagerImpl()

    @Provides
    @Singleton
    fun provideRouter(): Router = RouterImpl()

    @Provides
    @Singleton
    fun provideRetrofit(): NetworkApiInterface {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(CustomApplication.API_URL)
            .client(httpClient.build())
            .build()
        return retrofit.create(NetworkApiInterface::class.java)
    }
}