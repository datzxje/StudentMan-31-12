package vn.edu.hust.studentman.ui

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import vn.edu.hust.studentman.R
import vn.edu.hust.studentman.StudentAdapter
import vn.edu.hust.studentman.StudentModel

class StudentListFragment : Fragment() {

    private lateinit var studentListView: ListView
    private lateinit var studentAdapter: StudentAdapter
    private val students = mutableListOf(
        StudentModel("Nguyễn Văn An", "SV001"),
        StudentModel("Trần Thị Bảo", "SV002"),
        StudentModel("Lê Hoàng Cường", "SV003"),
        StudentModel("Phạm Thị Dung", "SV004"),
        StudentModel("Đỗ Minh Đức", "SV005"),
        StudentModel("Vũ Thị Hoa", "SV006"),
        StudentModel("Hoàng Văn Hải", "SV007"),
        StudentModel("Bùi Thị Hạnh", "SV008"),
        StudentModel("Đinh Văn Hùng", "SV009"),
        StudentModel("Nguyễn Thị Linh", "SV010"),
        StudentModel("Phạm Văn Long", "SV011"),
        StudentModel("Trần Thị Mai", "SV012"),
        StudentModel("Lê Thị Ngọc", "SV013"),
        StudentModel("Vũ Văn Nam", "SV014"),
        StudentModel("Hoàng Thị Phương", "SV015"),
        StudentModel("Đỗ Văn Quân", "SV016"),
        StudentModel("Nguyễn Thị Thu", "SV017"),
        StudentModel("Trần Văn Tài", "SV018"),
        StudentModel("Phạm Thị Tuyết", "SV019"),
        StudentModel("Lê Văn Vũ", "SV020")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_students, container, false)
        studentListView = view.findViewById(R.id.list_view_students)
        studentAdapter = StudentAdapter(requireContext(), students)
        studentListView.adapter = studentAdapter
        registerForContextMenu(studentListView)
        setHasOptionsMenu(true)

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<StudentModel>("newStudent")
            ?.observe(viewLifecycleOwner) { newStudent ->
                students.add(newStudent)
                studentAdapter.notifyDataSetChanged()
            }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<StudentModel>("editedStudent")
            ?.observe(viewLifecycleOwner) { editedStudent ->
                val index = students.indexOfFirst { it.studentId == editedStudent.studentId }
                if (index != -1) {
                    students[index] = editedStudent
                    studentAdapter.notifyDataSetChanged()
                }
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

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = requireActivity().menuInflater
        inflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId) {
            R.id.action_edit -> {
                val selectedStudent = students[info.position]
                val bundle = Bundle().apply {
                    putSerializable("student", selectedStudent)
                }
                findNavController().navigate(R.id.action_studentListFragment_to_addEditStudentFragment, bundle)
                true
            }
            R.id.action_remove -> {
                val removedStudent = students.removeAt(info.position)
                studentAdapter.notifyDataSetChanged()

                // Show Snackbar with undo option
                Snackbar.make(requireView(), "Đã xóa sinh viên", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        students.add(info.position, removedStudent)
                        studentAdapter.notifyDataSetChanged()
                    }.show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}