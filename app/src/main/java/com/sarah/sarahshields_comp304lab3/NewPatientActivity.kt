package com.sarah.sarahshields_comp304lab3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase

class NewPatientActivity : AppCompatActivity() {
    private lateinit var patientIDInput: TextInputEditText
    private lateinit var patientFirstNameInput: TextInputEditText
    private lateinit var patientLastNameInput: TextInputEditText
    private lateinit var patientDepartmentInput: TextInputEditText
    private lateinit var patientNurseIDInput: TextInputEditText
    private lateinit var patientRoomNumberInput: TextInputEditText
    private lateinit var addNewPatientButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_patient)

        // initialize UI elements
        patientIDInput = findViewById(R.id.patientIDInput)
        patientFirstNameInput = findViewById(R.id.patientFirstNameInput)
        patientLastNameInput = findViewById(R.id.patientLastNameInput)
        patientDepartmentInput = findViewById(R.id.patientDepartmentInput)
        patientNurseIDInput = findViewById(R.id.patientNurseIDInput)
        patientRoomNumberInput = findViewById(R.id.patientRoomNumberInput)
        addNewPatientButton = findViewById(R.id.addNewPatientButton)

        // set click listener for addNewPatientButton
        addNewPatientButton.setOnClickListener {
            // get values entered by nurse
            val patientId = patientIDInput.text.toString()
            val firstName = patientFirstNameInput.text.toString()
            val lastName = patientLastNameInput.text.toString()
            val department = patientDepartmentInput.text.toString()
            val nurseId = patientNurseIDInput.text.toString()
            val room = patientRoomNumberInput.text.toString()

            // create new patient object with the values
            val newPatient = Patient(
                patientId = patientId,
                firstName = firstName,
                lastName = lastName,
                department = department,
                nurseId = nurseId,
                room = room
            )
            //TODO: if no info entered need to be able to exit/error message

            // push the new patient to the database
            val databaseReference = FirebaseDatabase.getInstance().reference
            val newPatientReference = databaseReference.child("patients").push()
            newPatientReference.setValue(newPatient)

            Toast.makeText(this, "Patient Added", Toast.LENGTH_SHORT).show()

            //finish this activity to return to previous screen
            finish()
        }
    }
}