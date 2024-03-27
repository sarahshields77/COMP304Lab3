package com.sarah.sarahshields_comp304lab3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase

class NewPatientTestActivity : AppCompatActivity() {
    private lateinit var testIDInput: TextInputEditText
    private lateinit var patientTestIDInput: TextInputEditText
    private lateinit var testNurseIDInput: TextInputEditText
    private lateinit var BPLInput: TextInputEditText
    private lateinit var BPHInput: TextInputEditText
    private lateinit var temperatureInput: TextInputEditText
    private lateinit var addNewTestButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_patient_test)

        // initialize UI elements
        testIDInput = findViewById(R.id.testIDInput)
        patientTestIDInput = findViewById(R.id.patientTestIDInput)
        testNurseIDInput = findViewById(R.id.testNurseIDInput)
        BPLInput = findViewById(R.id.BPLInput)
        BPHInput = findViewById(R.id.BPHInput)
        temperatureInput = findViewById(R.id.temperatureInput)
        addNewTestButton = findViewById(R.id.addNewTestButton)

        // set click listener for addNewTestButton
        addNewTestButton.setOnClickListener {
            // get values entered by nurse
            val testId = testIDInput.text.toString()
            val patientId = patientTestIDInput.text.toString()
            val nurseId = testNurseIDInput.text.toString()
            val bpl = BPLInput.text.toString()
            val bph = BPHInput.text.toString()
            val temperature = temperatureInput.text.toString()

            // create new test object with the values
            val newTest = Test(
                testId = testId,
                patientId = patientId,
                nurseId = nurseId,
                bpl = bpl,
                bph = bph,
                temperature = temperature
            )
            // push the new test to the database
            val databaseReference = FirebaseDatabase.getInstance().reference
            databaseReference.child("tests").push().setValue(newTest)

            Toast.makeText(this, "Test Added", Toast.LENGTH_SHORT).show()

            //finish this activity to return to previous screen
            finish()
        }
    }
}