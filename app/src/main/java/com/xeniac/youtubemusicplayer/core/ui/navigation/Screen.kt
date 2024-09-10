package com.xeniac.youtubemusicplayer.core.ui.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@kotlinx.serialization.Serializable
sealed class Screen : Parcelable {

    @kotlinx.serialization.Serializable
    data object YouTubePlayerScreen : Screen()
}