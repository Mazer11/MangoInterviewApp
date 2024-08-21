package org.interview.login.ui.screen.auth.state

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import net.nomia.common.ui.composable.AppOutlinedTextField
import net.nomia.common.ui.composable.PhoneNumberTransformation
import org.interview.commonui.composable.FilledButton
import org.interview.commonui.theme.MangoInterviewAppTheme
import org.interview.commonui.theme.paddings
import org.interview.login.R

@Composable
fun AuthCodePage(
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
    phone: String,
    onCheckCode: (String) -> Unit,
    onNavBack: () -> Unit
) {
    val code = remember { mutableStateOf("") }
    val maxCodeLength = 6
    val isCodeValid = remember {
        derivedStateOf {
            code.value.length == maxCodeLength
        }
    }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column {
            Row {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Navigate Back",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.clickable { onNavBack() }
                )

                Text(
                    text = stringResource(id = R.string.auth),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(start = MaterialTheme.paddings.medium)
                )
            }

            Text(
                text = stringResource(R.string.enter_code_message, phone),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = MaterialTheme.paddings.medium)
            )
        }


        AppOutlinedTextField(
            modifier = Modifier
                .padding(top = MaterialTheme.paddings.large)
                .fillMaxWidth(),
            value = code.value,
            onValueChange = {
                if (it.length <= maxCodeLength)
                    code.value = it
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.code),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    onCheckCode(code.value)
                }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Phone
            ),
            textStyle = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center),
            isError = errorMessage != null && isCodeValid.value,
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
                    text = errorMessage ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            //SendButton
            FilledButton(
                onClick = { onCheckCode(code.value) },
                enabled = isCodeValid.value,
                modifier = Modifier
                    .padding(MaterialTheme.paddings.medium)
                    .fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.send_code))
            }

        }
    }

}

@Preview
@Composable
private fun AuthPhonePagePreview() {
    MangoInterviewAppTheme {
        AuthCodePage(
            onCheckCode = {},
            phone = "+79777777777",
            onNavBack = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(MaterialTheme.paddings.medium)
                .fillMaxSize()
        )
    }
}