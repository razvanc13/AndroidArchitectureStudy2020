package com.razbyte.architectureexample.ui.main

import android.view.View
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.razbyte.architectureexample.R
import com.razbyte.architectureexample.base.BaseFragment
import com.razbyte.architectureexample.business.helpers.utils.State
import com.razbyte.architectureexample.data.entities.RepositoryData
import com.razbyte.architectureexample.databinding.MainScreenBinding
import org.koin.android.ext.android.inject

class MainFragment : BaseFragment<MainScreenBinding, MainViewModel>() {

    override val layoutId = R.layout.main__screen
    override val viewModel: MainViewModel by inject()

    private val repositoriesAdapter = MainRepositoriesAdapter()

    override fun onCreateInit() {
        binding.reposList.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        binding.reposList.adapter = repositoriesAdapter
    }

    override fun onCreateObservers() {
        viewModel.repositories.observe(
            this,
            Observer<PagedList<RepositoryData>> { fetchedRepositories ->
                repositoriesAdapter.submitList(fetchedRepositories)
            })
        viewModel.getState().observe(this, Observer<State> { state ->
            state ?: return@Observer
            when (state) {
                State.DONE -> binding.loading.visibility = View.GONE
                State.LOADING -> binding.loading.visibility = View.VISIBLE
                State.ERROR -> binding.loading.visibility = View.VISIBLE
            }
        })
    }

    override fun onCreateInteractions() {
        repositoriesAdapter.onItemClick = { item ->
            router.pushOpenDetailsFragment(item)
        }
    }
}
