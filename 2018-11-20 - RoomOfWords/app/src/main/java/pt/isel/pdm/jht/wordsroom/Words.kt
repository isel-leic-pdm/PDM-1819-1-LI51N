package pt.isel.pdm.jht.wordsroom

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "words")
data class Word(@PrimaryKey val word: String)

@Dao
interface WordsDao {

    @Insert
    fun insert(word: Word)

    @Query("DELETE FROM words")
    fun deleteAll()

    @Query("SELECT * FROM words ORDER BY word ASC")
    fun getAllWords() : LiveData<List<Word>>
}

@Database(entities = [Word::class], version = 1)
abstract class WordsDatabase : RoomDatabase() {
    abstract fun wordsDao(): WordsDao
}
