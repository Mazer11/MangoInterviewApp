package org.interview.login.ui.screen.auth.state

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import net.nomia.common.ui.composable.AppOutlinedTextField
import net.nomia.common.ui.composable.PhoneNumberTransformation
import org.interview.commonui.composable.FilledButton
import org.interview.commonui.theme.MangoInterviewAppTheme
import org.interview.commonui.theme.paddings
import org.interview.login.R

@Composable
fun AuthPhonePage(
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
    onSendCode: (String) -> Unit,
    onRegistration: (String?) -> Unit
) {
    val phone = remember { mutableStateOf("") }
    val isPhoneValid = remember { derivedStateOf { phone.value.isValidPhoneNumber() } }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column {
            Text(
                text = stringResource(id = R.string.auth),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = stringResource(id = R.string.enter_phone_message),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = MaterialTheme.paddings.medium)
            )
        }


        AppOutlinedTextField(
            modifier = Modifier
                .padding(top = MaterialTheme.paddings.large)
                .fillMaxWidth(),
            value = phone.value,
            onValueChange = { phone.value = it },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.phone),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            visualTransformation = PhoneNumberTransformation,
            supportingText = {
                if (isPhoneValid.value.not() && phone.value.isNotEmpty())
                    Text(
                        text = stringResource(id = R.string.wrong_phone_format),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    onSendCode(phone.value)
                }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Phone
            ),
            textStyle = MaterialTheme.typography.bodyMedium,
            isError = isPhoneValid.value.not() && phone.value.isNotEmpty(),
            showKeyboard = true
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = errorMessage != null,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorMessage ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )
            }

            //SendButton
            FilledButton(
                onClick = { onSendCode(phone.value) },
                enabled = isPhoneValid.value,
                modifier = Modifier
                    .padding(MaterialTheme.paddings.medium)
                    .fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.get_code))
            }

            //Registration
            Text(
                text = stringResource(id = R.string.registration),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .padding(top = MaterialTheme.paddings.medium)
                    .clickable { onRegistration(phone.value) }
            )

        }
    }

}

private fun String.isValidPhoneNumber(): Boolean {
    val regex = Regex("^\\+\\d{10,15}$")
    val text = if (this.startsWith("+")) this else "+$this"
    return text.matches(regex)
}

@Preview
@Composable
private fun AuthPhonePagePreview() {
    MangoInterviewAppTheme {
        AuthPhonePage(
            onRegistration = {},
            onSendCode = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(MaterialTheme.paddings.medium)
                .fillMaxSize()
        )
    }
}