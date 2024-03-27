package com.sarah.sarahshields_comp304lab3

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.Unit

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
        operator fun invoke(position: Int) : Unit
    }

    private var listener: ((Int) -> Unit)? = null
    fun setOnItemClickListener(listener: (position: Int) -> Unit) {
        this.listener = listener
        Log.d("PatientAdapter", "Listener set!")  // verify this is called
    }
    inner class PatientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(patient: Patient) {
            if (isNameOnly) {
                // Bind only the patient name
                val patientNameTextView = itemView.findViewById<TextView>(R.id.patientNameTextView)
                patientNameTextView.text = "${patient.firstName} ${patient.lastName}"

                itemView.setOnClickListener {
                    Log.d("PatientViewHolder", "onItemClick triggered!")  // Is this called on click?
                    listener?.invoke(adapterPosition)
                }
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
            }
        }
    }
}


