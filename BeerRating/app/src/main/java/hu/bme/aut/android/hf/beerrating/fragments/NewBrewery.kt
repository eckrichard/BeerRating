package hu.bme.aut.android.hf.beerrating.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.hf.beerrating.MainActivity
import hu.bme.aut.android.hf.beerrating.R
import hu.bme.aut.android.hf.beerrating.data.DataFromDB
import hu.bme.aut.android.hf.beerrating.data.database.query.DBInsert
import hu.bme.aut.android.hf.beerrating.data.database.query.DBSelect
import hu.bme.aut.android.hf.beerrating.databinding.FragmentNewBreweryBinding
import hu.bme.aut.android.hf.beerrating.databinding.FragmentNewReviewBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewBrewery.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewBrewery : Fragment() {
    private lateinit var binding : FragmentNewBreweryBinding
    private lateinit var database: DataFromDB
    private lateinit var dbInsert: DBInsert
    private lateinit var dbSelect: DBSelect

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewBreweryBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity: MainActivity = activity as MainActivity
        database = mainActivity.dataFromDB!!
        dbInsert = DBInsert(mainActivity.dbHelper)
        dbSelect = DBSelect(mainActivity.dbHelper)

        (activity as MainActivity).binding.ibProfile.setOnClickListener {
            findNavController().navigate(R.id.action_newBrewery_to_profileView)
        }

        binding.btnSaveBrew.setOnClickListener {
            dbInsert.insertBrewery(binding.etbreweryName.text.toString(),
                binding.etcountry.text.toString(),
                binding.etetpostcode.text.toString().toInt(),
                binding.etcity.text.toString(),
                binding.etaddress.text.toString())
            dbSelect.LoadData(database)
            findNavController().popBackStack()
            Snackbar.make(it, R.string.newbrewery, Snackbar.LENGTH_LONG).show()
        }
    }
}