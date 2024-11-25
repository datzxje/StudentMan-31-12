package vn.edu.hust.studentman

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class AddStudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val studentNameEditText: EditText = findViewById(R.id.edit_student_name)
        val studentIdEditText: EditText = findViewById(R.id.edit_student_id)
        val saveButton: Button = findViewById(R.id.btn_save_student)

        saveButton.setOnClickListener {
            val name = studentNameEditText.text.toString()
            val studentId = studentIdEditText.text.toString()
            if (name.isNotEmpty() && studentId.isNotEmpty()) {
                val resultIntent = Intent()
                resultIntent.putExtra("studentName", name)
                resultIntent.putExtra("studentId", studentId)
                setResult(RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

    }
}