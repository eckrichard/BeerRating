package hu.bme.aut.android.hf.beerrating.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.hf.beerrating.MainActivity
import hu.bme.aut.android.hf.beerrating.R
import hu.bme.aut.android.hf.beerrating.data.DataFromDB
import hu.bme.aut.android.hf.beerrating.data.database.query.DBInsert
import hu.bme.aut.android.hf.beerrating.data.database.query.DBSelect
import hu.bme.aut.android.hf.beerrating.databinding.FragmentAddNewBeerBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewBeer.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewBeer : Fragment() {
    private lateinit var binding : FragmentAddNewBeerBinding
    private lateinit var database: DataFromDB
    private lateinit var dbInsert: DBInsert
    private lateinit var dbSelect: DBSelect

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

        var categorys = ArrayList<String>()
        for (cat in database.categorys){
            categorys.add(cat.name)
        }

        val adapter_cat = ArrayAdapter(mainActivity, android.R.layout.simple_spinner_item, categorys)
        binding.spbeercategory.setAdapter(adapter_cat)

        var brewerys = ArrayList<String>()
        for (brew in database.brewerys){
            brewerys.add(brew.name)
        }

        val adapter_brew = ArrayAdapter(mainActivity, android.R.layout.simple_spinner_item, brewerys)
        binding.spBeerBrewery.setAdapter(adapter_brew)

        (activity as MainActivity).binding.ibProfile.setOnClickListener {
            findNavController().navigate(R.id.action_addNewBeer_to_profileView)
        }

        binding.btnSaveNb.setOnClickListener {
            dbInsert.insertBeer(binding.etbeername.text.toString(),
                database.categorys.get(binding.spbeercategory.selectedItemPosition).id,
                binding.etalc.text.toString().toFloat(),
                database.brewerys.get(binding.spBeerBrewery.selectedItemPosition).id,
                binding.etBeerDetails.text.toString())
            //dbSelect.LoadData(database)
            dbSelect.refreshBeers(database)
            findNavController().popBackStack()
            Snackbar.make(it, R.string.newbeer, Snackbar.LENGTH_LONG).show()
        }

        binding.ibaddNewCat.setOnClickListener {
            findNavController().navigate(R.id.action_addNewBeer_to_newCategory)
        }

        binding.ibaddNewBrew.setOnClickListener {
            findNavController().navigate(R.id.action_addNewBeer_to_newBrewery)
        }
    }
}