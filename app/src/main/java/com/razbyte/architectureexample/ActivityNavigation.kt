package com.razbyte.architectureexample

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.razbyte.architectureexample.data.entities.RepositoryData
import com.razbyte.architectureexample.ui.details.DetailsFragment
import com.razbyte.architectureexample.ui.main.MainFragment
import org.koin.android.ext.android.get
import org.koin.androidx.fragment.android.setupKoinFragmentFactory

class ActivityNavigation : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        setupKoinFragmentFactory()

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.WHITE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = window.decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        if (savedInstanceState == null) {
            goToMainFragment()
        }
    }

    // region Navigation

    fun goToMainFragment() {
        val mainFragment: MainFragment = get()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, mainFragment)
            .commit()
    }

    fun pushOpenDetailsFragment(repositoryData: RepositoryData) {
        val detailsFragment: DetailsFragment = get()
        detailsFragment.repositoryData = repositoryData
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, detailsFragment)
            .addToBackStack(null)
            .commit()
    }

    fun popBackStack() {
        supportFragmentManager.popBackStack()
    }

    // endregion

}
