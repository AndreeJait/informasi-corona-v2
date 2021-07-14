package student.if419014.informasicorona.repo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import student.if419014.informasicorona.model.response.GetAllResponse
import student.if419014.informasicorona.repo.dao.AllDao

@Database(entities = [GetAllResponse::class], version = 1, exportSchema = false)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun allDao():AllDao
    companion object{
        @Volatile private var instance : AppDatabase ? = null
        private val  LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance?:buildDatabase(context).also{
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "corona_info"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}