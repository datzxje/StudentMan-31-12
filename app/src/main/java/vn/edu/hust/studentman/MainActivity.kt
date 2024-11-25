package vn.edu.hust.studentman

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

  val students = mutableListOf(
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


  private lateinit var studentAdapter: StudentAdapter
  private lateinit var listView: ListView

  @SuppressLint("MissingInflatedId")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Thiết lập toolbar
    val toolbar: Toolbar = findViewById(R.id.toolbar)
    setSupportActionBar(toolbar)

    // Thiết lập ListView
    listView = findViewById(R.id.list_view_students)
    studentAdapter = StudentAdapter(this, students)
    listView.adapter = studentAdapter

    // Đăng ký context menu cho ListView
    registerForContextMenu(listView)
  }

  // Tạo OptionMenu
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu)
    return true
  }

  // Xử lý khi chọn mục trong OptionMenu
  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.menu_add -> {
        // Mở Activity để thêm sinh viên mới
        val intent = Intent(this, AddStudentActivity::class.java)
        startActivityForResult(intent, REQUEST_ADD_STUDENT)
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  // Tạo ContextMenu cho từng mục trong ListView
  override fun onCreateContextMenu(
    menu: ContextMenu?,
    v: View?,
    menuInfo: ContextMenu.ContextMenuInfo?
  ) {
    super.onCreateContextMenu(menu, v, menuInfo)
    menuInflater.inflate(R.menu.context_menu, menu)
  }

  // Xử lý khi chọn mục trong ContextMenu
  override fun onContextItemSelected(item: MenuItem): Boolean {
    val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
    val selectedStudent = students[info.position]

    return when (item.itemId) {
      R.id.menu_edit -> {
        // Pass selected student and its position to the EditStudentActivity
        val intent = Intent(this, EditStudentActivity::class.java)
        intent.putExtra("student", selectedStudent)
        intent.putExtra("position", info.position)
        startActivityForResult(intent, REQUEST_EDIT_STUDENT)
        true
      }
      R.id.menu_remove -> {
        students.removeAt(info.position)
        studentAdapter.notifyDataSetChanged()
        Toast.makeText(this, "Đã xóa sinh viên", Toast.LENGTH_SHORT).show()
        true
      }
      else -> super.onContextItemSelected(item)
    }
  }


  // Xử lý kết quả trả về từ các Activity
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode == RESULT_OK && data != null) {
      when (requestCode) {
        REQUEST_ADD_STUDENT -> {
          val name = data.getStringExtra("studentName") ?: ""
          val id = data.getStringExtra("studentId") ?: ""
          students.add(StudentModel(name, id))
          studentAdapter.notifyDataSetChanged()
        }
        REQUEST_EDIT_STUDENT -> {
          val updatedStudent = data.getSerializableExtra("updatedStudent") as? StudentModel
          val position = data.getIntExtra("position", -1)

          if (updatedStudent != null && position != -1) {
            students[position] = updatedStudent
            studentAdapter.notifyDataSetChanged()
          }
        }
      }
    }
  }



  companion object {
    const val REQUEST_ADD_STUDENT = 1
    const val REQUEST_EDIT_STUDENT = 2
  }
}