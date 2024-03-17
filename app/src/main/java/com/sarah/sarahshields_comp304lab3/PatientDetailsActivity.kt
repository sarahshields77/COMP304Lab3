package com.sarah.sarahshields_comp304lab3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class PatientDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_details)

        // receive selected patient's name from intent in PatientMenuActivity
        val selectedPatientName = intent.getStringExtra("selectedPatientName")

        // update UI with it using a TextView
        // TODO: figure this out - should be showing full details on this page
        val patientNameTextView = findViewById<TextView>(R.id.patientNameTextView)
        patientNameTextView.text = selectedPatientName

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
}