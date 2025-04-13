package com.example.webviewapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val BASE_URL = "https://www.slavehack2.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webView = WebView(this)
        setContentView(webView)

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                val url = request.url.toString()
                return if (url.startsWith(BASE_URL)) {
                    false
                } else {
                    showExternalLinkDialog(url)
                    true
                }
            }
        }

        val data: Uri? = intent?.data
        if (data != null) {
            webView.loadUrl(data.toString())
        } else {
            webView.loadUrl(BASE_URL)
        }
    }

    private fun showExternalLinkDialog(url: String) {
        AlertDialog.Builder(this)
            .setTitle("Open External Link")
            .setMessage("This link is outside the app. Do you want to continue?")
            .setPositiveButton("Yes") { _, _ ->
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(browserIntent)
            }
            .setNegativeButton("No", null)
            .show()
    }
}
