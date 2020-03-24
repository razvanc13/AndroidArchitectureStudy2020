package com.razbyte.architectureexample.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.razbyte.architectureexample.base.BaseViewModel
import com.razbyte.architectureexample.business.helpers.utils.State
import com.razbyte.architectureexample.business.providers.GitApi
import com.razbyte.architectureexample.business.sources.RepositoriesDataSource
import com.razbyte.architectureexample.business.sources.RepositoriesDataSourceFactory
import com.razbyte.architectureexample.data.entities.RepositoryData
import org.koin.core.KoinComponent

class MainViewModel(application: Application) : BaseViewModel(application), KoinComponent {

    // region Observables

    var repositoriesDataSourceFactory: RepositoriesDataSourceFactory
    var repositories: LiveData<PagedList<RepositoryData>>
    val error = MutableLiveData<Boolean>().apply { value = false }
    val loading = MutableLiveData<Boolean>().apply { value = true }

    // endregion

    init {
        repositoriesDataSourceFactory = RepositoriesDataSourceFactory()
        val config = PagedList.Config.Builder()
            .setPageSize(GitApi.PAGE_SIZE)
            .setInitialLoadSizeHint(GitApi.PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()
        repositories = LivePagedListBuilder<Int, RepositoryData>(
            repositoriesDataSourceFactory, config
        ).build()
    }

    fun getState(): LiveData<State> = Transformations.switchMap<RepositoriesDataSource,
            State>(repositoriesDataSourceFactory.sourceLiveData, RepositoriesDataSource::state)
}
