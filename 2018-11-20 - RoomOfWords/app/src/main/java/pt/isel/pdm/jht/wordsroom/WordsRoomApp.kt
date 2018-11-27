package pt.isel.pdm.jht.wordsroom

import android.app.Application
import androidx.room.Room

class WordsRoomApp : Application() {

    val database by lazy {
        Room.databaseBuilder(this, WordsDatabase::class.java, "words_db").build()
    }

    val repository by lazy { WordsRepository(database.wordsDao()) }

}

val Application.repository
    get() = (this as WordsRoomApp).repository
