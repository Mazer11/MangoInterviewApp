package org.interview.login.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.interview.login.models.RegistrationData
import org.interview.login.ui.screen.registration.event.RegistrationScreenEvents
import org.interview.login.ui.screen.registration.state.RegistrationScreenState
import org.interview.login.usecase.RegistrationUseCase
import org.interview.remote.models.Response
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase
) : ViewModel() {

    private val _screenState =
        MutableStateFlow<RegistrationScreenState>(RegistrationScreenState.Form())
    val screenState: StateFlow<RegistrationScreenState> = _screenState.asStateFlow()

    fun onEvent(event: RegistrationScreenEvents) {
        when (event) {
            is RegistrationScreenEvents.SendRegistrationData -> onSendRegistrationData(event.data)
        }
    }

    private fun onSendRegistrationData(data: RegistrationData) {
        viewModelScope.launch {
            _screenState.value = RegistrationScreenState.Loading

            registrationUseCase(data).collectLatest { result ->
                if (result is Response.Success) {
                    //Logged successfully
                    _screenState.value = RegistrationScreenState.Logged
                } else {
                    //Show error
                    _screenState.value = RegistrationScreenState.Form(
                        errorMessage = (result as Response.Error).message.msg
                    )
                }
            }
        }
    }

}