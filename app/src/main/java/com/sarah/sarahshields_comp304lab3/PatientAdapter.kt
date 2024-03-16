package com.sarah.sarahshields_comp304lab3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PatientAdapter(private val patients: List<Patient>) :
    RecyclerView.Adapter<PatientAdapter.PatientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_patient, parent, false)
        return PatientViewHolder(view)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val patient = patients[position]
        holder.bind(patient)
    }

    override fun getItemCount(): Int {
        return patients.size
    }

    inner class PatientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Initialize TextView variables
        private val patientIDTextView: TextView = itemView.findViewById(R.id.patientIDTextView)
        private val patientNameTextView: TextView = itemView.findViewById(R.id.patientNameTextView)
        private val patientDepartmentTextView: TextView = itemView.findViewById(R.id.patientDepartmentTextView)
        private val patientNurseTextView: TextView = itemView.findViewById(R.id.patientNurseTextView)
        private val patientRoomTextView: TextView = itemView.findViewById(R.id.patientRoomTextView)

        fun bind(patient: Patient) {
            // Bind patient data to UI elements in the item layout
            patientIDTextView.text = "${patient.patientId}"
            patientNameTextView.text = "${patient.firstName} ${patient.lastName}"
            patientDepartmentTextView.text = "${patient.department}"
            patientNurseTextView.text = "${patient.nurseId}"
            patientRoomTextView.text = "${patient.room}"
        }
    }
}
