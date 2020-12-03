package kg.geektech.ruslan.youtubeapp

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private var backStackEntryCount: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        backStackEntryCount = navHostFragment.childFragmentManager.backStackEntryCount

        @Suppress("DEPRECATION")
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.videoInfoFragment) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    window.insetsController?.hide(WindowInsets.Type.statusBars())
                } else {
                    window.setFlags(
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN
                    )
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                    window.insetsController?.show(WindowInsets.Type.statusBars())
                else
                    window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            }

        }
    }

    @SuppressLint("RestrictedApi")
    override fun onBackPressed() {
        if (backStackEntryCount != null)
            if (backStackEntryCount!! > 0)
                navController.popBackStack()
            else super.onBackPressed()
    }

}
