package com.example.translator

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform