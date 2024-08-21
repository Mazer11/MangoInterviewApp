package org.interview.login.ui.screen.registration.state

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import net.nomia.common.ui.composable.AppOutlinedTextField
import net.nomia.common.ui.composable.PhoneNumberTransformation
import org.interview.commonui.composable.FilledButton
import org.interview.commonui.theme.MangoInterviewAppTheme
import org.interview.commonui.theme.paddings
import org.interview.login.R
import org.interview.login.models.RegistrationData

@Composable
fun RegistrationFormPage(
    modifier: Modifier = Modifier,
    formData: MutableState<RegistrationData>,
    errorMessage: String? = null,
    onSendData: (RegistrationData) -> Unit,
    onNavBack: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val isUsernameValid = remember {
        derivedStateOf {
            formData.value.username.isValidUsername()
        }
    }
    val isNameValid = remember {
        derivedStateOf {
            formData.value.name.isValidName()
        }
    }
    val isSendEnabled = remember {
        derivedStateOf {
            formData.value.username.isNotEmpty() &&
                    isUsernameValid.value &&
                    formData.value.name.isNotEmpty() &&
                    isNameValid.value
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {

            item {
                Row {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Navigate Back",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.clickable { onNavBack() }
                    )

                    Text(
                        text = stringResource(id = R.string.registration),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = MaterialTheme.paddings.medium)
                    )
                }
                Text(
                    text = stringResource(id = R.string.registration_message),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = MaterialTheme.paddings.medium)
                )
            }

            //Phone
            item {
                AppOutlinedTextField(
                    modifier = Modifier
                        .padding(top = MaterialTheme.paddings.large)
                        .fillMaxWidth(),
                    value = formData.value.phone,
                    onValueChange = {},
                    enabled = false,
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.phone),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    supportingText = {},
                    visualTransformation = PhoneNumberTransformation
                )
            }

            //Name
            item {
                AppOutlinedTextField(
                    modifier = Modifier
                        .padding(top = MaterialTheme.paddings.small)
                        .fillMaxWidth(),
                    value = formData.value.name,
                    onValueChange = {
                        formData.value = formData.value.copy(name = it)
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.enter_name),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Next) }
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    showKeyboard = true,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    isError = isNameValid.value.not() && formData.value.name.isNotEmpty(),
                    supportingText = {
                        val color = animateColorAsState(
                            targetValue = if (isNameValid.value.not())
                                MaterialTheme.colorScheme.error
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant,
                            label = ""
                        )

                        AnimatedVisibility(
                            visible = isNameValid.value.not() && formData.value.name.isNotEmpty()
                        ) {
                            Text(
                                text = stringResource(id = R.string.name_format_text),
                                color = color.value,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                )
            }

            //Username
            item {
                AppOutlinedTextField(
                    modifier = Modifier
                        .padding(top = MaterialTheme.paddings.small)
                        .fillMaxWidth(),
                    value = formData.value.username,
                    onValueChange = {
                        formData.value = formData.value.copy(username = it)
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.enter_username),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    supportingText = {
                        val color = animateColorAsState(
                            targetValue = if (isNameValid.value.not())
                                MaterialTheme.colorScheme.error
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant,
                            label = ""
                        )

                        Column {
                            AnimatedVisibility(
                                visible = isUsernameValid.value.not() && formData.value.username.isNotEmpty()
                            ) {
                                Text(
                                    text = stringResource(id = R.string.username_format_hint),
                                    color = color.value,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }

                            Text(
                                text = stringResource(id = R.string.this_is_how_others_will_see),
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    },
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            onSendData(formData.value)
                        }
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    textStyle = MaterialTheme.typography.bodyMedium,
                    isError = isUsernameValid.value.not() && formData.value.username.isNotEmpty()
                )
            }

        }

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            AnimatedVisibility(
                visible = errorMessage != null,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = errorMessage ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            //Send Button
            FilledButton(
                onClick = { onSendData(formData.value) },
                enabled = isSendEnabled.value,
                modifier = Modifier
                    .padding(MaterialTheme.paddings.medium)
                    .fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.continue_action))
            }
        }
    }

}

private fun String.isValidUsername(): Boolean {
    val regex = Regex("^[A-Za-z0-9-_]+$")
    return this.matches(regex)
}

private fun String.isValidName(): Boolean {
    val regex = Regex("^[A-Za-z]+\\s+[A-Za-z]+$")
    return this.matches(regex)
}

@Preview
@Composable
private fun RegistrationFormPage_EmptyPreview() {
    MangoInterviewAppTheme {
        RegistrationFormPage(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(MaterialTheme.paddings.medium)
                .fillMaxSize(),
            formData = remember { mutableStateOf(RegistrationData("89777777777", "", "")) },
            onSendData = {},
            onNavBack = {}
        )
    }
}

@Preview
@Composable
private fun RegistrationFormPage_FilledPreview() {
    MangoInterviewAppTheme {
        RegistrationFormPage(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(MaterialTheme.paddings.medium)
                .fillMaxSize(),
            formData = remember {
                mutableStateOf(
                    RegistrationData(
                        "89777777777",
                        "Name Surname",
                        "user_nickname"
                    )
                )
            },
            onSendData = {},
            onNavBack = {}
        )
    }
}