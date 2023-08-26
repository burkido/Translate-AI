package com.example.translator.core.domain.util

import kotlinx.coroutines.flow.StateFlow

expect class CommonStateFlow<T>(flow: StateFlow<T>): StateFlow<T>

fun <T> StateFlow<T>.asCommonStateFlow(): CommonStateFlow<T> = CommonStateFlow(this)