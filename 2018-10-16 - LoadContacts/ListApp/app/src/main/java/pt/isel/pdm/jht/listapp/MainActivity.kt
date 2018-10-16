package pt.isel.pdm.jht.listapp

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import android.support.v4.app.LoaderManager
import android.support.v4.content.ContextCompat
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import android.widget.Toast

class MainActivity : FragmentActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    private val REQUEST_READ_CONTACTS = 5

    private val LOAD_CONTACTS = 2

    val listView by lazy { findViewById<ListView>(R.id.listView) }

    val cursorAdapter by lazy {
        SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                null,
                arrayOf(
                        ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
                        ContactsContract.Contacts.LOOKUP_KEY
                ),
                intArrayOf(
                        android.R.id.text1,
                        android.R.id.text2
                ),
                0
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView.adapter = cursorAdapter

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    REQUEST_READ_CONTACTS
            )

        } else {
            continueOnCreate()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                continueOnCreate()
            } else {
                Toast.makeText(this, "-- NO PERMISSION --", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun continueOnCreate() {
        LoaderManager.getInstance(this).initLoader(LOAD_CONTACTS, null, this)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        if (id == LOAD_CONTACTS) {
            Toast.makeText(this, "NEW LOADER", Toast.LENGTH_LONG).show()
            return CursorLoader(
                    this,
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
        }
        throw IllegalArgumentException("id unknown")
    }

    override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor?) {
        Toast.makeText(this, "LOAD FINISHED", Toast.LENGTH_LONG).show()
        cursorAdapter.swapCursor(cursor)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        Toast.makeText(this, "LOADER RESET", Toast.LENGTH_LONG).show()
        cursorAdapter.swapCursor(null)
    }
}
