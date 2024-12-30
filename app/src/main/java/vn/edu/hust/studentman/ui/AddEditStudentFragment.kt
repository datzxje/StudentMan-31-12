package vn.edu.hust.studentman.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import vn.edu.hust.studentman.R
import vn.edu.hust.studentman.StudentDatabase
import vn.edu.hust.studentman.StudentEntity

class AddEditStudentFragment : Fragment() {

    private lateinit var studentDatabase: StudentDatabase
    private lateinit var editTextName: EditText
    private lateinit var editTextId: EditText
    private lateinit var buttonSave: Button
    private var studentId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_edit_student, container, false)
        studentDatabase = StudentDatabase.getDatabase(requireContext())
        editTextName = view.findViewById(R.id.edit_text_name)
        editTextId = view.findViewById(R.id.edit_text_id)
        buttonSave = view.findViewById(R.id.button_save)

        studentId = arguments?.getString("studentId")
        val studentName = arguments?.getString("studentName")

        studentId?.let {
            editTextId.setText(it)
            editTextId.isEnabled = false // ID không thay đổi khi chỉnh sửa
        }
        editTextName.setText(studentName)

        buttonSave.setOnClickListener {
            val name = editTextName.text.toString()
            val id = editTextId.text.toString()

            lifecycleScope.launch {
                if (studentId == null) {
                    // Add new student
                    studentDatabase.studentDao().insertStudent(StudentEntity(id, name))
                } else {
                    // Edit existing student
                    studentDatabase.studentDao().updateStudent(StudentEntity(id, name))
                }
                findNavController().navigateUp()
            }
        }

        return view
    }
}
