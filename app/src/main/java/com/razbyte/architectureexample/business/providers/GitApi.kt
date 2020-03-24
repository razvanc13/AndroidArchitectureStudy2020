package com.razbyte.architectureexample.business.providers

import com.razbyte.architectureexample.data.entities.AndroidRepositoriesData
import retrofit2.http.GET
import retrofit2.http.Query

interface GitApi {

    companion object {
        const val NAME = "git_api_provider"
        const val BASE_URL = "https://api.github.com/"
        const val PAGE_SIZE = 20
    }

    @GET("search/repositories")
    suspend fun getAndroidRepositoriesStarSortedDesc(
        @Query("q") key: String = "Android",
        @Query("sort") sortOn: String = "stars",
        @Query("order") order: String = "desc",
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = PAGE_SIZE
    ): AndroidRepositoriesData

}
