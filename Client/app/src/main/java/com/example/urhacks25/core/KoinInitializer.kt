package com.example.urhacks25.core

import android.content.Context
import androidx.annotation.Keep
import androidx.startup.Initializer
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

@Keep
class KoinInitializer: Initializer<Boolean> {
    override fun create(context: Context): Boolean {
        startKoin {
            androidLogger()
            androidContext(context.applicationContext)

            module {

            }
        }

        return true
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?> {
        return emptyList()
    }
}