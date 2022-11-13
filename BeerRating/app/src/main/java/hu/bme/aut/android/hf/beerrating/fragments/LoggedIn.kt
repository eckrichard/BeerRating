package hu.bme.aut.android.hf.beerrating.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.hf.beerrating.MainActivity
import hu.bme.aut.android.hf.beerrating.R
import hu.bme.aut.android.hf.beerrating.adapter.ReviewAdapter
import hu.bme.aut.android.hf.beerrating.data.DataFromDB
import hu.bme.aut.android.hf.beerrating.data.SaveSharedPreference
import hu.bme.aut.android.hf.beerrating.data.database.query.DBDelete
import hu.bme.aut.android.hf.beerrating.data.database.query.DBSelect
import hu.bme.aut.android.hf.beerrating.data.model.Reviews
import hu.bme.aut.android.hf.beerrating.databinding.FragmentLoggedInBinding

class LoggedIn : Fragment(), ReviewAdapter.ReviewClickListener {
    private lateinit var binding : FragmentLoggedInBinding

    private lateinit var database: DataFromDB
    private lateinit var dbDelete: DBDelete
    private lateinit var dbSelect: DBSelect
    private lateinit var adapter: ReviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoggedInBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity: MainActivity = activity as MainActivity
        database = mainActivity.dataFromDB!!
        dbDelete = DBDelete(mainActivity.dbHelper)
        dbSelect = DBSelect(mainActivity.dbHelper)

        if (database.sexs.size == 0)
            dbSelect.LoadData(database)
        else
            dbSelect.refreshReviews(database)

        (activity as MainActivity).binding.ibProfile.setOnClickListener {
            findNavController().navigate(R.id.action_loggedIn_to_profileView)
        }

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    database.user = null
                    database.reviews.clear()
                    SaveSharedPreference.setUserName(mainActivity, "")
                    findNavController().popBackStack()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_loggedIn_to_newReview)
        }

        binding.swipeToRefresh.setOnRefreshListener {
            dbSelect.refreshReviews(database)
            loadReviews()
            binding.swipeToRefresh.isRefreshing = false
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = ReviewAdapter(this)
        binding.rvReviews.layoutManager = LinearLayoutManager(activity)
        binding.rvReviews.adapter = adapter
        loadReviews()
    }

    private fun loadReviews() {
        val reviews = mutableListOf<Reviews>()
        for (review: Reviews in database.reviews){
            if (review.userId == database.user.id){
                reviews.add(review)
            }
        }
        adapter.update(reviews)
    }

    override fun onItemDeleted(item: Reviews, view: View) {
        item.beer.removeRating(item.rating)
        dbDelete.deleteReview(item.id)
        Snackbar.make(view, R.string.reviewdeleted, Snackbar.LENGTH_LONG).show()
    }

    override fun openReview(item: Reviews) {
        database.review = item
        findNavController().navigate(R.id.action_loggedIn_to_reviewView)
    }

    override fun openUpdate(item: Reviews) {
        database.review = item
        findNavController().navigate(R.id.action_loggedIn_to_updateReview)
    }
}