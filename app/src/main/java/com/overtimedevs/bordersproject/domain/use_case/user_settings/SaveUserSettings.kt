package com.overtimedevs.bordersproject.domain.use_case.user_settings

import com.overtimedevs.bordersproject.data.repository.UserRepository
import com.overtimedevs.bordersproject.domain.model.UserSettings
import javax.inject.Inject

class SaveUserSettings @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(userSettings: UserSettings) {
        userRepository.saveUserSettings(userSettings)
    }
}