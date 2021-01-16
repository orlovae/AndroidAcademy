package ru.aleksandrorlove.appname.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Configuration(
	@SerialName("images")
	val images: Images
)

@Serializable
data class Images(
	@SerialName("base_url")
	val baseUrlImages: String,

	@SerialName("backdrop_sizes")
	val backdropSizes: List<String>,

	@SerialName("logo_sizes")
	val logoSizes: List<String>,

	@SerialName("poster_sizes")
	val posterSizes: List<String>,

	@SerialName("profile_sizes")
	val profileSizes: List<String>,

	@SerialName("still_sizes")
	val stillSizes: List<String>
)

