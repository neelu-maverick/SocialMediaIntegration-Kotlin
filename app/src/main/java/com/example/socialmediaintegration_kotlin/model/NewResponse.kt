package com.example.socialmediaintegration_kotlin.model

import com.google.gson.annotations.SerializedName

data class NewResponse(
    val firstName: FirstName,
    val id: String,
    val lastName: LastName,
    val profilePicture: ProfilePicture
)
data class Handle(
    val emailAddress: String
)
data class ComLinkedinDigitalmediaMediaartifactStillImage(
    val displayAspectRatio: DisplayAspectRatio,
    val displaySize: DisplaySize,
    val mediaType: String,
    val rawCodecSpec: RawCodecSpec,
    val storageAspectRatio: StorageAspectRatio,
    val storageSize: StorageSize
)
data class Data(
    val StillImage: ComLinkedinDigitalmediaMediaartifactStillImage
)
data class DisplayAspectRatio(
    val formatted: String,
    val heightAspect: Double,
    val widthAspect: Double
)
data class DisplayImage(
    val elements: List<Element>,
    val paging: Paging
)
data class DisplaySize(
    val height: Double,
    val uom: String,
    val width: Double
)
data class Element(

    @SerializedName("handle~")
    val handle : Handle,
    val artifact: String,
    val authorizationMethod: String,
    val `data`: Data,
    val identifiers: List<Identifier>
)
data class FirstName(
    val localized: Localized,
    val preferredLocale: PreferredLocale
)
data class Identifier(
    val `file`: String,
    val identifier: String,
    val identifierExpiresInSeconds: Int,
    val identifierType: String,
    val index: Int,
    val mediaType: String
)
data class LastName(
    val localized: Localized,
    val preferredLocale: PreferredLocale
)

data class Localized(
    val en_US: String
)
data class Paging(
    val count: Int,
    val links: List<Any>,
    val start: Int
)
data class PreferredLocale(
    val country: String,
    val language: String
)
data class ProfilePicture(
    val displayImage: String,
    @SerializedName("displayImage~")
    val displayImageX: DisplayImage
)
data class RawCodecSpec(
    val name: String,
    val type: String
)
data class StorageSize(
    val height: Int,
    val width: Int
)
data class StorageAspectRatio(
    val formatted: String,
    val heightAspect: Double,
    val widthAspect: Double
)