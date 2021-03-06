package org.jetbrains.kotlin.androidtestapplication

import android.support.v7.app.*
import android.os.Bundle
import android.widget.*
import kotlinx.coroutines.experimental.android.*
import kotlinx.coroutines.experimental.*
import io.ktor.common.client.*
import io.ktor.common.client.http.*

class MainActivity : AppCompatActivity() {
    val client = HttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val urlField = findViewById<EditText>(R.id.urlText)
        val responseView = findViewById<TextView>(R.id.contentView)

        findViewById<Button>(R.id.getButton).setOnClickListener {
            async(CommonPool) {
                val response = client.request {
                    url.encodedPath = "/"
                    url.protocol = URLProtocol.HTTPS
                    url.port = 443
                    url.host = urlField.text.toString()
                }
                launch(UI) {
                    responseView.text = response.body
                }
            }
        }
    }

}
