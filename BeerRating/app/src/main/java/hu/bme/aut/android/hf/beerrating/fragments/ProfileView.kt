package hu.bme.aut.android.hf.beerrating.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.hf.beerrating.MainActivity
import hu.bme.aut.android.hf.beerrating.R
import hu.bme.aut.android.hf.beerrating.databinding.FragmentProfileViewBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileView.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileView : Fragment() {
    private lateinit var binding : FragmentProfileViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileViewBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity: MainActivity = activity as MainActivity
        var dataFromDB = mainActivity.dataFromDB
        if (dataFromDB != null) {
            binding.tvEmail.text = dataFromDB.user.email
            binding.tvDOB.text = dataFromDB.user.dob.toString()
            if (dataFromDB.user.sexId == 1)
                binding.tvSex.text = getString(R.string.female)
            else if (dataFromDB.user.sexId == 2)
                binding.tvSex.text = getString(R.string.male)
            else
                binding.tvSex.text = getString(R.string.other)
            binding.tvForeName.text = dataFromDB.user.forename
            binding.tvSureName.text = dataFromDB.user.surename
        }

        (activity as MainActivity).binding.ibProfile.setOnClickListener (null)

        binding.btnUpdateProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileView_to_updateProfile)
        }

        binding.btnChangePass.setOnClickListener {
            findNavController().navigate(R.id.action_profileView_to_changePass)
        }

        binding.btnLogOut.setOnClickListener {
            var count = findNavController().backQueue.count()
            var i = 0;
            while (i < count - 2){
                findNavController().popBackStack()
                i++
            }
            if (dataFromDB != null) {
                dataFromDB.user = null
                dataFromDB.reviews.clear()
            }
        }
    }
}