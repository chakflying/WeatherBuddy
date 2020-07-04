package com.example.weatherbuddy.data

import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.converter.gson.GsonConverterFactory


interface HKOAPI {
    // https://data.weather.gov.hk/weatherAPI/opendata/opendata.php?dataType=LTMV&lang=en&rformat=json
    @GET("opendata.php")
    suspend fun getLatestVisibility(
        @Query("lang") languageCode: String = "en",
        @Query("dataType") m_dataType: String = "LTMV"
    ): HKOVisibilityResponse

    companion object {
        operator fun invoke(): HKOAPI {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("rformat", "json")
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder().addInterceptor(requestInterceptor).build()
            return Retrofit.Builder().client(okHttpClient)
                .baseUrl("https://data.weather.gov.hk/weatherAPI/opendata/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(HKOAPI::class.java)
        }
    }
}