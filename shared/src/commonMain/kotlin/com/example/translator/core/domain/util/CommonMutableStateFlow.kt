package com.example.translator.core.domain.util

import kotlinx.coroutines.flow.MutableStateFlow

expect class CommonMutableStateFlow<T>(flow: MutableStateFlow<T>): MutableStateFlow<T>

fun <T> MutableStateFlow<T>.asCommonMutableStateFlow(): CommonMutableStateFlow<T> = CommonMutableStateFlow(this)