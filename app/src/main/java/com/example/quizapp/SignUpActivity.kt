package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()

        val editTextSignUpEmail = findViewById<EditText>(R.id.editTextSignUpEmail)
        val editTextSignUpPassword = findViewById<EditText>(R.id.editTextSignUpPassword)
        val buttonSignUp = findViewById<Button>(R.id.buttonSignUp)
        val textViewSignIn = findViewById<TextView>(R.id.textViewSignIn)

        buttonSignUp.setOnClickListener {
            val signUpEmail = editTextSignUpEmail.text.toString()
            val signUpPassword = editTextSignUpPassword.text.toString()

            createAccount(signUpEmail, signUpPassword)
        }

        textViewSignIn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    // If sign-up fails, display a message to the user.
                    // You can handle errors or display a message to the user here
                }
            }
    }
}
