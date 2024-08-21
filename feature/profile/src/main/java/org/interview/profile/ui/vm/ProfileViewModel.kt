package org.interview.profile.ui.vm

import android.content.Context
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.interview.profile.models.UpdateProfileData
import org.interview.profile.models.toProfile
import org.interview.profile.models.toUpdateProfileData
import org.interview.profile.ui.screen.edit.event.EditProfileScreenEvent
import org.interview.profile.ui.screen.edit.state.EditProfileScreenState
import org.interview.profile.ui.screen.profile.event.ProfileScreenEvent
import org.interview.profile.ui.screen.profile.state.ProfileScreenState
import org.interview.profile.usecase.GetProfileDataUseCase
import org.interview.profile.usecase.UpdateProfileUseCase
import org.interview.remote.models.Response
import org.interview.remote.repository.settings.SettingRepository
import javax.inject.Inject
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileDataUseCase: GetProfileDataUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val settingRepository: SettingRepository
) : ViewModel() {

    private val _profileState = MutableStateFlow<ProfileScreenState>(ProfileScreenState.Loading)
    val profileState: StateFlow<ProfileScreenState> = _profileState.asStateFlow()

    private val _editProfileState =
        MutableStateFlow<EditProfileScreenState>(EditProfileScreenState.Loading)
    val editProfileState: StateFlow<EditProfileScreenState> = _editProfileState.asStateFlow()

    init {
        getProfileData()
    }

    fun onProfileEvent(event: ProfileScreenEvent) {
        when (event) {
            is ProfileScreenEvent.EditProfile -> {}
            is ProfileScreenEvent.NavBack -> {}
            is ProfileScreenEvent.LogOut -> {
                //clear data
            }
        }
    }

    fun onEditProfileEvent(event: EditProfileScreenEvent) {
        when (event) {
            is EditProfileScreenEvent.NavBack -> {
                getProfileData()
            }

            is EditProfileScreenEvent.SendEditProfileScreenData -> {
                updateProfile(event.data, event.context)
            }
        }
    }

    private fun getProfileData() {
        viewModelScope.launch {
            getProfileDataUseCase().collectLatest { result ->
                _profileState.value = ProfileScreenState.Loading

                if (result is Response.Success) {
                    _profileState.value = ProfileScreenState.Ready(
                        profileData = result.result.toProfile(),
                        tokenImage = settingRepository.getAccessToken().toString()
                    )
                    _editProfileState.value =
                        EditProfileScreenState.Ready(result.result.toUpdateProfileData())
                } else
                    _profileState.value =
                        ProfileScreenState.Error(message = (result as Response.Error).message.msg)
            }
        }
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun updateProfile(data: UpdateProfileData, context: Context) {
        viewModelScope.launch {
            val request = if (data.avatar != null) {
                val newPhotoBytes = context.contentResolver.openInputStream(data.avatar.toUri())
                    .use { it?.readBytes() }
                val base64Photo =
                    if (newPhotoBytes != null) Base64.encode(source = newPhotoBytes) else ""
                data.copy(
                    avatar = base64Photo,
                    birthday = if ((data.birthday ?: "").length > 10)
                        data.birthday?.dropLast(1)
                    else
                        data.birthday
                )
            } else {
                data.copy(
                    birthday = if ((data.birthday ?: "").length > 10)
                        data.birthday?.dropLast(1)
                    else
                        data.birthday
                )
            }

            updateProfileUseCase(request).collectLatest { result ->
                _profileState.value = ProfileScreenState.Loading
                _editProfileState.value = EditProfileScreenState.Loading

                if (result is Response.Success) {
                    getProfileData()
                    _editProfileState.value = EditProfileScreenState.Updated
                } else
                    _editProfileState.value = EditProfileScreenState.Ready(
                        data = data,
                        errorMessage = "Ошибка во время отправки данных. Попробуйте позже"
                    )
            }
        }
    }

}