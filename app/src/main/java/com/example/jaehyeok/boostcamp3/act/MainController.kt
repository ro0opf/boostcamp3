package com.example.jaehyeok.boostcamp3.act

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.jaehyeok.boostcamp3.adapter.RecyclerAdapter
import com.example.jaehyeok.boostcamp3.databinding.ActivityMainBinding
import com.example.jaehyeok.boostcamp3.item.Homefeed
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity


class MainController(val bd: ActivityMainBinding, val context: Context) {
    val clientId = "mBNgdDH46JIfQDYl3bIe"
    val clientSecret = "k0u_z5uS5I"
    val TAG :String = "@#mainController#@"
    val mainActivity : MainActivity = context as MainActivity
    val NAVER_OPENAPI = "https://openapi.naver.com"

    fun onClickSearch(v: View) {
        if (bd.edtKeyword.text.isNullOrEmpty()) {
            Toast.makeText(context, "검색어를 입력해주세요", Toast.LENGTH_SHORT).show()
        } else {
            if(!verifyAvailableNetwork(mainActivity)) {
                Toast.makeText(context, "네트워크 연결을 확인해주세요", Toast.LENGTH_SHORT).show()
            }else {
                bd.rcvMain.visibility = View.INVISIBLE
                bd.animationView.visibility = View.VISIBLE
                bd.animationView.playAnimation()
                fetchJson(bd.edtKeyword.text.toString())
            }
            val imm = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
        }
    }


    fun verifyAvailableNetwork(activity: AppCompatActivity):Boolean{
        val connectivityManager=activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo=connectivityManager.activeNetworkInfo
        return  networkInfo!=null && networkInfo.isConnected
    }

    fun fetchJson(vararg p0: String) {
        val text = p0[0]
        val urlBuilder : HttpUrl.Builder = HttpUrl.parse(NAVER_OPENAPI + "/v1/search/movie.json")!!.newBuilder()
        urlBuilder.addQueryParameter("query",text)
            .addQueryParameter("display","50")
            .addQueryParameter("start","1")
            .addQueryParameter("genre","")

        val url = urlBuilder.build().toString()

        val request = Request.Builder()
            .url(url)
            .addHeader("X-Naver-Client-Id", clientId)
            .addHeader("X-Naver-Client-Secret", clientSecret)
            .method("GET", null)
            .build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                Log.e(TAG,"Success to execute request : $body")
                val gson = GsonBuilder().create()
                val homefeed = gson.fromJson(body, Homefeed::class.java)
                mainActivity.runOnUiThread{
                    bd.animationView.cancelAnimation()
                    bd.animationView.visibility = View.INVISIBLE
                    bd.rcvMain.visibility = View.VISIBLE
                    bd.rcvMain.adapter = RecyclerAdapter(homefeed)
                    if(homefeed.items.isNotEmpty()) {
                        bd.edtKeyword.setText("")
                    }else{
                        Toast.makeText(context, "\"${text}\" 검색 결과는 없습니다", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                Log.e(TAG,"Failed to execute request")
            }
        })
    }
}