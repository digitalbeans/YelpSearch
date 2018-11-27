package net.digitalbeans.yelpsearch

import com.google.gson.annotations.SerializedName

data class Categories (
    val categories: List<Category>
)

data class Category (
    val alias: String,
    val title: String,

    @SerializedName("parent_aliases")
    val parentAliases: List<String>,

    @SerializedName("country_whitelist")
    val countryWhitelist: List<Any?>,

    @SerializedName("country_blacklist")
    val countryBlacklist: List<Any?>
)
