package com.example.translator.translate.domain.translate

enum class TranslateError {
    UNKNOWN,
    SERVICE_UNAVAILABLE,
    SERVER_ERROR,
    CLIENT_ERROR,
    INVALID_SOURCE_LANGUAGE,
    INVALID_TARGET_LANGUAGE,
}

class TranslateException(val error: TranslateError): Exception("An error occurred while translating: $error")