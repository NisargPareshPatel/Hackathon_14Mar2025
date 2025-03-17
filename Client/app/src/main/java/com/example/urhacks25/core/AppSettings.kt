package com.example.urhacks25.core

import com.russhwolf.settings.Settings

class AppSettings (
    private val settings: Settings
) {
    var token: String
        get() = settings.getString("token", "")
        set(value) = settings.putString("token", value)

    var id: String
        get() = settings.getString("id", "")
        set(value) = settings.putString("id", value)

    var isStore: Boolean
        get() = settings.getBoolean("isStore", false)
        set(value) = settings.putBoolean("isStore", value)
}