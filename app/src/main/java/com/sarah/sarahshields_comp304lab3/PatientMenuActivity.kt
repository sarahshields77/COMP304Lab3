package com.sarah.sarahshields_comp304lab3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PatientMenuActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PatientAdapter
    private lateinit var patients: MutableList<Patient>
    private var selectedPatientPosition = -1 // initialize with default of no selection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_menu)

        recyclerView = findViewById(R.id.patientRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        patients = mutableListOf()
        adapter = PatientAdapter(patients, isNameOnly = true).apply {
            setOnItemClickListener(object : PatientAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    selectedPatientPosition = position
                }
            })
        }

        recyclerView.adapter = adapter

        val nurseId = intent.getStringExtra("nurseId") ?: ""
        val databaseReference = FirebaseDatabase.getInstance().reference

        // Query patients based on nurseId
        val query = databaseReference.child("patients").orderByChild("nurseId").equalTo(nurseId)

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                patients.clear()

                for (snapshot in dataSnapshot.children) {
                    val patient = snapshot.getValue(Patient::class.java)
                    patient?.let {
                        patients.add(it)
                    }
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })

        val addpatientbutton = findViewById<Button>(R.id.addPatientButton)
        addpatientbutton.setOnClickListener {
            val intent = Intent(this, NewPatientActivity::class.java)
            startActivity(intent)
        }

        val viewpatientbutton = findViewById<Button>(R.id.viewPatientButton)
        viewpatientbutton.setOnClickListener {
            if (selectedPatientPosition != -1) {
                val selectedPatientName = patients[selectedPatientPosition].firstName
                val intent = Intent(this, PatientDetailsActivity::class.java).apply {
                    putExtra("selectedPatientName", selectedPatientName)}
            } else {
                Toast.makeText(this, "Please select a patient", Toast.LENGTH_SHORT).show()
                }
            startActivity(intent)
        }
    }
}