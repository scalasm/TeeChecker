package it.linksmt.teechecker.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import it.linksmt.teechecker.R
import it.linksmt.teechecker.adapter.CheckItemListAdapter
import it.linksmt.teechecker.model.CheckItemViewModel
import it.linksmt.teechecker.util.CheckItem
import it.linksmt.teechecker.util.KeyChainHardwareSupportHelper
import it.linksmt.teechecker.util.CheckItemsResult
import it.linksmt.teechecker.util.Result
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var adapter : CheckItemListAdapter

    lateinit var model : CheckItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model = ViewModelProviders.of(this).get(CheckItemViewModel::class.java)
        model.result.observe(this, Observer { result -> handleResult(result!!) } )

        adapter = CheckItemListAdapter(this, emptyList() ) { item, view ->
            showTooltip( item, view )
        }
        checkItemsListView.adapter = adapter

        val layoutManager = LinearLayoutManager( this )
        checkItemsListView.layoutManager = layoutManager
        checkItemsListView.setHasFixedSize( true )

        textStatus?.text = getString(R.string.status_ready)

        btnPerformCheck.setOnClickListener {
            val result = KeyChainHardwareSupportHelper().check()
            model.result.postValue( result )
        }
    }

    private fun showTooltip(item: CheckItem, view: View) {
        val snackbar = Snackbar
                .make( view, getText( item.hintResourceId ), Snackbar.LENGTH_INDEFINITE)
                .setAction("OK") {
                    // Nothing
                }
        // Just allow the Snackbar to be a bit taller!
        val snackbarView = snackbar.view
        val textView = snackbarView.findViewById<View>(android.support.design.R.id.snackbar_text) as TextView
        textView.maxLines = 10  // show multiple line

        snackbar.show()
    }

    private fun handleResult(result: CheckItemsResult) {
        when (result.result) {
            Result.OK -> {
                textStatus?.text = getString(R.string.status_success)
            }
            Result.ANDROID_VERSION_OLDER_THAN_6_0 -> {
                textStatus?.text = getString(R.string.device_not_supported)
            }
            else -> {
                val errorMessage = if (result.hasErrorMessage) result.errorMessage else getString(R.string.status_unknown_error)

                textStatus?.text = errorMessage
            }
        }

        adapter.updateData( result.items )
    }
}
