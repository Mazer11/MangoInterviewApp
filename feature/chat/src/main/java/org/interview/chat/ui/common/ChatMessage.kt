package org.interview.chat.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.interview.commonui.theme.paddings

@Composable
fun ChatMessage(
    modifier: Modifier = Modifier,
    message: String,
    isMine: Boolean,
    timeStamp: String
) {

    Column(
        modifier = modifier
            .background(
                color =
                if (isMine)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.secondary,
                shape = MaterialTheme.shapes.medium
            )
            .padding(MaterialTheme.paddings.small)
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodySmall,
            color = if (isMine)
                MaterialTheme.colorScheme.onPrimary
            else
                MaterialTheme.colorScheme.onSecondary
        )

        Text(
            text = timeStamp,
            style = MaterialTheme.typography.labelMedium,
            color = if (isMine)
                MaterialTheme.colorScheme.onPrimary.copy(alpha = .8f)
            else
                MaterialTheme.colorScheme.onSecondary.copy(alpha = .8f),
            modifier = Modifier.padding(top = MaterialTheme.paddings.small)
        )
    }

}