package com.flixclusive.crash

import com.flixclusive.core.util.network.POST
import com.flixclusive.core.util.network.asString
import com.flixclusive.core.util.network.ignoreAllSSLErrors
import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Test

class CrashReportSenderTest : CrashReportSender {
    private lateinit var client: OkHttpClient

    private fun generateDirtyClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .followRedirects(true)
            .followSslRedirects(true)
            .ignoreAllSSLErrors()
            .build()
    }

    @Before
    fun setUp() {
        client = generateDirtyClient()
    }

    @Test
    fun `send function should return true`() {
        val errorMessage = """
            ========== ERROR-SAMPLE ===========
            
            LOREM IPSUM LOREM IPSUM LOREM IPSUM 
            LOREM IPSUM LOREM IPSUM LOREM IPSUM 
            LOREM IPSUM LOREM IPSUM LOREM IPSUM 
            LOREM IPSUM LOREM IPSUM LOREM IPSUM 
            ===================================
        """.trimIndent()

        send(errorMessage)
    }

    override fun send(errorLog: String) {
        val response = client.newCall(
            POST(
                url = errorReportFormUrl,
                data = mapOf("entry.1687138646" to errorLog)
            )
        ).execute()
        val responseString = response.body?.charStream().asString()

        assert(
            response.isSuccessful
                && (responseString?.contains("form_confirm", true) == true || responseString?.contains("Submit another response", true) == true)
        )
    }
}