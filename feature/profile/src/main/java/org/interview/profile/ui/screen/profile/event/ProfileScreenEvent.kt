package org.interview.profile.ui.screen.profile.event

sealed interface ProfileScreenEvent {

    data object EditProfile: ProfileScreenEvent

    data object NavBack: ProfileScreenEvent

    data object LogOut: ProfileScreenEvent

}