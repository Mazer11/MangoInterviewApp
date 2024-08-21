package org.interview.profile.ui.screen.edit.event

import android.content.Context
import org.interview.profile.models.UpdateProfileData

sealed interface EditProfileScreenEvent {

    data class SendEditProfileScreenData(
        val data: UpdateProfileData,
        val context: Context
    ) : EditProfileScreenEvent

    data object NavBack : EditProfileScreenEvent

}