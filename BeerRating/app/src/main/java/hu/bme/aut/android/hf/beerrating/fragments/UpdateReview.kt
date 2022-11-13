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
import hu.bme.aut.android.hf.beerrating.data.database.query.DBSelect
import hu.bme.aut.android.hf.beerrating.data.database.query.DBUpdate
import hu.bme.aut.android.hf.beerrating.databinding.FragmentUpdateReviewBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class UpdateReview : Fragment() {
    private lateinit var binding : FragmentUpdateReviewBinding
    private lateinit var database: DataFromDB
    private lateinit var dbUpdate: DBUpdate
    private lateinit var dbSelect: DBSelect

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateReviewBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity: MainActivity = activity as MainActivity
        database = mainActivity.dataFromDB!!
        dbUpdate = DBUpdate(mainActivity.dbHelper)
        dbSelect = DBSelect(mainActivity.dbHelper)

        binding.tvBeerName.text = database.review.beer.name
        binding.ratingBar.rating = database.review.rating
        binding.etOpinion.setText(database.review.opinion)

        (activity as MainActivity).binding.ibProfile.setOnClickListener {
            findNavController().navigate(R.id.action_updateReview_to_profileView)
        }

        binding.btnSaveUpdateReview.setOnClickListener {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val date = current.format(formatter)

            database.review.rating = binding.ratingBar.rating
            dbUpdate.updateReview(binding.ratingBar.rating, binding.etOpinion.text.toString(), date, database)
            //dbSelect.LoadData(database)
            dbSelect.refreshReviews(database)
            findNavController().popBackStack()
            Snackbar.make(it, R.string.reviewupdate, Snackbar.LENGTH_LONG).show()
        }
    }
}