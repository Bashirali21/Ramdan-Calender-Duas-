package com.example.ramzancalender.room
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ramzancalender.models.FilteredModel

@Database(
    entities = [FilteredModel::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class PostDb: RoomDatabase() {
    abstract val    dao: RamzanDao
}