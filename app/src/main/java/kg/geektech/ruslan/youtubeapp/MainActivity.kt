package kg.geektech.ruslan.youtubeapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController

        navController.navigate(R.id.playListsFragment)

        //отслеживание отображаемых фрагментов, вдруг пригодиться
        /*navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.playListsFragment)
                supportActionBar?.hide()
            else supportActionBar?.show()
        }*/
    }

}
