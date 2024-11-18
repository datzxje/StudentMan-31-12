package vn.edu.hust.studentman

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
  private lateinit var studentAdapter: StudentAdapter
  private lateinit var recyclerView: RecyclerView
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
  private var lastDeletedStudent: StudentModel? = null
  private var lastDeletedPosition: Int = -1

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    recyclerView = findViewById(R.id.recycler_view_students)
    studentAdapter = StudentAdapter(students,
      onEditClick = { position -> showEditDialog(position) },
      onDeleteClick = { position -> deleteStudent(position) }
    )

    recyclerView.apply {
      adapter = studentAdapter
      layoutManager = LinearLayoutManager(this@MainActivity)
    }

    findViewById<Button>(R.id.btn_add_new).setOnClickListener {
      showAddDialog()
    }
  }

  @SuppressLint("MissingInflatedId")
  private fun showAddDialog() {
    val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit_student, null)
    val nameInput = dialogView.findViewById<EditText>(R.id.input_student_name)
    val idInput = dialogView.findViewById<EditText>(R.id.input_student_id)

    AlertDialog.Builder(this)
      .setTitle("Thêm sinh viên mới")
      .setView(dialogView)
      .setPositiveButton("Thêm") { _, _ ->
        val name = nameInput.text.toString()
        val id = idInput.text.toString()
        if (name.isNotEmpty() && id.isNotEmpty()) {
          students.add(StudentModel(name, id))
          studentAdapter.notifyItemInserted(students.size - 1)
        }
      }
      .setNegativeButton("Hủy", null)
      .create()
      .show()
  }

  @SuppressLint("MissingInflatedId")
  private fun showEditDialog(position: Int) {
    val student = students[position]
    val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit_student, null)
    val nameInput = dialogView.findViewById<EditText>(R.id.input_student_name)
    val idInput = dialogView.findViewById<EditText>(R.id.input_student_id)

    nameInput.setText(student.studentName)
    idInput.setText(student.studentId)

    AlertDialog.Builder(this)
      .setTitle("Chỉnh sửa thông tin sinh viên")
      .setView(dialogView)
      .setPositiveButton("Lưu") { _, _ ->
        val name = nameInput.text.toString()
        val id = idInput.text.toString()
        if (name.isNotEmpty() && id.isNotEmpty()) {
          students[position] = StudentModel(name, id)
          studentAdapter.notifyItemChanged(position)
        }
      }
      .setNegativeButton("Hủy", null)
      .create()
      .show()
  }

  private fun deleteStudent(position: Int) {
    AlertDialog.Builder(this)
      .setTitle("Xác nhận xóa")
      .setMessage("Bạn có chắc chắn muốn xóa sinh viên này không?")
      .setPositiveButton("Xóa") { _, _ ->
        // Nếu người dùng xác nhận, tiến hành xóa
        lastDeletedStudent = students[position]
        lastDeletedPosition = position
        students.removeAt(position)
        studentAdapter.notifyItemRemoved(position)

        // Hiển thị Snackbar để hoàn tác
        Snackbar.make(recyclerView, "Đã xóa sinh viên", Snackbar.LENGTH_LONG)
          .setAction("Hoàn tác") {
            lastDeletedStudent?.let {
              students.add(lastDeletedPosition, it)
              studentAdapter.notifyItemInserted(lastDeletedPosition)
            }
          }
          .show()
      }
      .setNegativeButton("Hủy", null)
      .create()
      .show()
  }

}


