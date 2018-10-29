package pt.isel.pdm.jht.livecontacts

import android.content.ContentResolver
import android.database.ContentObserver
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract

object Contacts {

    private val handler by lazy { Handler(Looper.getMainLooper()) }

    fun loadContacts(resolver : ContentResolver, onDone : (List<ContactSummary>) -> Unit) {
        val loadTask = ContactsLoadTask({ contactsList -> onDone(contactsList) })
        loadTask.execute(resolver)
    }

    fun onContactsChanged(resolver : ContentResolver, onChanged : () -> Unit) {
        resolver.registerContentObserver(
                ContactsContract.Contacts.CONTENT_URI,
                false,
                object : ContentObserver(handler) {
                    override fun onChange(selfChange: Boolean) {
                        onChanged()
                    }
                }
        )
    }
}

class ContactsLoadTask(private val end : (List<ContactSummary>) -> Unit) : AsyncTask<ContentResolver, Void, List<ContactSummary>>() {

    override fun doInBackground(vararg params: ContentResolver): List<ContactSummary> {
        val contactsCursor = params[0].query(
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

        val contacts = mutableListOf<ContactSummary>()
        if (contactsCursor.moveToFirst()) {
            val nameIdx = contactsCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
            val keyIdx = contactsCursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY)
            do {
                contacts.add(
                        ContactSummary(
                            contactsCursor.getString(nameIdx),
                            contactsCursor.getString(keyIdx)
                        )
                )
            } while (contactsCursor.moveToNext())
        }
        contactsCursor.close()
        return contacts
    }

    override fun onPostExecute(result: List<ContactSummary>) {
        end(result)
    }
}
