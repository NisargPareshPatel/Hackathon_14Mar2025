package com.example.urhacks25.core

import android.content.Context
import androidx.annotation.Keep
import androidx.startup.Initializer
import com.russhwolf.settings.SharedPreferencesSettings
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import kotlin.math.sin

@Keep
class KoinInitializer: Initializer<Boolean> {
    override fun create(context: Context): Boolean {
        startKoin {
            androidLogger()
            androidContext(context.applicationContext)

            module {
                single {
                    HttpClient(OkHttp) {
                        install(Logging)

                        install(ContentNegotiation) {
                            json()
                        }
                    }
                }

                single {
                    AppSettings(
                        settings = SharedPreferencesSettings(
                            delegate = get<Context>().getSharedPreferences("prefs", Context.MODE_PRIVATE)
                        )
                    )
                }

                single {
                    ApiController(
                        client = get<HttpClient>()
                    )
                }
            }
        }

        return true
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?> {
        return emptyList()
    }
}