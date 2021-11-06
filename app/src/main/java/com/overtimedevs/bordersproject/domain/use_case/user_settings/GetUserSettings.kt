package com.overtimedevs.bordersproject.domain.use_case.user_settings

import com.overtimedevs.bordersproject.data.repository.UserRepository
import com.overtimedevs.bordersproject.domain.model.UserSettings
import javax.inject.Inject

class GetUserSettings @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): UserSettings {
        return userRepository.getUserSettings()
    }
}