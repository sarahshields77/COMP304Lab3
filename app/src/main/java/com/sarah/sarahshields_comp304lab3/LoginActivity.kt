package com.sarah.sarahshields_comp304lab3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var nurseIDInput : TextInputEditText
    private lateinit var nursePasswordInput : TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        nurseIDInput = findViewById<TextInputEditText>(R.id.userNameInput)
        nursePasswordInput = findViewById<TextInputEditText>(R.id.passwordInput)

        val loginButton = findViewById<Button>(R.id.loginPageButton)
        loginButton.setOnClickListener { loginNurse() }
    }

    private fun loginNurse() {
        val nurseId = nurseIDInput.text.toString()
        val password = nursePasswordInput.text.toString()

        auth.signInWithEmailAndPassword(nurseId, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()

                    val nurseId = auth.currentUser?.email ?: ""

                    val intent = Intent(this, PatientMenuActivity::class.java)
                    intent.putExtra("nurseId", nurseId)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this,
                        "Login failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}