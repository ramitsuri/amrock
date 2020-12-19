package com.ramitsuri.amrock.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RepositoryInfo(
    @field:SerializedName("name") val name: String,
    @field:SerializedName("updated_at") val date: String,
    @field:SerializedName("description") val description: String,
    @field:SerializedName("html_url") val url: String
) : Parcelable