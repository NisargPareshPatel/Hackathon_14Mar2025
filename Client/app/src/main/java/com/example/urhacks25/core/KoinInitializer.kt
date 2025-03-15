package com.example.urhacks25.core

import android.content.Context
import androidx.annotation.Keep
import androidx.startup.Initializer
import com.cloudinary.Configuration
import com.cloudinary.android.MediaManager
import com.russhwolf.settings.SharedPreferencesSettings
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import kotlin.math.sin

@Keep
class KoinInitializer : Initializer<Boolean> {
    override fun create(context: Context): Boolean {
        MediaManager.init(
            context, Configuration.Builder()
                .setCloudName("dzbppy2qi")
                .setApiKey("274454216227838")
                .setApiSecret("NeHWTJ9e1ejL4Q2OinP1zToXzmM")
                .build()
        )

        startKoin {
            androidLogger()
            androidContext(context.applicationContext)

            modules(
                module {
                    single<HttpClient> {
                        HttpClient(OkHttp) {
                            install(Logging)

                            install(ContentNegotiation) {
                                json(Json {
                                    ignoreUnknownKeys = true
                                    encodeDefaults = false
                                })
                            }
                        }
                    }

                    single<AppSettings> {
                        AppSettings(
                            settings = SharedPreferencesSettings(
                                delegate = get<Context>().getSharedPreferences(
                                    "prefs",
                                    Context.MODE_PRIVATE
                                )
                            )
                        )
                    }

                    single<ApiController> {
                        ApiController(
                            client = get<HttpClient>(),
                            settings = get<AppSettings>()
                        )
                    }
                }
            )
        }

        return true
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?> {
        return emptyList()
    }
}