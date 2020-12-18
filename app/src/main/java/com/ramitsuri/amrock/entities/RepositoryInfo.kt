package com.ramitsuri.amrock.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RepositoryInfo(
    val name: String,
    val date: String,
    val description: String,
    val url: String = "https://github.com/QuickenLoans/Behaviors.js"
) : Parcelable