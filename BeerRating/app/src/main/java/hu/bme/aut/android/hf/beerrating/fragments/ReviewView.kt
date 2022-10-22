package hu.bme.aut.android.hf.beerrating.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.hf.beerrating.R
import hu.bme.aut.android.hf.beerrating.databinding.FragmentReviewViewBinding
import hu.bme.aut.android.hf.beerrating.databinding.FragmentUpdateProfileBinding

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

        binding.ibModify.setOnClickListener {
            findNavController().navigate(R.id.action_reviewView_to_updateReview)
        }

        binding.ibRemove.setOnClickListener {
            //TODO profil frissítése és snckbarba írás
            findNavController().popBackStack()
        }
    }
}