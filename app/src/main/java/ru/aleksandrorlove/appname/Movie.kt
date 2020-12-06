package ru.aleksandrorlove.appname

import androidx.annotation.DrawableRes


data class Movie(
    @DrawableRes
    val cover: Int,
    @DrawableRes
    val background: Int,
    val RARS: String,
    val like: Boolean,
    val tag: String,
    val stars: Int,
    val reviews: String,
    val title: String,
    val longMovie: String,

    //For Movie Details
    val description: String,
    @DrawableRes
    val photo01: Int,
    @DrawableRes
    val photo02: Int,
    @DrawableRes
    val photo03: Int,
    @DrawableRes
    val photo04: Int,

    val actor01: String,
    val actor02: String,
    val actor03: String,
    val actor04: String,
)