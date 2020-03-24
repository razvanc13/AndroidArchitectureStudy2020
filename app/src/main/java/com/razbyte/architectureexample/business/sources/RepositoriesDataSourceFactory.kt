package com.razbyte.architectureexample.business.sources

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.razbyte.architectureexample.data.entities.RepositoryData

class RepositoriesDataSourceFactory :
    DataSource.Factory<Int, RepositoryData>() {

    val sourceLiveData = MutableLiveData<RepositoriesDataSource>()

    override fun create(): DataSource<Int, RepositoryData> {
        val latestSource = RepositoriesDataSource()
        sourceLiveData.postValue(latestSource)
        return latestSource
    }
}