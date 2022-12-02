package hu.bme.aut.android.hf.beerrating.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.hf.beerrating.MainActivity
import hu.bme.aut.android.hf.beerrating.R
import hu.bme.aut.android.hf.beerrating.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var binding : FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity: MainActivity? = activity as MainActivity?

        if(mainActivity?.dataFromDB?.user != null)
            findNavController().navigate(R.id.action_mainFragment_to_loggedIn)

        (activity as MainActivity).binding.ibProfile.setOnClickListener (null)

        binding.btnLoginMain.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_logIn)
        }
        binding.btnRegisterMain.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_register)
        }
    }
}