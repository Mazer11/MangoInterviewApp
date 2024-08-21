package org.interview.chat.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
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
import androidx.compose.ui.text.input.ImeAction
import net.nomia.common.ui.composable.AppOutlinedTextField
import org.interview.chat.R
import org.interview.commonui.composable.FilledButton

@Composable
fun ChatBottomBar(
    modifier: Modifier = Modifier,
    onSendMessage: (String) -> Unit
) {
    val message = remember { mutableStateOf("") }
    val isSendEnabled = remember { derivedStateOf { message.value.isNotEmpty() } }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        //Text
        AppOutlinedTextField(
            modifier = Modifier.weight(8f),
            value = message.value,
            onValueChange = { message.value = it },
            textStyle = MaterialTheme.typography.bodyLarge,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.enter_message),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    onSendMessage(message.value)
                    message.value = ""
                }
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )

        Spacer(modifier = Modifier.weight(1f))

        //Button
        FilledButton(
            modifier = Modifier.weight(2f),
            onClick = {
                onSendMessage(message.value)
                message.value = ""
            },
            enabled = isSendEnabled.value
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = "Send message"
            )
        }
    }

}