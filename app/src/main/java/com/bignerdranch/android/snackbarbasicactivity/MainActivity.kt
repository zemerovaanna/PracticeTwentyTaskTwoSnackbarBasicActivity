package com.bignerdranch.android.snackbarbasicactivity

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bignerdranch.android.snackbarbasicactivity.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var shPref: SharedPreferences
    var temp = 0
    var count = ""


    private lateinit var snackbar: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab?.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        shPref = getPreferences(MODE_PRIVATE)
        if(shPref.getString("count", "").toString().isNotEmpty())
        {
            temp = shPref.getString("count", "").toString().toInt()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    //Функционал кнопок:

    fun ButtonOne(view: View) {
        Snackbar.make(view,"Вы нажали кнопку Стандартный Snackbar", Snackbar.LENGTH_LONG).show()
    }

    fun ButtonTwo(view: View) {
/*        snackbar.setActionTextColor(Color.YELLOW)*/
        snackbar = Snackbar.make(view,"Вы нажали кнопку Snackbar с кнопкой", Snackbar.LENGTH_LONG)
        snackbar.setAction("Увидеть Toast") {
            Toast.makeText(this, "Toast Message", Toast.LENGTH_LONG).show()
        }.show()
        snackbar.setActionTextColor(Color.RED)
    }

    fun ButtonThree(view: View) {
        temp++
        Saving()
        Load()
        val  toast = Toast.makeText(this, "Количество нажатий: $temp", Toast.LENGTH_LONG)
        toast.setGravity(Gravity.END, 200, 700)
        toast.show()
    }

    fun Saving(){
        shPref = getPreferences(MODE_PRIVATE)
        val ed = shPref.edit()
        ed.putString("count", temp.toString())
        ed.apply()
    }

    fun Load()
    {
        shPref = getPreferences(MODE_PRIVATE)
        count = shPref.getString("count", "").toString()
    }

    override fun onStop() {
        super.onStop()
        Saving()
    }
}