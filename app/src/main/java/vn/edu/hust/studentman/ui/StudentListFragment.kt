package vn.edu.hust.studentman.ui

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import vn.edu.hust.studentman.R
import vn.edu.hust.studentman.StudentAdapter
import vn.edu.hust.studentman.StudentDatabase
import vn.edu.hust.studentman.StudentEntity

class StudentListFragment : Fragment() {

    private lateinit var studentListView: ListView
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var studentDatabase: StudentDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_students, container, false)
        studentListView = view.findViewById(R.id.list_view_students)
        studentDatabase = StudentDatabase.getDatabase(requireContext())
        studentAdapter = StudentAdapter(requireContext(), listOf())
        studentListView.adapter = studentAdapter
        registerForContextMenu(studentListView)
        setHasOptionsMenu(true)

        lifecycleScope.launch {
            val students = studentDatabase.studentDao().getAllStudents()
            studentAdapter.updateStudents(students)
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                findNavController().navigate(R.id.action_studentListFragment_to_addEditStudentFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateStudentList() {
        lifecycleScope.launch {
            val students = studentDatabase.studentDao().getAllStudents()
            studentAdapter.updateStudents(students)
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = requireActivity().menuInflater
        inflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId) {
            R.id.action_edit -> {
                val selectedStudent = studentAdapter.getItem(info.position) as StudentEntity
                val bundle = Bundle().apply {
                    putString("studentId", selectedStudent.id)
                    putString("studentName", selectedStudent.name)
                }
                findNavController().navigate(R.id.action_studentListFragment_to_addEditStudentFragment, bundle)
                true
            }
            R.id.action_remove -> {
                val student = studentAdapter.getItem(info.position) as StudentEntity
                lifecycleScope.launch {
                    studentDatabase.studentDao().deleteStudent(student)
                    updateStudentList()
                }

                Snackbar.make(requireView(), "Đã xóa sinh viên", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        lifecycleScope.launch {
                            studentDatabase.studentDao().insertStudent(student)
                            updateStudentList()
                        }
                    }.show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}