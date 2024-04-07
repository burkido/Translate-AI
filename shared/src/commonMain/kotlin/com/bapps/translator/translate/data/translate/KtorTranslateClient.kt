package com.bapps.translator.translate.data.translate

import com.bapps.translator.translate.domain.translate.TranslateClient
import com.bapps.translator.translate.domain.translate.TranslateError
import com.bapps.translator.translate.domain.translate.TranslateException
import com.bapps.translator.util.NetworkConstants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.utils.io.errors.IOException

class KtorTranslateClient(
    private val httpClient: HttpClient  // HttpClient is expect class so it uses native impl
): TranslateClient {

    override suspend fun translate(
        text: String,
        sourceLanguage: String,
        targetLanguage: String
    ): String {
        val result = try {
            httpClient.post {
                url(NetworkConstants.BASE_URL + "translate/")
                contentType(ContentType.Application.Json)
                setBody(
                    TranslateDto(
                        textToTranslate = text,
                        sourceLanguageCode = sourceLanguage,
                        targetLanguageCode = targetLanguage
                    )
                )
            }
        } catch (e: IOException) {
            throw TranslateException(TranslateError.SERVICE_UNAVAILABLE)
        }

        when(result.status.value) {
            in 200..299 -> Unit
            in 400..499 -> throw TranslateException(TranslateError.CLIENT_ERROR)
            500 -> throw TranslateException(TranslateError.SERVER_ERROR)
            else -> throw TranslateException(TranslateError.UNKNOWN)
        }

        return try {
            result.body<TranslatedDto>().translatedText
        } catch (e: Exception) {
            throw TranslateException(TranslateError.SERVER_ERROR)
        }
    }
}