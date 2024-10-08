package net.nomia.common.ui.composable

import android.telephony.PhoneNumberUtils.isNonSeparator
import android.text.Selection
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.interview.commonui.R
import org.interview.commonui.extensions.clearFocusOnKeyboardDismiss
import org.interview.commonui.theme.MangoInterviewAppTheme
import org.interview.commonui.utils.PhoneHelper

@Composable
fun AppOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onCancel: () -> Unit = { onValueChange("") },
    enabled: Boolean = true,
    showKeyboard: Boolean = false,
    isFocused: MutableState<Boolean> = remember { mutableStateOf(false) },
    focusRequester: FocusRequester = remember { FocusRequester() },
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = defaultTrailingContent(
        isError = isError,
        onClick = onCancel,
        value = value,
        enabled = enabled,
        isFocused = isFocused.value
    ),
    supportingText: @Composable (() -> Unit)? = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.extraSmall,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
) {
    if (showKeyboard) {
        LaunchedEffect(Unit) { focusRequester.requestFocus() }
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                isFocused.value = focusState.isFocused
            }
            .clearFocusOnKeyboardDismiss(),
        enabled = enabled,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        supportingText = supportingText,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
    )
}

@Composable
fun defaultTrailingContent(
    isError: Boolean,
    onClick: () -> Unit,
    value: String,
    enabled: Boolean,
    isFocused: Boolean,
    trailingIcon: @Composable () -> Unit = {
        IconButton(onClick = onClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_cancel_24),
                contentDescription = null
            )
        }
    }
): @Composable (() -> Unit)? =
    when {
        isError && !isFocused -> {
            {
                Icon(
                    painter = painterResource(id = R.drawable.ic_error_24),
                    contentDescription = null
                )

            }
        }
        value.isNotEmpty() && enabled -> {
            { trailingIcon() }
        }
        else -> null
    }

object PhoneNumberTransformation : VisualTransformation {
    private val phoneNumberFormatter = PhoneHelper.phoneNumberTypeFormatter

    override fun filter(text: AnnotatedString): TransformedText {
        val transformation = reformat(text, Selection.getSelectionEnd(text))

        return TransformedText(AnnotatedString(transformation.formatted ?: ""), object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return transformation.originalToTransformed[offset]
            }

            override fun transformedToOriginal(offset: Int): Int {
                return transformation.transformedToOriginal[offset]
            }
        })
    }

    private fun reformat(phoneNumberCharSequence: CharSequence, cursor: Int): Transformation {
        val currentCursorIndex = cursor - 1
        var formatted: String? = null
        var lastNonSeparator = 0.toChar()
        var hasCursor = false

        phoneNumberFormatter.clear()
        phoneNumberCharSequence.forEachIndexed { index, char ->
            if (isNonSeparator(char)) {
                if (lastNonSeparator.code != 0) {
                    formatted = getFormattedNumber(lastNonSeparator, hasCursor)
                    hasCursor = false
                }
                lastNonSeparator = char
            }
            if (index == currentCursorIndex) {
                hasCursor = true
            }
        }

        if (lastNonSeparator.code != 0) {
            formatted = getFormattedNumber(lastNonSeparator, hasCursor)
        }

        val originalToTransformed = mutableListOf<Int>()
        val transformedToOriginal = mutableListOf<Int>()
        var specialCharsCount = 0

        formatted?.forEachIndexed { index, char ->
            if (isSeparator(char)) {
                specialCharsCount++
            } else {
                originalToTransformed.add(index)
            }
            transformedToOriginal.add(index - specialCharsCount)
        }

        originalToTransformed.add(originalToTransformed.maxOrNull()?.plus(1) ?: 0)
        transformedToOriginal.add(transformedToOriginal.maxOrNull()?.plus(1) ?: 0)

        return Transformation(formatted, originalToTransformed, transformedToOriginal)
    }

    private fun getFormattedNumber(lastNonSeparator: Char, hasCursor: Boolean): String? =
        with(phoneNumberFormatter) {
            if (hasCursor)
                inputDigitAndRememberPosition(lastNonSeparator)
            else
                inputDigit(lastNonSeparator)

        }

    private fun isSeparator(char: Char): Boolean =
        !isNonSeparator(char)

    private data class Transformation(
        val formatted: String?,
        val originalToTransformed: List<Int>,
        val transformedToOriginal: List<Int>
    )
}

@Preview
@Composable
private fun NomiaOutlinedTextFieldPreview() {
    MangoInterviewAppTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AppOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = "Enabled",
                onValueChange = {},
                label = { Text(text = "Label") },
            )
            AppOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {},
                placeholder = { Text(text = "Placeholder") }
            )

            AppOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = "Error",
                onValueChange = {},
                label = { Text(text = "Label") },
                supportingText = { Text(text = "An error occurs") },
                isError = true
            )
            AppOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {},
                placeholder = { Text(text = "Disabled") },
                enabled = false
            )
        }
    }
}
