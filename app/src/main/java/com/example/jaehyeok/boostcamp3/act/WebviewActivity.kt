package com.example.jaehyeok.boostcamp3.act

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.ContentValues
import android.content.res.Resources
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.*
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.jaehyeok.boostcamp3.R
import com.example.jaehyeok.boostcamp3.R.id.*
import com.example.jaehyeok.boostcamp3.databinding.ActivityWebviewBinding
import kotlinx.android.synthetic.main.activity_webview.*
import android.content.Intent
import android.graphics.Bitmap

class WebviewActivity:AppCompatActivity(){
    val TAG = "@#webviewAct#@"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun init() {
        val binding: ActivityWebviewBinding = DataBindingUtil.setContentView(this, R.layout.activity_webview)
        val tbar = binding.incToolbar.toolBar
        setSupportActionBar(tbar)
        binding.ctrl = WebviewController(binding, this)
        webview.webViewClient = object:WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                tbar.title = url
            }
        }
        webview.webChromeClient = object : WebChromeClient(){
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                prgs_h.visibility = View.VISIBLE
                prgs_h.setProgress(newProgress)
                if (prgs_h.progress == 100){
                    prgs_h.visibility = View.INVISIBLE
                }
                super.onProgressChanged(view, newProgress)
            }
        }
        val webSetting = webview.settings
        webSetting.javaScriptEnabled = true
        WebView.setWebContentsDebuggingEnabled(false)
        webview.loadUrl(intent.getStringExtra("url"))

        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        refresh_lo.setOnRefreshListener {
            webview.reload()
            refresh_lo.isRefreshing = false
        }
        prgs_h.max = 100
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.webview_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if(webview.canGoForward()){
            menu?.findItem(item_forward)?.setVisible(true)
        }
        return super.onPrepareOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId
        when(id){
            android.R.id.home->{
                onBackPressed()
            }
            item_share->{
                val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
                sharingIntent.type = "text/plain"
                val shareBody = webview.url
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "부스트 캠프3, 영화 검색")
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody)
                startActivity(Intent.createChooser(sharingIntent, "공유하기"))
            }
            item_refresh->{
                webview.reload()
            }
            item_forward->{
                webview.goForward()
            }
            item_addhome->{
                // @TODO : shortcut
            }
            item_bookmark->{
                // @TODO : bookmark
            }
            item_internet->{
                // @TODO : internet
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack()
            return
        }
        super.onBackPressed()
    }
}
