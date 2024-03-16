package com.sarah.sarahshields_comp304lab3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.Button
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_menu)

        recyclerView = findViewById(R.id.patientRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        patients = mutableListOf()
        adapter = PatientAdapter(patients)

        recyclerView.adapter = adapter

        // TODO: Assuming you have a reference to your Firebase database
        val databaseReference = FirebaseDatabase.getInstance().reference

        // TODO: Assuming nurseId is obtained from somewhere (e.g., login)
        val nurseId = "123" // Replace with actual nurseId retrieval

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
            val intent = Intent(this, PatientDetailsActivity::class.java)
            startActivity(intent)
        }
    }
}