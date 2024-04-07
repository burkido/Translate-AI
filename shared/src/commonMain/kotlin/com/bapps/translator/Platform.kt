package com.bapps.translator

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform