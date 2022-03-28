package pw.naydenov.jobsity_task.core.resource_manager

import pw.naydenov.jobsity_task.CustomApplication

class ResourceManagerImpl: ResourceManager {

    override fun string(resId: Int) = CustomApplication.instance().getString(resId)

}