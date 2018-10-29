package pt.isel.pdm.jht.livecontacts

import android.app.Application
import android.database.ContentObserver
import android.provider.ContactsContract
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

data class ContactSummary(val name : String, val key : String)

class ContactsViewModel(private val app : Application) : AndroidViewModel(app) {

    val contacts = MutableLiveData<List<ContactSummary>>()

    init {
        loadContacts()
        Contacts.onContactsChanged(app.contentResolver) {
            loadContacts()
        }
    }

    private fun loadContacts() {
        Toast.makeText(app, "LOADING", Toast.LENGTH_LONG).show()
        Contacts.loadContacts(app.contentResolver) {
            contactsList -> contacts.value = contactsList
        }
    }
}
