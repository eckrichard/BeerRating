package hu.bme.aut.android.hf.beerrating.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.hf.beerrating.MainActivity
import hu.bme.aut.android.hf.beerrating.R
import hu.bme.aut.android.hf.beerrating.data.DataFromDB
import hu.bme.aut.android.hf.beerrating.data.database.query.DBDelete
import hu.bme.aut.android.hf.beerrating.data.database.query.DBInsert
import hu.bme.aut.android.hf.beerrating.data.database.query.DBSelect
import hu.bme.aut.android.hf.beerrating.databinding.FragmentNewReviewBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewReview.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewReview : Fragment() {
    private lateinit var binding : FragmentNewReviewBinding
    private lateinit var database: DataFromDB
    private lateinit var dbInsert: DBInsert
    private lateinit var dbSelect: DBSelect

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewReviewBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity: MainActivity = activity as MainActivity
        database = mainActivity.dataFromDB!!
        dbInsert = DBInsert(mainActivity.dbHelper)
        dbSelect = DBSelect(mainActivity.dbHelper)

        var beers = ArrayList<String>()
        for (beer in database.beers){
            beers.add(beer.name)
        }

        val adapter = ArrayAdapter(mainActivity, android.R.layout.simple_spinner_item, beers)
        binding.spBeer.setAdapter(adapter)


        (activity as MainActivity).binding.ibProfile.setOnClickListener {
            findNavController().navigate(R.id.action_newReview_to_profileView)
        }

        binding.ibaddNewBeerNr.setOnClickListener {
            findNavController().navigate(R.id.action_newReview_to_addNewBeer)
        }

        binding.btnSaveNr.setOnClickListener {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val date = current.format(formatter)

            val beerId = database.beers.get(binding.spBeer.selectedItemPosition).id

            dbInsert.insertReview(binding.ratingBar.rating, binding.etOpinion.text.toString(), date, beerId, database)
            dbSelect.LoadData(database)
            findNavController().popBackStack()
            Snackbar.make(it, R.string.newreview, Snackbar.LENGTH_LONG).show()
        }
    }
}