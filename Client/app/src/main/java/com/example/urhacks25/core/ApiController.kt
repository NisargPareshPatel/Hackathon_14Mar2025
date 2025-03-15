package com.example.urhacks25.core

import com.example.urhacks25.core.api_model.ApiAuthModel
import com.example.urhacks25.core.api_model.ApiProductModel
import com.example.urhacks25.core.api_model.ApiStoreModel
import com.example.urhacks25.core.api_model.ApiUserModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put

class ApiController (
    private val client: HttpClient,
    private val settings: AppSettings
) {
    companion object {
        private val BASE_URL = "http://10.69.69.111:4000"

        private val BASE_URL_PRODS = "$BASE_URL/api/prodRoutes"
        private val BASE_URL_USER = "$BASE_URL/api/userRoutes"
        private val BASE_URL_STORE = "$BASE_URL/api/storeRoutes"
    }

    // -- Products
    suspend fun prodCreate(model: ApiProductModel): Result<Boolean> {
        return runCatching {
            client.post("${BASE_URL_PRODS}/create") {
                addAuthorization()
                setJsonContent(model)
            }.status == HttpStatusCode.OK
        }
    }

    suspend fun getProdByStore(storeId: String): Result<List<ApiProductModel>> {
        return runCatching {
            client.post("${BASE_URL_PRODS}/getProdbyStore") {
                addAuthorization()
                setJsonContent(buildJsonObject {
                    put("sid", storeId)
                })
            }.bodyAsResult()
        }
    }

    suspend fun setProdBook(bookerId: String, productId: String): Result<Boolean> {
        return runCatching {
            client.post("${BASE_URL_PRODS}/setBook") {
                addAuthorization()
                setJsonContent(buildJsonObject {
                    put("prod_id", productId)
                    put("booker_id", bookerId)
                })
            }.status == HttpStatusCode.OK
        }
    }

    suspend fun getProdById(id: String): Result<ApiProductModel> {
        return runCatching {
            client.get("${BASE_URL_PRODS}/$id") {
                addAuthorization()
            }.bodyAsResult()
        }
    }

    // -- Users
    suspend fun userLogin(username: String, password: String): Result<ApiUserModel> {
        return runCatching {
            client.post("${BASE_URL_USER}/login") {
                setJsonContent(ApiAuthModel(username, password))
            }.bodyAsResult()
        }
    }

    suspend fun userRegister(model: ApiUserModel): Result<ApiUserModel> {
        return runCatching {
            client.post("${BASE_URL_USER}/signup") {
                setJsonContent(model)
            }.bodyAsResult()
        }
    }

    suspend fun getUserById(id: String): Result<ApiUserModel> {
        return runCatching {
            client.get("${BASE_URL_USER}/$id") {
                addAuthorization()
            }.bodyAsResult()
        }
    }

    // -- Stores
    suspend fun storeLogin(username: String, password: String): Result<ApiStoreModel> {
        return runCatching {
            client.post("${BASE_URL_STORE}/login") {
                setJsonContent(ApiAuthModel(username, password))
            }.bodyAsResult()
        }
    }

    suspend fun storeRegister(model: ApiStoreModel): Result<ApiStoreModel> {
        return runCatching {
            client.post("${BASE_URL_STORE}/signup") {
                setJsonContent(model)
            }.bodyAsResult()
        }
    }

    suspend fun getStoreById(id: String): Result<ApiStoreModel> {
        return runCatching {
            client.get("${BASE_URL_STORE}/$id") {
                addAuthorization()
            }.bodyAsResult()
        }
    }

    suspend fun getStores(): Result<List<ApiStoreModel>> {
        return runCatching {
            client.get("${BASE_URL_STORE}/getStores") {
                addAuthorization()
            }.bodyAsResult()
        }
    }

    //

    private fun HttpRequestBuilder.addAuthorization() {
        if (settings.token.isNotEmpty()) {
            header("Authorization", "Bearer ${settings.token}")
        }
    }

    inline fun <reified T> HttpRequestBuilder.setJsonContent(data: T) {
        contentType(ContentType.Application.Json)
        setBody<T>(data)
    }

    suspend inline fun <reified T> HttpResponse.bodyAsResult(): T {
        return if (status == HttpStatusCode.OK) {
            body<T>()
        } else {
            throw Exception(body<JsonObject>()["error"]?.jsonPrimitive?.content ?: "Unknown error")
        }
    }
}