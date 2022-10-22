package hu.bme.aut.android.hf.beerrating.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
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

        binding.btnSave.setOnClickListener {
            //TODO sörfőzde mentése és snckbarba írás
            findNavController().popBackStack()
        }
    }
}