package vn.edu.hust.studentman

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [StudentEntity::class], version = 1, exportSchema = false)
abstract class StudentDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao

    companion object {
        @Volatile
        private var INSTANCE: StudentDatabase? = null

        fun getDatabase(context: Context): StudentDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudentDatabase::class.java,
                    "student_man"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}