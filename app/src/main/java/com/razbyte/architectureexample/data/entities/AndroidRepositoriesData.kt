package com.razbyte.architectureexample.data.entities

import com.google.gson.annotations.SerializedName

class AndroidRepositoriesData(

    @SerializedName("total_count") val count: Int,
    @SerializedName("items") val items: List<RepositoryData>

)
