package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val editTextSignUpEmail = findViewById<EditText>(R.id.editTextSignUpEmail)
        val editTextSignUpPassword = findViewById<EditText>(R.id.editTextSignUpPassword)
        val buttonSignUp = findViewById<Button>(R.id.buttonSignUp)
        val textViewSignIn = findViewById<TextView>(R.id.textViewSignIn)

        buttonSignUp.setOnClickListener {
            val signUpEmail = editTextSignUpEmail.text.toString()
            val signUpPassword = editTextSignUpPassword.text.toString()

            // TODO: Implement your Sign Up logic here
            // For now, you can print the email and password
            println("Sign Up Email: $signUpEmail, Password: $signUpPassword")

            // TODO: Add logic to create a new user (e.g., store credentials in a database)

            // For testing purposes, let's open a new activity (replace it with your actual logic)
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        textViewSignIn.setOnClickListener {
            // Navigate to the Sign In page
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
