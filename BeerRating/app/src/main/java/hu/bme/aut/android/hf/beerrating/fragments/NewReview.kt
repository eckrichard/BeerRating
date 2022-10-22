package hu.bme.aut.android.hf.beerrating.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.hf.beerrating.databinding.FragmentNewReviewBinding
import hu.bme.aut.android.hf.beerrating.databinding.FragmentUpdateProfileBinding

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

        binding.btnSave.setOnClickListener {
            //TODO profil frissítése és snckbarba írás
            findNavController().popBackStack()
        }
    }
}