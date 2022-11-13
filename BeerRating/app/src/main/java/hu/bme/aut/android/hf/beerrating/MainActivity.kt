package hu.bme.aut.android.hf.beerrating

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.android.hf.beerrating.data.DataFromDB
import hu.bme.aut.android.hf.beerrating.data.SaveSharedPreference
import hu.bme.aut.android.hf.beerrating.data.database.dbHelper
import hu.bme.aut.android.hf.beerrating.data.database.query.DBSelect
import hu.bme.aut.android.hf.beerrating.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    var dbHelper : dbHelper? = null
    var dataFromDB : DataFromDB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = dbHelper()
        dataFromDB = DataFromDB()

        if(SaveSharedPreference.getUserName(this)!!.isNotEmpty())
        {
            DBSelect(dbHelper).SelectUserByUsername(SaveSharedPreference.getUserName(this)!!, dataFromDB)
            DBSelect(dbHelper).LoadData(dataFromDB)
        }
    }
}