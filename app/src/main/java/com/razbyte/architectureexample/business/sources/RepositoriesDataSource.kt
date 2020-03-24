package com.razbyte.architectureexample.business.sources

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.razbyte.architectureexample.business.helpers.utils.State
import com.razbyte.architectureexample.business.providers.GitApi
import com.razbyte.architectureexample.data.entities.RepositoryData
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class RepositoriesDataSource : PageKeyedDataSource<Int, RepositoryData>(), KoinComponent {

    private val retryDelay = 3000L

    private val gitApi: GitApi by inject()

    var state: MutableLiveData<State> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, RepositoryData>
    ) {
        updateState(State.LOADING)
        GlobalScope.launch(Dispatchers.Default) {
            try {
                val result = gitApi.getAndroidRepositoriesStarSortedDesc(page = 1).items
                withContext(Dispatchers.Main) {
                    updateState(State.DONE)
                    callback.onResult(result, null, 2)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    updateState(State.ERROR)
                }

                // wait and retry
                delay(retryDelay)
                loadInitial(params, callback)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, RepositoryData>) {
        updateState(State.LOADING)
        GlobalScope.launch(Dispatchers.Default) {
            try {
                val result = gitApi.getAndroidRepositoriesStarSortedDesc(page = params.key).items
                withContext(Dispatchers.Main) {
                    updateState(State.DONE)
                    callback.onResult(result, params.key + 1)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    updateState(State.ERROR)
                }

                // wait and retry
                delay(retryDelay)
                loadAfter(params, callback)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, RepositoryData>) {}

    private fun updateState(state: State) {
        this.state.postValue(state)
    }
}