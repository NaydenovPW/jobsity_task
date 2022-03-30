package pw.naydenov.jobsity_task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import pw.naydenov.jobsity_task.core.router.Router
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CustomApplication.instance().daggerCoreComponent.inject(this)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)
        initViews()
    }

    override fun onResume() {
        super.onResume()
        setFragmentListener()
        router.tvShowsListing()
    }

    private fun initViews() {

    }

    private fun setFragmentListener() {
        router
            .getFragmentEmitter()
            .observe(this, { command ->
                val transaction = supportFragmentManager.beginTransaction()
                if (command.replace) transaction.replace(R.id.activity_main_fragment_container, command.fragment)
                if (command.addToBackStack) transaction.addToBackStack(command.fragment.tag)
                transaction.commit()
            })
    }

}