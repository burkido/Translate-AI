package com.example.translator.core.domain.util

import kotlinx.coroutines.flow.Flow

actual open class CommonFlow<T> actual constructor(
    flow: Flow<T>
) : Flow<T> by flow