package com.kani.medzone

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.kani.medzone.ui.AdminActivity


class SplashActivity : AppCompatActivity() {
    private val SPLASH_SCREEN_TIME_OUT = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)


        Handler().postDelayed({

            if(BuildConfig.isAdmin)
            {
                val i = Intent(
                    this@SplashActivity,
                    AdminActivity::class.java
                )
                intent.extras?.let {
                    i.putExtras(it)
                }
                //Intent is used to switch from one activity to another.
                startActivity(i)
            }else{
                val i =
                    Intent(
                        this@SplashActivity,
                        MainActivity::class.java
                    )
                intent.extras?.let {
                    i.putExtras(it)
                }

                //Intent is used to switch from one activity to another.
                startActivity(i)
            }

            //invoke the SecondActivity.
            finish()
            //the current activity will get finished.
        }, SPLASH_SCREEN_TIME_OUT.toLong())
    }
}