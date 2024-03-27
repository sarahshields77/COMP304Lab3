package com.sarah.sarahshields_comp304lab3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PatientDetailsActivity : AppCompatActivity() {
    private var selectedPatientName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_details)

        // receive selected patient's name from intent in PatientMenuActivity
        selectedPatientName = intent.getStringExtra("selectedPatientName")
        // added Mar 27 to store the received nurseId to pass back to PatientMenuActivity
        val nurseId = intent.getStringExtra("nurseId") ?: ""
        Log.d("PatientDetailsActivity", "Received nurseId: $nurseId")

        // Load patient details
        loadPatientDetails(selectedPatientName!!)
    }
    override fun onResume() {
        super.onResume()
        // Reload patient details when the activity resumes
        selectedPatientName?.let { loadPatientDetails(it) }
    }
    private fun loadPatientDetails(patientName: String) {
        val databaseReference = FirebaseDatabase.getInstance().reference
        val patientQuery = databaseReference.child("patients").orderByChild("firstName").equalTo(patientName)

        patientQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (patientSnapshot in snapshot.children) {
                    val patient = patientSnapshot.getValue(Patient::class.java)
                    if (patient != null) {
                        // You found the patient!
                        updateTextViews(patient)
                        // Initialize buttons and set click listeners
                        initializeButtons(patient)
                        break // Exit the loop since you found the match
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Handle query error
            }
        })
    }
    private fun initializeButtons(patient: Patient) {
        val updatePatientButton = findViewById<Button>(R.id.updatePatientButton)
        val addPatientTestButton = findViewById<Button>(R.id.addPatientTestButton)
        val viewPatientTestsButton = findViewById<Button>(R.id.viewPatientTestsButton)
        val returnToPatientMenuButton = findViewById<Button>(R.id.returnToPatientMenuButton)

        updatePatientButton.setOnClickListener {
            val intent = Intent(this, UpdatePatientActivity::class.java)
            // Pass patient details as extras
            intent.putExtra("patientId", patient.patientId)
            intent.putExtra("firstName", patient.firstName)
            intent.putExtra("lastName", patient.lastName)
            intent.putExtra("department", patient.department)
            intent.putExtra("nurseId", patient.nurseId)
            intent.putExtra("room", patient.room)
            startActivity(intent)
        }
        addPatientTestButton.setOnClickListener {
            val intent = Intent(this, NewPatientTestActivity::class.java)
            startActivity(intent)
        }
        viewPatientTestsButton.setOnClickListener {
            // access patientId from the 'patient' object
            val selectedPatientId = patient.patientId ?: ""
            Log.d("PatientDetailsActivity", "Received patientId: $selectedPatientId")
            val intent = Intent(this, ViewPatientTestsActivity::class.java).apply {
                putExtra("patientId", selectedPatientId)
            }
            startActivity(intent)
        }
        returnToPatientMenuButton.setOnClickListener {
            val nurseId = intent.getStringExtra("nurseId") ?: ""
            val intent = Intent(this, PatientMenuActivity::class.java).apply {
                putExtra("nurseId", nurseId)
            }
            startActivity(intent)
        }
    }
    private fun updateTextViews(patient: Patient) {
        val patientIDTextView = findViewById<TextView>(R.id.patientIDTextViewDetails)
        patientIDTextView.text = patient.patientId ?: ""
        val patientFirstNameTextView = findViewById<TextView>(R.id.patientFirstNameTextViewDetails)
        patientFirstNameTextView.text = patient.firstName ?: ""
        val patientLastNameTextView = findViewById<TextView>(R.id.patientLastNameTextViewDetails)
        patientLastNameTextView.text = patient.lastName ?: ""
        val patientDepartmentTextView = findViewById<TextView>(R.id.patientDepartmentTextViewDetails)
        patientDepartmentTextView.text = patient.department ?: ""
        val patientNurseTextView = findViewById<TextView>(R.id.patientNurseTextViewDetails)
        patientNurseTextView.text = patient.nurseId ?: ""
        val patientRoomNumberTextView = findViewById<TextView>(R.id.patientRoomTextViewDetails)
        patientRoomNumberTextView.text = patient.room ?: ""
    }
}
