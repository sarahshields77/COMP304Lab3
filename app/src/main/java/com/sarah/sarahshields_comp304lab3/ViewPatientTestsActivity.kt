package com.sarah.sarahshields_comp304lab3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ViewPatientTestsActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TestAdapter
    private lateinit var tests: MutableList<Test>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_patient_tests)

        // receive selected patientID from intent in PatientMenuActivity
        val patientId = intent.getStringExtra("patientId") ?: ""
        Log.d("ViewPatientTestsActivity", "Received patientId: $patientId")

        loadTestDetails(patientId)

        recyclerView = findViewById(R.id.testRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        tests = mutableListOf()
        adapter = TestAdapter(tests)

        recyclerView.adapter = adapter

        // Load test details
        loadTestDetails(patientId)

        val backtopatientdetailsbutton = findViewById<Button>(R.id.backToPatientDetailsButton)
        backtopatientdetailsbutton.setOnClickListener {
            val intent = Intent(this, PatientDetailsActivity::class.java)
            startActivity(intent)
        }
    }
    private fun loadTestDetails(patientId: String) {
        val databaseReference = FirebaseDatabase.getInstance().reference
        val patientQuery = databaseReference.child("tests").orderByChild("patientId").equalTo(patientId)

        patientQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                tests.clear()
                for (testSnapshot in snapshot.children) {
                    val test = testSnapshot.getValue(Test::class.java)
                    test?.let { tests.add(it) }
                }
                adapter.notifyDataSetChanged() //notify the adapter
            }
            override fun onCancelled(error: DatabaseError) {
                // Handle query error
            }
        })
    }
}