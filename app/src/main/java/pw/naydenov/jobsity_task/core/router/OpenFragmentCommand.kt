package pw.naydenov.jobsity_task.core.router

import androidx.fragment.app.Fragment

class OpenFragmentCommand(
    val fragment: Fragment,
    val addToBackStack: Boolean = false,
    val replace: Boolean = true
)