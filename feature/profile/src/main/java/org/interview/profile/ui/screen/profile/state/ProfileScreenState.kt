package org.interview.profile.ui.screen.profile.state

import org.interview.profile.models.ProfileData

sealed interface ProfileScreenState {

    data object Loading : ProfileScreenState

    data class Ready(val profileData: ProfileData, val tokenImage: String? = null): ProfileScreenState

    data class Error(val message: String): ProfileScreenState

}