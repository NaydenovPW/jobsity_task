package pw.naydenov.jobsity_task

import android.app.Application
import pw.naydenov.jobsity_task.di.CoreComponent
import pw.naydenov.jobsity_task.di.DaggerCoreComponent

class CustomApplication: Application() {

    lateinit var daggerCoreComponent: CoreComponent

    override fun onCreate() {
        super.onCreate()

        applicationInstance = this
        daggerCoreComponent = DaggerCoreComponent.builder().build()
    }

    companion object {
        private lateinit var applicationInstance: CustomApplication
        fun instance() = applicationInstance
    }

}