package pw.naydenov.jobsity_task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pw.naydenov.jobsity_task.core.router.Router
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CustomApplication.instance().daggerCoreComponent.inject(this)
        setContentView(R.layout.activity_main)
        initViews()
    }

    override fun onResume() {
        super.onResume()
        setFragmentListener()
        router.showListing()
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