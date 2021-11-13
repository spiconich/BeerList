package space.spitsa.beerlist

import com.google.gson.annotations.SerializedName

class Beer {
    val id: Int? = null
    val name: String? = null
    val tagline: String? = null
    @SerializedName("first_brewed")
    val firstBrewed: String? = null
    val description: String? = null
    @SerializedName("image_url")
    val imageUrl: String? = null
    val abv: Float? = null
    val ibu: Float? =null
    @SerializedName("target_fg")
    val targetFg: Int? = null
    @SerializedName("target_og")
    val targetOg: Float? = null
    val ebc: Int? = null
    val srm: Float? = null
    val ph: Float? = null
    @SerializedName("attenuation_level")
    val attenuationLevel: Float? = null
}