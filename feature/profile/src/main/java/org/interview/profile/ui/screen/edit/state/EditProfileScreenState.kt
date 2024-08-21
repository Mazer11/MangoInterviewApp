package org.interview.profile.ui.screen.edit.state

import org.interview.profile.models.UpdateProfileData

sealed interface EditProfileScreenState {

    data object Loading: EditProfileScreenState

    data class Ready(val data: UpdateProfileData, val errorMessage: String? = null) : EditProfileScreenState

    data object Updated : EditProfileScreenState

}