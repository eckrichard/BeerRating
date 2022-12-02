package hu.bme.aut.android.hf.beerrating.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.hf.beerrating.MainActivity
import hu.bme.aut.android.hf.beerrating.R
import hu.bme.aut.android.hf.beerrating.data.DataFromDB
import hu.bme.aut.android.hf.beerrating.data.database.query.DBInsert
import hu.bme.aut.android.hf.beerrating.data.database.query.DBSelect
import hu.bme.aut.android.hf.beerrating.databinding.FragmentNewReviewBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NewReview : Fragment() {
    private lateinit var binding : FragmentNewReviewBinding
    private lateinit var database: DataFromDB
    private lateinit var dbInsert: DBInsert
    private lateinit var dbSelect: DBSelect
    private lateinit var spinnerDialog: Dialog

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

        val beers = ArrayList<String>()
        for (beer in database.beers){
            beers.add(beer.name)
        }

        val adapter = ArrayAdapter(mainActivity, R.layout.spinner_item, R.id.tvSinnerItemName, beers)

        var beerId: Int? = null

        binding.spBeer.setOnClickListener {
            spinnerDialog = Dialog(mainActivity)
            spinnerDialog.setContentView(R.layout.dialog_searchable_spinner)
            spinnerDialog.window?.setLayout(800, 1000)
            spinnerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            spinnerDialog.show()

            val editText = spinnerDialog.findViewById<EditText>(R.id.etSelectBeer)
            val listview = spinnerDialog.findViewById<ListView>(R.id.beerList)

            listview.adapter = adapter

            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    adapter.filter.filter(s)
                }
                override fun afterTextChanged(s: Editable) {

                }
            })

            listview.onItemClickListener =
                OnItemClickListener { parent, view, position, id ->
                    binding.spBeer.setText(adapter.getItem(position))
                    beerId = database.beers.get(position).id
                    spinnerDialog.dismiss()
                }
        }


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

            if (beerId != null){
                dbInsert.insertReview(binding.ratingBar.rating, binding.etOpinion.text.toString(), date,
                    beerId!!, database)
                dbSelect.refreshReviews(database)
                findNavController().popBackStack()
                Snackbar.make(it, R.string.newreview, Snackbar.LENGTH_LONG).show()
            }
            else {
                Snackbar.make(it, R.string.selectbeerError, Snackbar.LENGTH_LONG).show()
            }
        }
    }
}