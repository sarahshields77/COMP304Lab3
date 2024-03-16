package com.sarah.sarahshields_comp304lab3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ViewPatientTestsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_patient_tests)

        val backtopatientdetailsbutton = findViewById<Button>(R.id.backToPatientDetailsButton)
        backtopatientdetailsbutton.setOnClickListener {
            val intent = Intent(this, PatientDetailsActivity::class.java)
            startActivity(intent)
        }
    }
}