package com.ivandjr.listcompose.util

import java.util.UUID

fun String.isValidUUID(): Boolean {
    return try {
        UUID.fromString(this)
        true
    } catch (e: IllegalArgumentException) {
        false
    }
}
