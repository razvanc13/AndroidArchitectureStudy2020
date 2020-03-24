package com.razbyte.architectureexample.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.razbyte.architectureexample.ActivityNavigation


abstract class BaseFragment<T : ViewDataBinding, T2 : BaseViewModel> : Fragment() {

    // Implement with the layout id
    abstract val layoutId: Int

    // Inject ViewModel lazy
    abstract val viewModel: T2

    // Data binding variable
    lateinit var binding: T private set

    // Navigation
    val router: ActivityNavigation by lazy { (activity as ActivityNavigation) }

    // Fragment initialization
    abstract fun onCreateInit()

    // Init observers
    abstract fun onCreateObservers()

    // Init user interactions
    abstract fun onCreateInteractions()

    override fun onCreateView(
        inflater: LayoutInflater, @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        onCreateInit()
        onCreateObservers()
        onCreateInteractions()
        return binding.root
    }

}