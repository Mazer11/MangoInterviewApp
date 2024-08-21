package org.interview.profile.ui.screen.edit

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import net.nomia.common.ui.composable.AppOutlinedTextField
import org.interview.commonui.composable.CircularLoading
import org.interview.commonui.composable.DatePickerDialog
import org.interview.commonui.composable.FilledButton
import org.interview.commonui.theme.paddings
import org.interview.profile.R
import org.interview.profile.models.UpdateProfileData
import org.interview.profile.ui.screen.edit.event.EditProfileScreenEvent
import org.interview.profile.ui.screen.edit.state.EditProfileScreenState
import org.interview.utils.extensions.toShortDate
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    modifier: Modifier = Modifier,
    state: EditProfileScreenState,
    onEvent: (EditProfileScreenEvent) -> Unit
) {
    val context = LocalContext.current

    val formData = remember { mutableStateOf(UpdateProfileData()) }
    Log.e("EditProfileScreen", "EditProfileScreen: ${formData.value}")

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
    val showDatePicker = remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    val imageLink = remember { mutableStateOf(formData.value.avatar?.toUri()) }
    // Лаунчер для выбора фотографии из галереии пользователя
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                imageLink.value = uri
            }
        }
    )

    if (showDatePicker.value)
        DatePickerDialog(
            state = datePickerState,
            onDismissRequest = { showDatePicker.value = false },
            onApply = {
                if (datePickerState.selectedDateMillis != null) {
                    val instant = Instant.ofEpochMilli(datePickerState.selectedDateMillis!!)
                    val formatter = DateTimeFormatter.ISO_DATE.withZone(ZoneOffset.UTC)

                    formData.value = formData.value.copy(birthday = formatter.format(instant))
                    showDatePicker.value = false
                }
            }
        )

    Scaffold(
        modifier = modifier,
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Navigate Back",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.clickable { onEvent(EditProfileScreenEvent.NavBack) }
                )

                Text(
                    text = stringResource(id = R.string.profile),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = MaterialTheme.paddings.medium)
                )
            }
        },
        bottomBar = {
            FilledButton(
                onClick = {
                    onEvent(
                        EditProfileScreenEvent.SendEditProfileScreenData(
                            data = formData.value.copy(
                                avatar = imageLink.value.toString(),
                                avatarFileName = imageLink.value?.lastPathSegment
                            ),
                            context = context
                        )
                    )
                },
                enabled = isSendEnabled.value,
                modifier = Modifier
                    .padding(MaterialTheme.paddings.medium)
                    .fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.save_changes))
            }
        }
    ) { paddingValues ->
        AnimatedContent(
            targetState = state,
            label = "ProfileScreen state animation",
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) { target ->
            when (target) {
                is EditProfileScreenState.Updated -> {
                    onEvent(EditProfileScreenEvent.NavBack)
                }

                is EditProfileScreenState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularLoading()
                    }
                }

                is EditProfileScreenState.Ready -> {

                    LaunchedEffect(key1 = true) {
                        formData.value = target.data
                    }

                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        //Avatar
                        item {
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .padding(MaterialTheme.paddings.medium)
                                    .size(128.dp)
                                    .background(
                                        MaterialTheme.colorScheme.surfaceVariant,
                                        shape = CircleShape
                                    )
                                    .clickable {
                                        imagePickerLauncher.launch(
                                            PickVisualMediaRequest(
                                                ActivityResultContracts.PickVisualMedia.ImageOnly
                                            )
                                        )
                                    }
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(
                                        model = ImageRequest.Builder(context)
                                            .data(
                                                if (imageLink.value?.toString()?.contains("jpg") == true)
                                                    "https://plannerok.ru/" + imageLink.value?.toString()
                                                else
                                                    imageLink.value?.toString()
                                            )
                                            .crossfade(true)
                                            .build(),
                                        contentScale = ContentScale.Crop
                                    ),
                                    contentDescription = "Avatar",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.clip(CircleShape)
                                )
                            }
                        }

                        //Name
                        item {
                            AppOutlinedTextField(
                                modifier = Modifier
                                    .padding(top = MaterialTheme.paddings.large)
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
                                        targetValue = if (isNameValid.value)
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
                                    onNext = { focusManager.moveFocus(FocusDirection.Next) }
                                ),
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next
                                ),
                                textStyle = MaterialTheme.typography.bodyMedium,
                                isError = isUsernameValid.value.not() && formData.value.username.isNotEmpty()
                            )
                        }

                        //City
                        item {
                            AppOutlinedTextField(
                                modifier = Modifier
                                    .padding(top = MaterialTheme.paddings.small)
                                    .fillMaxWidth(),
                                value = formData.value.city ?: "",
                                onValueChange = {
                                    formData.value = formData.value.copy(city = it)
                                },
                                placeholder = {
                                    Text(
                                        text = stringResource(id = R.string.enter_city),
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                },
                                supportingText = {},
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Next) }
                                ),
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next
                                ),
                                textStyle = MaterialTheme.typography.bodyMedium
                            )
                        }

                        //Birthday
                        item {
                            AppOutlinedTextField(
                                modifier = Modifier
                                    .padding(top = MaterialTheme.paddings.small)
                                    .fillMaxWidth()
                                    .clickable { showDatePicker.value = true },
                                value = formData.value.birthday?.toShortDate() ?: "",
                                onValueChange = {},
                                placeholder = {
                                    Text(
                                        text = stringResource(id = R.string.enter_birthday),
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                },
                                textStyle = MaterialTheme.typography.bodyMedium,
                                enabled = false
                            )
                        }

                        //Status
                        item {
                            AppOutlinedTextField(
                                modifier = Modifier
                                    .padding(top = MaterialTheme.paddings.small)
                                    .fillMaxWidth(),
                                value = formData.value.status ?: "",
                                onValueChange = {
                                    if ((formData.value.status ?: "").length <= 300)
                                        formData.value = formData.value.copy(status = it)
                                },
                                singleLine = false,
                                placeholder = {
                                    Text(
                                        text = stringResource(id = R.string.tell_about_yourself),
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                },
                                supportingText = {
                                    Text(
                                        text = "${(formData.value.status ?: "").length}/300",
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        style = MaterialTheme.typography.bodyLarge,
                                        textAlign = TextAlign.End,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                },
                                textStyle = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }

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