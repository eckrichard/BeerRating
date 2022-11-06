package hu.bme.aut.android.hf.beerrating.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.hf.beerrating.MainActivity
import hu.bme.aut.android.hf.beerrating.R
import hu.bme.aut.android.hf.beerrating.data.DataFromDB
import hu.bme.aut.android.hf.beerrating.data.database.query.DBDelete
import hu.bme.aut.android.hf.beerrating.data.database.query.DBSelect
import hu.bme.aut.android.hf.beerrating.databinding.FragmentReviewViewBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReviewView.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReviewView : Fragment() {
    private lateinit var binding : FragmentReviewViewBinding

    private lateinit var database: DataFromDB
    private lateinit var dbDelete: DBDelete

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReviewViewBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity: MainActivity = activity as MainActivity
        database = mainActivity.dataFromDB!!
        dbDelete = DBDelete(mainActivity.dbHelper)

        binding.tvBeerName.text = database.review.beer.name
        binding.tvAlc.text = "${database.review.beer.alc} %"
        binding.tvLastReviewDateView.text = database.review.lastChange.toString()
        binding.tvbeerCat.text = database.review.beer.category.name
        binding.tvRatingView.text = database.review.rating.toString()
        binding.tvbreweryName.text = database.review.beer.brewery.name
        binding.tvDetails.text = database.review.beer.details
        binding.tvOpinion.text = database.review.opinion
        binding.tvglobalRatingView.text = database.review.beer.globalRating.toString()

        (activity as MainActivity).binding.ibProfile.setOnClickListener {
            findNavController().navigate(R.id.action_reviewView_to_profileView)
        }

        binding.ibDel.setOnClickListener {
            dbDelete.deleteReview(database.review.id)
            findNavController().popBackStack()
        }

        binding.ibMod.setOnClickListener {
            findNavController().navigate(R.id.action_reviewView_to_updateReview)
        }
    }
}