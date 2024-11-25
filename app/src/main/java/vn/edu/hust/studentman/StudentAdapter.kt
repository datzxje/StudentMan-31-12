package vn.edu.hust.studentman

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class StudentAdapter(context: Context, private val students: List<StudentModel>) :
  ArrayAdapter<StudentModel>(context, R.layout.layout_student_item, students) {

  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    val view: View = convertView ?: LayoutInflater.from(context)
      .inflate(R.layout.layout_student_item, parent, false)

    val student = students[position]

    val studentNameTextView: TextView = view.findViewById(R.id.text_student_name)
    val studentIdTextView: TextView = view.findViewById(R.id.text_student_id)

    studentNameTextView.text = student.studentName
    studentIdTextView.text = student.studentId

    return view
  }
}