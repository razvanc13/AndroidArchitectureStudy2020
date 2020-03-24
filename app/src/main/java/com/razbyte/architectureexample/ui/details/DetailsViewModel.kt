package com.razbyte.architectureexample.ui.details

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.razbyte.architectureexample.base.BaseViewModel
import com.razbyte.architectureexample.business.providers.GitRawApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

class DetailsViewModel(application: Application) : BaseViewModel(application), KoinComponent {

    // region Observables

    val readme = MutableLiveData<String>().apply { value = "" }
    val error = MutableLiveData<Boolean>().apply { value = false }
    val loading = MutableLiveData<Boolean>().apply { value = true }

    // endregion

    private val gitRawApi: GitRawApi by inject()

    fun fetchReadme(fullName: String, mainBranch: String) {
        loading.value = true
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val result = fetchReadmeFromApi(fullName, mainBranch)
                withContext(Dispatchers.Main) {
                    error.value = false
                    loading.value = false
                    readme.value = result
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    loading.value = false
                    error.value = true
                }
            }
        }
    }

    private suspend fun fetchReadmeFromApi(fullName: String, mainBranch: String): String {
        return gitRawApi.getReadmeRaw(fullName, mainBranch).string()
    }
}
