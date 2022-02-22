package com.example.a7minuteworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.FrameLayout
import android.widget.Toast
import com.example.a7minuteworkout.databinding.ActivityMainBinding


// binding faster in compile time
// avoid the reuse of id

class MainActivity : AppCompatActivity() {

    // for using view binding check gridle script file

    private var binding:ActivityMainBinding? = null





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // preapre the binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        // setting the binding
        setContentView(binding?.root)

        // create for start button
        //  val frameLayOut:FrameLayout = findViewById(R.id.flStart)

        // using id with binding
        binding?.flStart?.setOnClickListener{
            startActivity(Intent(this,ExcersizeActivity::class.java))
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        // unassign binding to prevent memory look
        binding = null
    }
}