package org.interview.commonui.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.interview.commonui.theme.MangoInterviewAppTheme

@Composable
fun FilledButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    contentPadding: PaddingValues = AppButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    FilledTonalButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        contentPadding = contentPadding,
        content = content
    )
}

@Composable
fun TonalButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.filledTonalButtonColors(),
    contentPadding: PaddingValues = AppButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    FilledTonalButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        contentPadding = contentPadding,
        content = content
    )
}

@Composable
fun TonalButtonWithIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.filledTonalButtonColors(),
    contentPadding: PaddingValues = AppButtonDefaults.ButtonWithIconContentPadding,
    leadingIcon: @Composable () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    FilledTonalButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        contentPadding = contentPadding,
        content = {
            leadingIcon()
            Spacer(Modifier.width(ButtonDefaults.IconSpacing))
            content()
        }
    )
}

@Composable
fun FilledIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = IconButtonDefaults.filledShape,
    colors: IconButtonColors = IconButtonDefaults.filledTonalIconButtonColors(),
    content: @Composable () -> Unit
) {
    FilledTonalIconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        content = content
    )
}

object AppButtonDefaults {
    private val buttonHorizontalPadding = 24.dp
    private val buttonVerticalPadding = 10.dp
    private val buttonWithIconHorizontalStartPadding = 16.dp

    val ContentPadding =
        PaddingValues(
            start = buttonHorizontalPadding,
            top = buttonVerticalPadding,
            end = buttonHorizontalPadding,
            bottom = buttonVerticalPadding
        )

    val ButtonWithIconContentPadding =
        PaddingValues(
            start = buttonWithIconHorizontalStartPadding,
            top = buttonVerticalPadding,
            end = buttonHorizontalPadding,
            bottom = buttonVerticalPadding
        )

    @Composable
    fun pinKeyboardColors() = IconButtonDefaults.filledIconButtonColors(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Preview
@Composable
private fun NomiaFilledButtonPreview() {
    MangoInterviewAppTheme {
        Surface {
            Row {
                FilledButton(onClick = {}) {
                    Text(text = "Enabled")
                }
                Spacer(modifier = Modifier.width(8.dp))
                FilledButton(onClick = {}, enabled = false) {
                    Text(text = "Disabled")
                }
            }
        }
    }
}

@Preview
@Composable
private fun NomiaTonalButtonPreview() {
    MangoInterviewAppTheme {
        Surface {
            Row {
                TonalButton(onClick = {}) {
                    Text(text = "Enabled")
                }
                Spacer(modifier = Modifier.width(8.dp))
                TonalButton(onClick = {}, enabled = false) {
                    Text(text = "Disabled")
                }
            }
        }
    }
}
