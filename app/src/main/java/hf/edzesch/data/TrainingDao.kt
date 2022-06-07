package hf.edzesch.data

import androidx.room.*

@Dao
interface TrainingDao {
    @Query("SELECT * FROM training")
    fun getAll(): List<Training>

    @Query("SELECT * FROM training WHERE selectedDate =:date")
    fun getDay(date: String): List<Training>

    @Query("SELECT MAX(distance) FROM training WHERE type=:t")
    fun getLongestDistance(t:Training.Type): Double

    @Query("SELECT AVG(distance) FROM training WHERE type=:t")
    fun getAverageDistance(t:Training.Type): Double

    @Query("SELECT MAX(length) FROM training WHERE type=:t")
    fun getLongest(t:Training.Type): Double

    @Query("SELECT AVG(length) FROM training WHERE type=:t")
    fun getAverageLength(t:Training.Type): Double

    @Query("SELECT SUM(length) FROM training WHERE type=:t")
    fun getTotalTime(t:Training.Type): Double

    @Query("SELECT SUM(distance) FROM training WHERE type=:t")
    fun getTotalDistance(t:Training.Type): Double

    @Insert
    fun insert(trainings: Training): Long

    @Update
    fun update(training: Training)

    @Delete
    fun deleteItem(training: Training)
}
