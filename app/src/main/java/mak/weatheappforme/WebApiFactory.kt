package mak.weatheappforme

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object WebApiFactory {

    private const val BASE_URL = "http://dataservice.accuweather.com/"

    private val authInterceptor = Interceptor { chain ->
        val newUrl = chain.request().url()
            .newBuilder()
            .addQueryParameter("apikey", BuildConfig.ACCUWEATHER_API_KEY)
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    private val client = OkHttpClient().newBuilder()
        .addInterceptor(authInterceptor)
        .build()


    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()


    val webApi: WebApi = retrofit.create(WebApi::class.java)

}