package org.interview.profile.ui.screen.profile

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import org.interview.commonui.composable.CircularLoading
import org.interview.commonui.theme.paddings
import org.interview.profile.R
import org.interview.profile.ui.screen.profile.event.ProfileScreenEvent
import org.interview.profile.ui.screen.profile.state.ProfileScreenState
import org.interview.utils.extensions.dateToZodiac
import org.interview.utils.extensions.toShortDate

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    state: ProfileScreenState,
    onEvent: (ProfileScreenEvent) -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        modifier = modifier,
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Navigate Back",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.clickable { onEvent(ProfileScreenEvent.NavBack) }
                    )

                    Text(
                        text = stringResource(id = R.string.profile),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(horizontal = MaterialTheme.paddings.medium)
                    )
                }

                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit profile",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.clickable { onEvent(ProfileScreenEvent.EditProfile) }
                )
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
                is ProfileScreenState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            modifier = Modifier.padding(top = MaterialTheme.paddings.medium),
                            text = target.message,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                is ProfileScreenState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularLoading()
                    }
                }

                is ProfileScreenState.Ready -> {

                    LazyColumn(
                        modifier = Modifier
                            .padding(MaterialTheme.paddings.medium)
                            .fillMaxWidth()
                    ) {
                        //Avatar + name + username
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .padding(MaterialTheme.paddings.medium)
                                        .size(92.dp)
                                        .background(
                                            MaterialTheme.colorScheme.surfaceVariant,
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = rememberAsyncImagePainter(
                                            model = ImageRequest.Builder(context)
                                                .addHeader("Authorization", "Bearer ${target.tokenImage}")
                                                .data("https://plannerok.ru/" + target.profileData.avatar)
                                                .crossfade(true)
                                                .build(),
                                            contentScale = ContentScale.Crop
                                        ),
                                        contentDescription = "Avatar",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.clip(CircleShape)
                                    )
                                }

                                Column(modifier = Modifier.padding(start = MaterialTheme.paddings.small)) {
                                    Text(
                                        text = target.profileData.username,
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )

                                    Text(
                                        text = target.profileData.name +
                                                if (target.profileData.birthday != null)
                                                    " / " + target.profileData.birthday.dateToZodiac()?.zodiacName
                                                else "",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(top = MaterialTheme.paddings.small)
                                    )
                                }
                            }
                        }

                        //Birthday
                        if (target.profileData.birthday != null)
                            item {
                                Text(
                                    text = "Дата рождения: "
                                            + (target.profileData.birthday?.toShortDate() ?: ""),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.padding(top = MaterialTheme.paddings.medium)
                                )
                            }

                        //Phone
                        item {
                            Text(
                                text = "Телефон: " + target.profileData.phone,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(top = MaterialTheme.paddings.medium)
                            )
                        }

                        //Address
                        if (target.profileData.city != null)
                            item {
                                Text(
                                    text = "Город: " + target.profileData.city,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.padding(top = MaterialTheme.paddings.medium)
                                )
                            }

                        if (target.profileData.status != null)
                            item {
                                Text(
                                    text = "Обо мне",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(top = MaterialTheme.paddings.extraLarge)
                                )
                                Text(
                                    text = target.profileData.status,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.padding(top = MaterialTheme.paddings.medium)
                                )
                            }

                    }
                }

            }
        }
    }
}