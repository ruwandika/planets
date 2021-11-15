package com.ruwa.planets.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class PlanetResults(
    @SerializedName("results") val results: List<Planet>,
    @SerializedName("next") val nextPage: String

)

@Parcelize
data class Planet(

    @SerializedName("name") val name: String,
    @SerializedName("climate") val climate: String,
    @SerializedName("orbital_period") val orbitalPeriod: String,
    @SerializedName("gravity") val gravity: String,
    var planetImageUrl: String
) : Parcelable
