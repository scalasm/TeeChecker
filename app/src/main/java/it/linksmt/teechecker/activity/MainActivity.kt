package it.linksmt.teechecker.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import it.linksmt.teechecker.R
import it.linksmt.teechecker.adapter.HardwareCheckItemListAdapter
import it.linksmt.teechecker.util.KeyChainHardwareSupportHelper
import it.linksmt.teechecker.util.KeychainHardwareSupportoResult
import it.linksmt.teechecker.util.Result
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var adapter : HardwareCheckItemListAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = HardwareCheckItemListAdapter(this, emptyList() ) { item, view ->
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
        checkItemsListView.adapter = adapter

        val layoutManager = LinearLayoutManager( this )
        checkItemsListView.layoutManager = layoutManager
        checkItemsListView.setHasFixedSize( true )

        textStatus?.text = getString(R.string.status_ready)
//        checkItemsListView?.visibility = View.INVISIBLE

        btnPerformCheck.setOnClickListener {
            val result = KeyChainHardwareSupportHelper().check()

            handleResult( result )
        }
    }

    private fun handleResult(result: KeychainHardwareSupportoResult) {
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
