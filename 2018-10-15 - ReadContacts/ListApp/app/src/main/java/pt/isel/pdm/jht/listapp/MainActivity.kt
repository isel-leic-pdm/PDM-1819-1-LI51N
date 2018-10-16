package pt.isel.pdm.jht.listapp

import android.os.AsyncTask
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    val text by lazy { findViewById<TextView>(R.id.text) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        object : AsyncTask<Void, Void, List<String>>() {

            override fun doInBackground(vararg params: Void?): List<String> {

                val contactsCursor = contentResolver.query(
                        ContactsContract.Contacts.CONTENT_URI,
                        arrayOf(
                                ContactsContract.Contacts._ID,
                                ContactsContract.Contacts.LOOKUP_KEY,
                                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
                        ),
                        null,
                        null,
                        null
                )

                if (contactsCursor == null) {
                    return listOf()
                }

                val contacts = mutableListOf<String>()
                if (contactsCursor.moveToFirst()) {
                    val nameIdx = contactsCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
                    do {
                        contacts.add(contactsCursor.getString(nameIdx))
                    } while (contactsCursor.moveToNext())
                }
                contactsCursor.close()
                return contacts
            }

            override fun onPostExecute(result: List<String>?) {
                val contactsStr = result?.joinToString("\n") ?: "NO RESULTS"
                text.text = contactsStr
            }
        }.execute()
    }
}
