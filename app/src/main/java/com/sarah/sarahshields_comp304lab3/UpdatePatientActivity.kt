package com.sarah.sarahshields_comp304lab3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class UpdatePatientActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_patient)

        // get patient details passed from PatientDetailsActivity
        val patientId = intent.getStringExtra("patientId")
        val firstName = intent.getStringExtra("firstName")
        val lastName = intent.getStringExtra("lastName")
        val department = intent.getStringExtra("department")
        val nurseId = intent.getStringExtra("nurseId")
        val room = intent.getStringExtra("room")

        // populate EditTexts with patient details
        val patientIDInput = findViewById<EditText>(R.id.patientIDInput)
        patientIDInput.setText(patientId)
        val patientLastNameInput = findViewById<EditText>(R.id.patientLastNameInput)
        patientLastNameInput.setText(lastName)
        val patientFirstNameInput = findViewById<EditText>(R.id.patientFirstNameInput)
        patientFirstNameInput.setText(firstName)
        val patientDepartmentInput = findViewById<EditText>(R.id.patientDepartmentInput)
        patientDepartmentInput.setText(department)
        val patientNurse = findViewById<EditText>(R.id.patientNurseIDInput)
        patientNurse.setText(nurseId)
        val patientRoom = findViewById<EditText>(R.id.patientRoomNumberInput)
        patientRoom.setText(room)

        val updatePageButton = findViewById<Button>(R.id.updatePageButton)
        updatePageButton.setOnClickListener {
            // retrieve updated details from EditTexts
            val updateFirstName = patientFirstNameInput.text.toString()
            val updateLastName = patientLastNameInput.text.toString()
            val updateDepartment = patientDepartmentInput.text.toString()
            val updateNurseId = patientNurse.text.toString()
            val updateRoom = patientRoom.text.toString()

            // get reference to the specific patient in the database using PatientID
            val databaseReference = FirebaseDatabase.getInstance().reference
            val patientReference = databaseReference.child("patients").child(patientId!!)

            // update the patient's details
            patientReference.child("firstName").setValue(updateFirstName)
            patientReference.child("lastName").setValue(updateLastName)
            patientReference.child("department").setValue(updateDepartment)
            patientReference.child("nurseId").setValue(updateNurseId)
            patientReference.child("room").setValue(updateRoom)

            Toast.makeText(this, "Patient Updated", Toast.LENGTH_SHORT).show()

            //finish this activity to return to previous screen
            finish()

        }
    }
}
