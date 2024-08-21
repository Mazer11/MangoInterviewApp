package org.interview.login.models

data class CheckAuthResult(
    val userId: Int,
    val isUserExists: Boolean
)
