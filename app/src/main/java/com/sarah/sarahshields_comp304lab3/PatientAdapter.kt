package com.sarah.sarahshields_comp304lab3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PatientAdapter(private val patients: List<Patient>, private val isNameOnly: Boolean) :
    RecyclerView.Adapter<PatientAdapter.PatientViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val layoutResId = if (isNameOnly) R.layout.item_patient_name else R.layout.item_patient_details
        val view = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        return PatientViewHolder(view)
    }
    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val patient = patients[position]
        holder.bind(patient)
    }
    override fun getItemCount(): Int {
        return patients.size
    }
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
    inner class PatientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(patient: Patient) {
            if (isNameOnly) {
                // Bind only the patient name
                val patientNameTextView = itemView.findViewById<TextView>(R.id.patientNameTextView)
                patientNameTextView.text = "${patient.firstName} ${patient.lastName}"
            } else {
                // Bind full patient details
                val patientIDTextView = itemView.findViewById<TextView>(R.id.patientIDTextView)
                val patientNameTextView = itemView.findViewById<TextView>(R.id.patientNameTextView)
                val patientDepartmentTextView = itemView.findViewById<TextView>(R.id.patientDepartmentTextView)
                val patientNurseTextView = itemView.findViewById<TextView>(R.id.patientNurseTextView)
                val patientRoomTextView = itemView.findViewById<TextView>(R.id.patientRoomTextView)

                patientIDTextView.text = "${patient.patientId}"
                patientNameTextView.text = "${patient.firstName} ${patient.lastName}"
                patientDepartmentTextView.text = "${patient.department}"
                patientNurseTextView.text = "${patient.nurseId}"
                patientRoomTextView.text = "${patient.room}"

                itemView.setOnClickListener {
                    listener?.onItemClick(adapterPosition)
                }
            }
        }
    }
}

