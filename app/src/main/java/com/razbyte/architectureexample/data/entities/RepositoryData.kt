package com.razbyte.architectureexample.data.entities

import com.google.gson.annotations.SerializedName

data class RepositoryData(

    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("stargazers_count") val stargazers: Int,
    @SerializedName("watchers_count") val watchers: Int,
    @SerializedName("forks") val forks: Int,
    @SerializedName("language") val language: String,
    @SerializedName("open_issues") val issues: Int,
    @SerializedName("default_branch") val defaultBranch: String


)