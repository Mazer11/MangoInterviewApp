package org.interview.remote.repository.settings

import kotlinx.coroutines.flow.StateFlow

interface SettingRepository {

    fun saveClientTokens(accessToken: String, refreshToken: String? = null)
    fun getRefreshToken(): StateFlow<String?>
    fun getAccessToken(): StateFlow<String?>

}