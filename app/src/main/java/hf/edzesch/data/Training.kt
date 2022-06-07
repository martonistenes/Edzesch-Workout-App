package hf.edzesch.data

import androidx.room.*

@Entity(tableName = "training")
data class Training(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "type") var type: Type,
    @ColumnInfo(name = "distance") var distance: Double,
    @ColumnInfo(name = "length") var length: Double,
    @ColumnInfo(name = "location") var location: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "selectedDate") var selectedDate: String
) {
    enum class Type {
        KAYAKING, RUNNING, SWIMMING, LIFTING, CYCLING, ELSE ;
        companion object {
            @JvmStatic
            @TypeConverter
            fun getByOrdinal(ordinal: Int): Type? {
                var ret: Type? = null
                for (type in values()) {
                    if (type.ordinal == ordinal) {
                        ret = type
                        break
                    }
                }
                return ret
            }

            @JvmStatic
            @TypeConverter
            fun toInt(category: Type): Int {
                return category.ordinal
            }
        }
    }
}
