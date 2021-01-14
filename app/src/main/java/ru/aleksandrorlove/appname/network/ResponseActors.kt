package ru.aleksandrorlove.appname.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.nio.file.Path

@Serializable
data class ResponseActors(
    @SerialName("cast")
	val actors: List<Actor>
)

@Serializable
data class Actor(
	@SerialName("id")
	val id: Int? = null,

	@SerialName("name")
	val name: String? = null,

	//TODO если поле нулевое, то подменять ссылку на ресрс, нет фото
	@SerialName("profile_path")
	val picturePath: String? = null
)

