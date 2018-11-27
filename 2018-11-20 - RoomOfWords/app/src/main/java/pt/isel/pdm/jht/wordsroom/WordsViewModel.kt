package pt.isel.pdm.jht.wordsroom

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class WordsViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = app.repository

    val allWords = repository.allWords

    fun add(word: Word) {
        repository.insert(word)
    }

    fun clear() {
        repository.deleteAll()
    }
}
