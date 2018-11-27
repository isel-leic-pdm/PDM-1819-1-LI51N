package pt.isel.pdm.jht.wordsroom

import android.os.AsyncTask

class WordsRepository(private val wordsDao: WordsDao) {

    val allWords = wordsDao.getAllWords()

    fun insert(word: Word) = InsertTask(wordsDao).execute(word)

    fun deleteAll() = DeleteAllTask(wordsDao).execute()

}

class InsertTask(private val wordsDao: WordsDao) : AsyncTask<Word, Void, Unit>() {
    override fun doInBackground(vararg words: Word) {
        wordsDao.insert(words[0])
    }
}

class DeleteAllTask(private val wordsDao: WordsDao) : AsyncTask<Void, Void, Unit>() {
    override fun doInBackground(vararg nothing: Void) {
        wordsDao.deleteAll()
    }
}

/*
class WordsRepository() {

    private val words = mutableListOf<Word>()

    val allWords: MutableLiveData<List<Word>> =
            MutableLiveData<List<Word>>().let { it.value = words; it }

    fun insert(word: Word) {
        words.add(word)
        allWords.value = words
    }

    fun deleteAll() {
        words.clear()
        allWords.value = words
    }

}
*/