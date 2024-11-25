package vn.edu.hust.studentman

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class EditStudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Retrieve the passed data
        val student = intent.getSerializableExtra("student") as? StudentModel
        val position = intent.getIntExtra("position", -1)

        val studentNameEditText: EditText = findViewById(R.id.edit_student_name)
        val studentIdEditText: EditText = findViewById(R.id.edit_student_id)
        val saveButton: Button = findViewById(R.id.btn_save_student)

        if (student != null && position != -1) {
            // Pre-fill fields with the student's data
            studentNameEditText.setText(student.studentName)
            studentIdEditText.setText(student.studentId)

            saveButton.setOnClickListener {
                val updatedName = studentNameEditText.text.toString()
                val updatedId = studentIdEditText.text.toString()

                if (updatedName.isNotEmpty() && updatedId.isNotEmpty()) {
                    val updatedStudent = StudentModel(updatedName, updatedId)
                    val resultIntent = Intent()
                    resultIntent.putExtra("updatedStudent", updatedStudent)
                    resultIntent.putExtra("position", position)
                    setResult(RESULT_OK, resultIntent)
                    finish()
                }
            }
        } else {
            // Handle error gracefully
            finish()
        }
    }
}
