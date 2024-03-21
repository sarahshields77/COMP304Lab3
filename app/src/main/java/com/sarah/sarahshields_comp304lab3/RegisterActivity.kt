package com.sarah.sarahshields_comp304lab3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    private lateinit var nurseIDInput: TextInputEditText
    private lateinit var nurseFirstNameInput : TextInputEditText
    private lateinit var nurseLastNameInput : TextInputEditText
    private lateinit var nurseDepartmentInput : TextInputEditText
    private lateinit var nursePasswordInput : TextInputEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        nurseIDInput = findViewById<TextInputEditText>(R.id.nurseIDInput)
        nurseFirstNameInput = findViewById<TextInputEditText>(R.id.nurseFirstNameInput)
        nurseLastNameInput = findViewById<TextInputEditText>(R.id.nurseLastNameInput)
        nurseDepartmentInput = findViewById<TextInputEditText>(R.id.nurseDepartmentInput)
        nursePasswordInput = findViewById<TextInputEditText>(R.id.nursePasswordInput)

        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("Nurses")

        val registerPageButton = findViewById<Button>(R.id.registerPageButton)
        registerPageButton.setOnClickListener { registerNurse() }
    }
    private fun registerNurse() {
        val nurseId = nurseIDInput.text.toString()
        val firstName = nurseFirstNameInput.text.toString()
        val lastName = nurseLastNameInput.text.toString()
        val department = nurseDepartmentInput.text.toString()
        val password = nursePasswordInput.text.toString()

        auth.createUserWithEmailAndPassword(nurseId, password) // Correct password usage
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    saveNurseToDatabase(user!!.uid, firstName, lastName, department, password) // Removed nurseID
                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                } else {
                    Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun saveNurseToDatabase(uid: String, firstName: String, lastName: String, department: String, password: String) {
        val nurseData = HashMap<String, Any>()
        nurseData["nurseId"] = uid
        nurseData["firstname"] = firstName
        nurseData["lastname"] = lastName
        nurseData["department"] = department
        nurseData["password"] = password

        databaseReference.child(uid).setValue(nurseData)
    }
}
