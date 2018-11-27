package pt.isel.pdm.jht.wordsroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val model by lazy {
        ViewModelProviders.of(this)
                .get(WordsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = WordsAdapter(this)
        listView.adapter = adapter
        listView.layoutManager = LinearLayoutManager(this)

        model.allWords.observe(this, Observer { words ->
            adapter.setWords(words)
        })
    }

    fun onAdd(view: View) {
        model.add(Word(wordBox.text.toString()))
    }

    fun onClear(view: View) {
        model.clear()
    }
}
