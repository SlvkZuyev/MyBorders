package com.overtimedevs.bordersproject.domain.use_case.session_info

import com.overtimedevs.bordersproject.data.repository.SessionRepository
import com.overtimedevs.bordersproject.domain.model.SessionInfo
import javax.inject.Inject

class GetSessionInfo @Inject constructor(
    private val sessionRepository: SessionRepository
) {
    operator fun invoke(): SessionInfo {
        return sessionRepository.getSessionInfo()
    }
}