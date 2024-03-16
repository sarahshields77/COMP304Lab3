package com.sarah.sarahshields_comp304lab3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class PatientDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_details)

        val updatepatientbutton = findViewById<Button>(R.id.updatePatientButton)
        updatepatientbutton.setOnClickListener {
            val intent = Intent(this, UpdatePatientActivity::class.java)
            startActivity(intent)
        }

        val addpatienttestbutton = findViewById<Button>(R.id.addPatientTestButton)
        addpatienttestbutton.setOnClickListener {
            val intent = Intent(this, NewPatientTestActivity::class.java)
            startActivity(intent)
        }

        val viewpatienttestsbutton = findViewById<Button>(R.id.viewPatientTestsButton)
        viewpatienttestsbutton.setOnClickListener {
            val intent = Intent(this, ViewPatientTestsActivity::class.java)
            startActivity(intent)
        }

        val returntopatientmenubutton = findViewById<Button>(R.id.returnToPatientMenuButton)
        returntopatientmenubutton.setOnClickListener {
            val intent = Intent(this, PatientMenuActivity::class.java)
            startActivity(intent)
        }
    }
}