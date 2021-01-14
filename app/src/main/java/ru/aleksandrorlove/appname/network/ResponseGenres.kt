package ru.aleksandrorlove.appname.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGenres(
	@SerialName("genres")
	val genres: List<Genre>
)

@Serializable
data class Genre(
	@SerialName("id")
	val id: Int? = null,

	@SerialName("name")
	val name: String? = null
)

