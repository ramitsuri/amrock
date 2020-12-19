package com.ramitsuri.amrock.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.time.Instant

@Parcelize
data class RepositoryInfo(
    @field:SerializedName("name") val name: String,
    @field:SerializedName("updated_at") val date: Instant,
    @field:SerializedName("description") val description: String,
    @field:SerializedName("html_url") val url: String
) : Parcelable