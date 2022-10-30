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
import hu.bme.aut.android.hf.beerrating.data.database.query.DBInsert
import hu.bme.aut.android.hf.beerrating.data.database.query.DBSelect
import hu.bme.aut.android.hf.beerrating.databinding.FragmentChangePassBinding
import hu.bme.aut.android.hf.beerrating.databinding.FragmentRegisterBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Register.newInstance] factory method to
 * create an instance of this fragment.
 */
class Register : Fragment() {
    private lateinit var binding : FragmentRegisterBinding
    private lateinit var database: DataFromDB
    private lateinit var dbInsert: DBInsert
    private lateinit var dbSelect: DBSelect

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity: MainActivity = activity as MainActivity
        database = mainActivity.dataFromDB!!
        dbInsert = DBInsert(mainActivity.dbHelper)
        dbSelect = DBSelect(mainActivity.dbHelper)

        binding.Female.isChecked = true

        (activity as MainActivity).binding.ibProfile.setOnClickListener (null)

        binding.btnRegister.setOnClickListener {
            var sexId: Int

            if (binding.Female.isChecked)
                sexId = 1
            else if (binding.Male.isChecked)
                sexId = 2
            else
                sexId = 3

            dbInsert.insertUser(binding.etEmail.text.toString(),
                binding.etUsername.text.toString(),
                binding.etPassword.text.toString(),
                binding.etDOB.text.toString(),
                binding.etForeName.text.toString(),
                binding.etSureName.text.toString(),
                sexId)

            dbSelect.checkLogin(binding.etUsername.text.toString(),
                binding.etPassword.text.toString(),
                database)

            findNavController().navigate(R.id.action_register_to_loggedIn)
        }
    }
}