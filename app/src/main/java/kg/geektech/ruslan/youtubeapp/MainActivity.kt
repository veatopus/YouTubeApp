package kg.geektech.ruslan.youtubeapp

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.videoInfoFragment)
                window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            else
                window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        }
    }

}
