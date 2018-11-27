package net.digitalbeans.yelpsearch

import com.google.gson.annotations.SerializedName

class UsersList {
    @SerializedName("items")
    var users: List<Users>? = null
}