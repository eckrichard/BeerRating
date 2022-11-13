package hu.bme.aut.android.hf.beerrating.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.hf.beerrating.MainActivity
import hu.bme.aut.android.hf.beerrating.R
import hu.bme.aut.android.hf.beerrating.data.DataFromDB
import hu.bme.aut.android.hf.beerrating.data.database.query.DBInsert
import hu.bme.aut.android.hf.beerrating.data.database.query.DBSelect
import hu.bme.aut.android.hf.beerrating.databinding.FragmentAddNewBeerBinding

class NewBeer : Fragment() {
    private lateinit var binding : FragmentAddNewBeerBinding
    private lateinit var database: DataFromDB
    private lateinit var dbInsert: DBInsert
    private lateinit var dbSelect: DBSelect
    private lateinit var spinnerDialogCategory: Dialog
    private lateinit var spinnerDialogBrewery: Dialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNewBeerBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity: MainActivity = activity as MainActivity
        database = mainActivity.dataFromDB!!
        dbInsert = DBInsert(mainActivity.dbHelper)
        dbSelect = DBSelect(mainActivity.dbHelper)

        val categorys = ArrayList<String>()
        for (cat in database.categorys){
            categorys.add(cat.name)
        }
        val adapter_cat = ArrayAdapter(mainActivity, R.layout.spinner_item, R.id.tvSinnerItemName, categorys)
        var categoryId: Int? = null

        binding.spbeercategory.setOnClickListener {
            spinnerDialogCategory = Dialog(mainActivity)
            spinnerDialogCategory.setContentView(R.layout.dialog_searchable_spinner)
            spinnerDialogCategory.window?.setLayout(650, 800)
            spinnerDialogCategory.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            spinnerDialogCategory.show()

            val editText = spinnerDialogCategory.findViewById<EditText>(R.id.etSelectBeer)
            val listview = spinnerDialogCategory.findViewById<ListView>(R.id.beerList)

            listview.adapter = adapter_cat

            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    adapter_cat.filter.filter(s)
                }
                override fun afterTextChanged(s: Editable) {

                }
            })

            listview.onItemClickListener =
                AdapterView.OnItemClickListener { parent, view, position, id ->
                    binding.spbeercategory.setText(adapter_cat.getItem(position))
                    categoryId = database.categorys.get(position).id
                    spinnerDialogCategory.dismiss()
                }
        }

        val brewerys = ArrayList<String>()
        for (brew in database.brewerys){
            brewerys.add(brew.name)
        }

        val adapter_brew = ArrayAdapter(mainActivity, R.layout.spinner_item, R.id.tvSinnerItemName, brewerys)
        var breweryId: Int? = null

        binding.spBeerBrewery.setOnClickListener {
            spinnerDialogBrewery = Dialog(mainActivity)
            spinnerDialogBrewery.setContentView(R.layout.dialog_searchable_spinner)
            spinnerDialogBrewery.window?.setLayout(650, 800)
            spinnerDialogBrewery.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            spinnerDialogBrewery.show()

            val editText = spinnerDialogBrewery.findViewById<EditText>(R.id.etSelectBeer)
            val listview = spinnerDialogBrewery.findViewById<ListView>(R.id.beerList)

            listview.adapter = adapter_brew

            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    adapter_brew.filter.filter(s)
                }
                override fun afterTextChanged(s: Editable) {

                }
            })

            listview.onItemClickListener =
                AdapterView.OnItemClickListener { parent, view, position, id ->
                    binding.spBeerBrewery.setText(adapter_brew.getItem(position))
                    breweryId = database.brewerys.get(position).id
                    spinnerDialogBrewery.dismiss()
                }
        }

        (activity as MainActivity).binding.ibProfile.setOnClickListener {
            findNavController().navigate(R.id.action_addNewBeer_to_profileView)
        }

        binding.btnSaveNb.setOnClickListener {
            if(binding.etbeername.text.toString().length != 0 &&
                binding.etalc.text.toString().length != 0) {
                var exists = false
                for (b in database.beers){
                    if (b.name.toString().equals(binding.etbeername.text.toString(), ignoreCase = true)){
                        exists = true
                        break
                    }
                }
                if(!exists){
                    if (categoryId != null && breweryId != null){
                        dbInsert.insertBeer(binding.etbeername.text.toString(),
                            categoryId!!,
                            binding.etalc.text.toString().toFloat(),
                            breweryId!!,
                            binding.etBeerDetails.text.toString())
                        dbSelect.refreshBeers(database)
                        findNavController().popBackStack()
                        Snackbar.make(it, R.string.newbeer, Snackbar.LENGTH_LONG).show()
                    }
                    else {
                        Snackbar.make(it, R.string.newbeerError, Snackbar.LENGTH_LONG).show()
                    }
                }
                else {
                    Snackbar.make(it, R.string.beerExists, Snackbar.LENGTH_LONG).show()
                }
            }
            else {
                Snackbar.make(it, R.string.emptyData, Snackbar.LENGTH_LONG).show()
            }
        }

        binding.ibaddNewCategory.setOnClickListener {
            findNavController().navigate(R.id.action_addNewBeer_to_newCategory)
        }

        binding.ibaddNewBrewery.setOnClickListener {
            findNavController().navigate(R.id.action_addNewBeer_to_newBrewery)
        }
    }
}