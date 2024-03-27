package com.sarah.sarahshields_comp304lab3

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TestAdapter(private val tests: List<Test>) : RecyclerView.Adapter<TestAdapter.TestViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        val layoutResId = R.layout.item_patient_test
        val view = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        return TestViewHolder(view)
    }
    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        val test = tests[position]
        holder.bind(test)
    }
    override fun getItemCount(): Int {
        return tests.size
    }
    interface OnItemClickListener {
        operator fun invoke(position: Int) : Unit
    }

    private var listener: ((Int) -> Unit)? = null
    fun setOnItemClickListener(listener: (position: Int) -> Unit) {
        this.listener = listener
        Log.d("TestAdapter", "Listener set!")  // verify this is called
    }
    inner class TestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(test: Test) {
            // Bind test details
            val testIDTextView = itemView.findViewById<TextView>(R.id.testIDTextView)
            val patientIDTextView = itemView.findViewById<TextView>(R.id.patientIDTextView)
            val patientNurseTextView = itemView.findViewById<TextView>(R.id.patientNurseTextView)
            val BPLTextView = itemView.findViewById<TextView>(R.id.BPLTextView)
            val BPHTextView = itemView.findViewById<TextView>(R.id.BPHTextView)
            val temperatureTextView = itemView.findViewById<TextView>(R.id.temperatureTextView)

            testIDTextView.text = "${test.testId}"
            patientIDTextView.text = "${test.patientId}"
            patientNurseTextView.text = "${test.nurseId}"
            BPLTextView.text = "${test.bpl}"
            BPHTextView.text = "${test.bph}"
            temperatureTextView.text = "${test.temperature}"
            Log.d("TestAdapter", "BPL value: ${test.bpl}")
            Log.d("TestAdapter", "BPH value: ${test.bph}")
        }
    }
}