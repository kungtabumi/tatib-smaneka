package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.model.BookmarkEntity
import com.example.data.model.HandlingProcedureEntity
import com.example.data.model.LearningTopicEntity
import com.example.data.model.QuizQuestionEntity
import com.example.data.model.ReadingHistoryEntity
import com.example.data.model.RegulationEntity
import com.example.data.model.ViolationEntity
import com.example.data.model.VisualStandardEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        RegulationEntity::class,
        ViolationEntity::class,
        HandlingProcedureEntity::class,
        VisualStandardEntity::class,
        LearningTopicEntity::class,
        QuizQuestionEntity::class,
        BookmarkEntity::class,
        ReadingHistoryEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tatibDao(): TatibDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "smaneka_tatib_database"
                )
                    .addCallback(TatibDatabaseCallback(scope))
                    .fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class TatibDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.tatibDao())
                }
            }
        }

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    if (database.tatibDao().getRegulationsCount() == 0) {
                        populateDatabase(database.tatibDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(dao: TatibDao) {
            TatibDatabaseSeeder.seed(dao)
        }
    }
}
