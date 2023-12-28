package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val email = findViewById<EditText>(R.id.editTextEmail)
        val pass = findViewById<EditText>(R.id.editTextPassword)
        val signbtn = findViewById<Button>(R.id.buttonSignIn)
        val tosignup = findViewById<TextView>(R.id.textViewSignUp)
        signbtn.setOnClickListener({
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        })
        tosignup.setOnClickListener({
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        })


    }

}