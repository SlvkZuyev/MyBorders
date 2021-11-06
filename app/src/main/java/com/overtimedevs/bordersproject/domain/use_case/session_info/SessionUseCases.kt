package com.overtimedevs.bordersproject.domain.use_case.session_info

import javax.inject.Inject

data class SessionUseCases @Inject constructor(
    val saveSessionInfo: SaveSessionInfo,
    val getSessionInfo: GetSessionInfo
)