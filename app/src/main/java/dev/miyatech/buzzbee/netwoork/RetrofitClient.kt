package dev.miyatech.buzzbee.netwoork

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


object RetrofitClient {


    const val Server = "https://billpaynxt.in/"
    const val MainServer = Server + "portal/billapi/"


    const val localkart="https://www.localkart.app/portal/api/"

    const val BaseUrl = "https://miyahosting.co.in/"
    const val HttpServer = BaseUrl + "buzzbee/portal/api/"

    val retrofit: Retrofit.Builder by lazy {
        val levelType: Level
//        if (BuildConfig.BUILD_TYPE.contentEquals("debug"))
        levelType = Level.BODY

        val logging = HttpLoggingInterceptor()
        logging.setLevel(levelType)

//        val okhttpClient = OkHttpClient.Builder()
        val okhttpClient = getUnsafeOkHttpClient()


        okhttpClient?.addInterceptor(logging)

        Retrofit.Builder()
            .baseUrl(HttpServer)
            .client(okhttpClient?.build())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val api_interface: ApiInterface by lazy {
        retrofit
            .build()
            .create(ApiInterface::class.java)
    }


    val retrofitClient: Retrofit.Builder by lazy {
        val levelType: Level
//        if (BuildConfig.BUILD_TYPE.contentEquals("debug"))
        levelType = Level.BODY

        val logging = HttpLoggingInterceptor()
        logging.setLevel(levelType)

//        val okhttpClient = OkHttpClient.Builder()
        val okhttpClient = getUnsafeOkHttpClient()


        okhttpClient?.addInterceptor(logging)

        Retrofit.Builder()
            .baseUrl(localkart)
            .client(okhttpClient?.build())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiInterface_LocalKart: ApiInterface by lazy {
        retrofitClient
            .build()
            .create(ApiInterface::class.java)
    }


    fun getUnsafeOkHttpClient(): OkHttpClient.Builder? {
        return try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(
                object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                }
            )

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier(HostnameVerifier { hostname, session -> true })
            builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}


fun isInternetAvailable(context: Context): Boolean {

    var result = false
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        connectivityManager.run {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
    }
    return result
}



