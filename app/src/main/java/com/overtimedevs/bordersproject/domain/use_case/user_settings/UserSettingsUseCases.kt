package com.overtimedevs.bordersproject.domain.use_case.user_settings

import javax.inject.Inject

data class UserSettingsUseCases @Inject constructor(
    val getUserSettings: GetUserSettings,
    val saveUserSettings: SaveUserSettings
)