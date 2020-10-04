package com.virginiaprivacy.rpdid

import com.virginiaprivacy.rpdid.copdata.Cop
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Application : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private val copList: Flow<Cop> = flow {
        "https://rpdid.virginiaprivacy.com/api/cops"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = applicationContext
        val preferences = getPreferences(Context.MODE_PRIVATE)
        if (preferences.getBoolean(FIRST_RUN_PREF_KEY, true)) {
            AlertDialog.Builder(context).apply {
                setCancelable(false)
                setTitle("Save Images To Device?")
                setMessage(getString(R.string.offline_storage_dialog_message))
                setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
                    // TODO: offline storage
                }
                setNegativeButton("No") { dialogInterface: DialogInterface, i: Int -> }
            }
        }
        setContentView(R.layout.activity_item_detail)

        val navController = findNavController(R.id.nav_host_fragment_item_detail)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_item_detail)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    companion object {
        const val FIRST_RUN_PREF_KEY = "first_run"
    }
}

lateinit var context: Context

val cops = HashMap<Int, Cop>()