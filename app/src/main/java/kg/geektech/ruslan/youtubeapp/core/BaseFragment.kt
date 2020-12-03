package kg.geektech.ruslan.youtubeapp.core

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import java.util.*


abstract class BaseFragment<V : BaseViewModel, B : ViewBinding>(val layoutID: Int) : Fragment() {

    var mViewModule: V? = null
    var binding: B? = null

    abstract fun getViewModule(): V
    abstract fun getViewBinding(): B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (savedInstanceState != null) onRestoreInstanceState(savedInstanceState)
        binding = getViewBinding()
        return binding?.root
    }

    open fun onRestoreInstanceState(saveInstanceState: Bundle){

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModule = getViewModule()
        setUpViewModelObs(mViewModule!!)
        obs(mViewModule!!)
        binding?.let { setUpView(it) }
    }

    private fun obs(viewModule: V) {
        viewModule.isFinished.observe(requireActivity(), Observer { isFinish(it) })
        viewModule.isLoading.observe(requireActivity(), Observer { progress(it) })
        viewModule.toast.observe(requireActivity(), Observer { requireContext().showToast(it) })
    }

    abstract fun progress(isProgress: Boolean)

    private fun isFinish(isFinish: Boolean){
        if (isFinish) findNavController().navigateUp()
    }

    abstract fun setUpView(binding: B)

    abstract fun setUpViewModelObs(viewModel: V)

    override fun onResume() {
        val locate = "ru" // load in Prefs
        setLocale(locate, requireContext())
        super.onResume()
    }

    private fun setLocale(s: String, context:Context) {
        val locale = Locale(s)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        context.resources.updateConfiguration(
            config,
            context.resources.displayMetrics)
    }

}