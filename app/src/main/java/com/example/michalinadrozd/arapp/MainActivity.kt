package com.example.michalinadrozd.arapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_AR.setOnClickListener{
            val intent = Intent(this, Augumented_reality::class.java)
            startActivity(intent)


        }
    }


}
