package ru.aleksandrorlove.appname

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.DrawableRes


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

    //For Movie Details
    val description: String?,
    @DrawableRes
    val photo01: Int,
    @DrawableRes
    val photo02: Int,
    @DrawableRes
    val photo03: Int,
    @DrawableRes
    val photo04: Int,

    val actor01: String?,
    val actor02: String?,
    val actor03: String?,
    val actor04: String?
) : Parcelable {

    companion object {
        val CREATOR = object : Parcelable.Creator<Movie> {
            override fun createFromParcel(parcel: Parcel) = Movie(parcel)
            override fun newArray(size: Int) = arrayOfNulls<Movie>(size)
        }
    }

    private constructor(parcel: Parcel) : this(
        cover = parcel.readInt(),
        background = parcel.readInt(),
        RARS = parcel.readString(),
        like = parcel.readByte() != 0.toByte(),
        tag = parcel.readString(),
        stars = parcel.readInt(),
        reviews = parcel.readString(),
        title = parcel.readString(),
        longMovie = parcel.readString(),
        description = parcel.readString(),
        photo01 = parcel.readInt(),
        photo02 = parcel.readInt(),
        photo03 = parcel.readInt(),
        photo04 = parcel.readInt(),
        actor01 = parcel.readString(),
        actor02 = parcel.readString(),
        actor03 = parcel.readString(),
        actor04 = parcel.readString()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(cover)
        parcel.writeInt(background)
        parcel.writeString(RARS)
        parcel.writeByte(if (like) 1 else 0)
        parcel.writeString(tag)
        parcel.writeInt(stars)
        parcel.writeString(reviews)
        parcel.writeString(title)
        parcel.writeString(longMovie)
        parcel.writeString(description)
        parcel.writeInt(photo01)
        parcel.writeInt(photo02)
        parcel.writeInt(photo03)
        parcel.writeInt(photo04)
        parcel.writeString(actor01)
        parcel.writeString(actor02)
        parcel.writeString(actor03)
        parcel.writeString(actor04)
    }
}