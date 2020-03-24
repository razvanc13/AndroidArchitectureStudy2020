package com.razbyte.architectureexample.ui.details

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.razbyte.architectureexample.R
import com.razbyte.architectureexample.base.BaseFragment
import com.razbyte.architectureexample.data.entities.RepositoryData
import com.razbyte.architectureexample.databinding.DetailsScreenBinding
import org.koin.android.ext.android.inject

class DetailsFragment : BaseFragment<DetailsScreenBinding, DetailsViewModel>() {

    override val layoutId = R.layout.details__screen
    override val viewModel: DetailsViewModel by inject()

    var repositoryData: RepositoryData? = null

    override fun onCreateInit() {

        if (repositoryData == null) {
            Log.e("DetailsFragment", "No repository data sent")
            router.popBackStack()
            return
        }

        repositoryData?.let {
            binding.name.text = it.name
            binding.fullName.text = it.fullName
            binding.watchers.text = getString(R.string.details_watchers, it.watchers)
            binding.forks.text = getString(R.string.details_forks, it.forks)
            binding.language.text = getString(R.string.details_language, it.language)
            binding.branch.text = it.defaultBranch
            binding.issues.text = getString(R.string.details_open_issues, it.issues)

            viewModel.fetchReadme(it.fullName, it.defaultBranch)
        }
    }

    override fun onCreateObservers() {
        viewModel.readme.observe(this, Observer<String> { text ->
            binding.readme.text = text
        })
        viewModel.error.observe(this, Observer<Boolean> { activated ->
            binding.failed.visibility = if (activated) View.VISIBLE else View.GONE
        })
        viewModel.loading.observe(this, Observer<Boolean> { activated ->
            binding.loading.visibility = if (activated) View.VISIBLE else View.GONE
        })
    }

    override fun onCreateInteractions() {
        binding.backButton.setOnClickListener {
            router.popBackStack()
        }
    }
}
