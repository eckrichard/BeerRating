package hu.bme.aut.android.hf.beerrating

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.android.hf.beerrating.data.DataFromDB
import hu.bme.aut.android.hf.beerrating.data.database.dbHelper
import hu.bme.aut.android.hf.beerrating.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    var dbHelper : dbHelper? = null
    var dataFromDB : DataFromDB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        dbHelper = dbHelper()
        dataFromDB = DataFromDB()
    }
}