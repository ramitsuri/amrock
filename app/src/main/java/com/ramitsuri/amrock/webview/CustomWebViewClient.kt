package com.ramitsuri.amrock.webview

import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient


class CustomWebViewClient() : WebViewClient() {

    var onPageStarted: (() -> Unit)? = null
    var onPageFinished: (() -> Unit)? = null

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        onPageStarted?.invoke()
    }


    override fun onPageFinished(view: WebView, url: String?) {
        super.onPageFinished(view, url)
        onPageFinished?.invoke()
    }
}
