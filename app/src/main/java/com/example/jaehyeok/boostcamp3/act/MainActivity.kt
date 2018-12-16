package com.example.jaehyeok.boostcamp3.act

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jaehyeok.boostcamp3.R
import com.example.jaehyeok.boostcamp3.R.id.rcv_main
import com.example.jaehyeok.boostcamp3.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val TAG  = "@#mainAct#@"
    var backPressedTime:Long = 0
    val FINISH_INTERVAL_TIME:Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG,"onCreate")
        init()
        setRecyclerView()
    }

    private fun init(){
        val binding:ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val actionBar = binding.incToolbar.toolBar
        setSupportActionBar(actionBar)
        actionBar.title = "부스트캠프 사전과제"
        actionBar.setTitleTextColor(Color.WHITE)
        binding.ctrl = MainController(binding, this)
    }

    fun setRecyclerView(){
        rcv_main.layoutManager = LinearLayoutManager(applicationContext)
        rcv_main.setHasFixedSize(true)
    }


    override fun onBackPressed() {
        val tempTime = System.currentTimeMillis()
        val intervalTime = tempTime - backPressedTime

        if (intervalTime in 0..FINISH_INTERVAL_TIME) {
            moveTaskToBack(true)
            finish()
        } else {
            backPressedTime = tempTime
            Toast.makeText(applicationContext, "\"뒤로\" 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
    }
}
