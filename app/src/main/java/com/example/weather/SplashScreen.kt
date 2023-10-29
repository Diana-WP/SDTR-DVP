package com.example.weather

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {//This method is called when the activity is being created

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        window.setFlags(// it sets the flags to make the window fullscreen
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler().postDelayed({ //schedules a task to be executed after a delay of 3000 milliseconds (3 seconds)
            val intent = Intent(this, MainActivity::class.java) //start mainactivity after 3 sec
            startActivity(intent)
            finish()//close the splash screen activity.
        }, 3000)
    }

}