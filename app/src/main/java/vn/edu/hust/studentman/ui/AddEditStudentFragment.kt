package vn.edu.hust.studentman.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import vn.edu.hust.studentman.R
import vn.edu.hust.studentman.StudentModel

class AddEditStudentFragment : Fragment() {

    private lateinit var editTextName: EditText
    private lateinit var editTextId: EditText
    private lateinit var buttonSave: Button
    private var student: StudentModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_edit_student, container, false)
        editTextName = view.findViewById(R.id.edit_text_name)
        editTextId = view.findViewById(R.id.edit_text_id)
        buttonSave = view.findViewById(R.id.button_save)

        student = arguments?.getSerializable("student") as? StudentModel
        student?.let {
            editTextName.setText(it.studentName)
            editTextId.setText(it.studentId)
        }

        buttonSave.setOnClickListener {
            val name = editTextName.text.toString()
            val id = editTextId.text.toString()
            if (student == null) {
                // Add new student
                val newStudent = StudentModel(name, id)
                // Pass the new student back to StudentListFragment
                findNavController().previousBackStackEntry?.savedStateHandle?.set("newStudent", newStudent)
            } else {
                // Edit existing student
                student?.studentName = name
                student?.studentId = id
                // Pass the edited student back to StudentListFragment
                findNavController().previousBackStackEntry?.savedStateHandle?.set("editedStudent", student)
            }
            findNavController().popBackStack()
        }

        return view
    }
}