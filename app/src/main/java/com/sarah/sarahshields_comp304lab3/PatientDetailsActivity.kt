package com.sarah.sarahshields_comp304lab3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PatientDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_details)

        // receive selected patient's name from intent in PatientMenuActivity
        val selectedPatientName = intent.getStringExtra("selectedPatientName")

        val databaseReference = FirebaseDatabase.getInstance().reference

        val patientQuery = databaseReference.child("patients").orderByChild("firstName").equalTo(
            selectedPatientName
        )

        patientQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (patientSnapshot in snapshot.children) {
                    val patient = patientSnapshot.getValue(Patient::class.java)
                    if (patient != null) {
                        // You found the patient!
                        updateTextViews(patient)
                        break // Exit the loop since you found the match
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Handle query error
            }
        })

        // Initialize buttons and set click listeners
        val updatePatientButton = findViewById<Button>(R.id.updatePatientButton)
        val addPatientTestButton = findViewById<Button>(R.id.addPatientTestButton)
        val viewPatientTestsButton = findViewById<Button>(R.id.viewPatientTestsButton)
        val returnToPatientMenuButton = findViewById<Button>(R.id.returnToPatientMenuButton)

        updatePatientButton.setOnClickListener {
            val intent = Intent(this, UpdatePatientActivity::class.java)
            startActivity(intent)
        }

        addPatientTestButton.setOnClickListener {
            val intent = Intent(this, NewPatientTestActivity::class.java)
            startActivity(intent)
        }

        viewPatientTestsButton.setOnClickListener {
            val intent = Intent(this, ViewPatientTestsActivity::class.java)
            startActivity(intent)
        }

        returnToPatientMenuButton.setOnClickListener {
            val intent = Intent(this, PatientMenuActivity::class.java)
            startActivity(intent)
        }
    }
    private fun updateTextViews(patient: Patient) {

        val patientIDTextView = findViewById<TextView>(R.id.patientIDTextView)
        patientIDTextView.text = patient.patientId

        val patientFirstNameTextView = findViewById<TextView>(R.id.patientFirstNameTextView)
        patientFirstNameTextView.text = patient.firstName

        val patientLastNameTextView = findViewById<TextView>(R.id.patientLastNameTextView)
        patientLastNameTextView.text = patient.lastName

        val patientDepartmentTextView = findViewById<TextView>(R.id.patientDepartmentTextView)
        patientDepartmentTextView.text = patient.department

        val patientNurseTextView = findViewById<TextView>(R.id.patientNurseTextView)
        patientNurseTextView.text = patient.nurseId

        val patientRoomNumberTextView = findViewById<TextView>(R.id.patientRoomTextView)
        patientRoomNumberTextView.text = patient.room
    }
}