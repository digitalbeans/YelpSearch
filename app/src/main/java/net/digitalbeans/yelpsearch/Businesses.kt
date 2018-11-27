package net.digitalbeans.yelpsearch

import com.google.gson.annotations.SerializedName

data class Businesses (
    val total: Long,
    val businesses: List<Business>,
    val region: Region
)

data class Business (
    val rating: Double,
    val price: String,
    val phone: String,
    val id: String,
    val alias: String,

    @SerializedName("is_closed")
    val isClosed: Boolean,

    val categories: List<Category>,

    @SerializedName("review_count")
    val reviewCount: Long,

    val name: String,
    val url: String,
    val coordinates: Center,

    @SerializedName("image_url")
    val imageURL: String,

    val location: Location,
    val distance: Double,
    val transactions: List<String>
)

//data class Category (
//    val alias: String,
//    val title: String
//)

data class Center (
    val latitude: Double,
    val longitude: Double
)

data class Location (
    val city: String,
    val country: String,
    val address2: String,
    val address3: String,
    val state: String,
    val address1: String,

    @SerializedName("zip_code")
    val zipCode: String
) {
    var description: String = ""
        get() {

        var descriptionString: String = ""
        if (address1 !=null && address1.isNotBlank()) {
            descriptionString += address1
        }
        if (address2 !=null && address2.isNotBlank()) {
            descriptionString +=  ", " + address2
        }
        if (city != null && city.isNotBlank()) {
            descriptionString +=  ", " + city
        }
        if (state.isNotBlank()) {
            descriptionString +=  ", " + state
        }
        if (zipCode.isNotBlank()) {
            descriptionString +=  ", " + zipCode
        }

        return descriptionString
    }

}

data class Region (
    val center: Center
)