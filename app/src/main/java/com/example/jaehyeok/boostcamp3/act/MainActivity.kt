package com.example.jaehyeok.boostcamp3.act

import android.databinding.DataBindingUtil
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.jaehyeok.boostcamp3.R
import com.example.jaehyeok.boostcamp3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val TAG  = "@#mainAct#@"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG,"onCreate")
        init()
    }

    private fun init(){
        val binding:ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.ld = layout_data("검색")
        val actionBar = binding.incToolbar!!.toolBar
        actionBar!!.title = "부스트캠프 사전과제"
        actionBar.setTitleTextColor(Color.WHITE)
    }

    data class layout_data(val bt_search : String){

    }
}
