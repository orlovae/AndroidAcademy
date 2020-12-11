package ru.aleksandrorlove.appname

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    @DrawableRes
    val cover: Int,
    @DrawableRes
    val background: Int,
    val RARS: String?,
    val like: Boolean,
    val tag: String?,
    val stars: Int,
    val reviews: String?,
    val title: String?,
    val longMovie: String?,
    val description: String?,
    val actors: ArrayList<Actor>
) : Parcelable