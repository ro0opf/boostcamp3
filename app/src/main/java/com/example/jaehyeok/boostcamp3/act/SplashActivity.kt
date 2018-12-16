package com.example.jaehyeok.boostcamp3.act

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.jaehyeok.boostcamp3.R
import com.example.jaehyeok.boostcamp3.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    val SPLASH_TIME : Long = 2000;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        Handler().postDelayed({
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        }, SPLASH_TIME)
    }

    fun init(){
        val binding:ActivitySplashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.ctrl = SplashController(binding, this)
    }
}
