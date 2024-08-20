package org.interview.commonui.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CircularLoading(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    size: Dp = AppSpinnerDefaults.buttonSize,
    color: Color = MaterialTheme.colorScheme.onPrimary,
) {
    CircularProgressIndicator(
        modifier = Modifier
            .padding(paddingValues)
            .size(size = size)
            .then(modifier),
        strokeWidth = 3.dp,
        color = color
    )
}

object AppSpinnerDefaults {
    val buttonSize = 24.dp
    val alertDialogSize = 18.dp
    val dropdownSize = 16.dp
}
