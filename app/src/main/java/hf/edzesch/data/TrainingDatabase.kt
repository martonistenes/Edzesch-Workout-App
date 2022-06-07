package hf.edzesch.data

import android.content.Context
import androidx.room.*

@Database(entities = [Training::class], version = 1)
@TypeConverters(value = [Training.Type::class])
abstract class TrainingDatabase : RoomDatabase() {
    abstract fun TrainingDao(): TrainingDao

    companion object {
        fun getDatabase(applicationContext: Context): TrainingDatabase {
            return Room.databaseBuilder(
                applicationContext,
                TrainingDatabase::class.java,
                "trainings"
            ).build();
        }
    }
}
