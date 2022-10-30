package hu.bme.aut.android.hf.beerrating.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.hf.beerrating.MainActivity
import hu.bme.aut.android.hf.beerrating.R
import hu.bme.aut.android.hf.beerrating.data.DataFromDB
import hu.bme.aut.android.hf.beerrating.data.database.query.DBUpdate
import hu.bme.aut.android.hf.beerrating.databinding.FragmentUpdateProfileBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UpdateProfile.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateProfile : Fragment() {
    private lateinit var binding : FragmentUpdateProfileBinding
    private lateinit var database: DataFromDB
    private lateinit var dbUpdate: DBUpdate

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateProfileBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity: MainActivity = activity as MainActivity
        database = mainActivity.dataFromDB!!
        dbUpdate = DBUpdate(mainActivity.dbHelper)
        var sexId = database.user.sexId

        if(database != null){
            binding.etEmail.setText(database.user.email)
            binding.etDOB.setText(database.user.dob.toString())
            binding.etForeName.setText(database.user.forename)
            binding.etSureName.setText(database.user.surename)
            if (sexId == 1)
                binding.Female.isChecked = true
            else if (sexId == 2)
                binding.Male.isChecked = true
            else
                binding.Other.isChecked = true
        }

        (activity as MainActivity).binding.ibProfile.setOnClickListener {
            findNavController().navigate(R.id.action_updateProfile_to_profileView)
        }

        binding.btnUpdateUP.setOnClickListener {
            //TODO profil frissítése és snckbarba írás

            if (binding.Female.isChecked)
                sexId = 1
            else if (binding.Male.isChecked)
                sexId = 2
            else
                sexId = 3

            dbUpdate.updateUser(binding.etEmail.text.toString(),
                binding.etForeName.text.toString(),
                binding.etSureName.text.toString(),
                binding.etDOB.text.toString(),
                sexId,
                database)
            findNavController().popBackStack()
        }
    }
}