package com.razbyte.architectureexample.business.providers

import com.razbyte.architectureexample.data.entities.AndroidRepositoriesData
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitRawApi {

    companion object {
        const val NAME = "git_raw_api_provider"
        const val BASE_URL = "https://raw.githubusercontent.com/"
    }

    @GET("{full_name}/{main_branch}/README.md")
    suspend fun getReadmeRaw(
        @Path(value = "full_name", encoded = true) fullName: String,
        @Path(value = "main_branch", encoded = true) mainBranch: String
    ): ResponseBody

}
