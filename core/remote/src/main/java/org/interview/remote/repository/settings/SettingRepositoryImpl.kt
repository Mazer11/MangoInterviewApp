package org.interview.remote.repository.settings

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingRepositoryImpl @Inject constructor() : SettingRepository {
    //TODO: use keystore to encode and decode tokens
    private val _accessToken = MutableStateFlow<String?>(null)
    private val _refreshToken = MutableStateFlow<String?>(null)

    override fun saveClientTokens(accessToken: String, refreshToken: String?) {
        _accessToken.value = accessToken

        if (!refreshToken.isNullOrEmpty())
            _refreshToken.value = refreshToken
    }

    override fun getRefreshToken(): StateFlow<String?> = _refreshToken.asStateFlow()

    override fun getAccessToken(): StateFlow<String?> = _accessToken.asStateFlow()
}