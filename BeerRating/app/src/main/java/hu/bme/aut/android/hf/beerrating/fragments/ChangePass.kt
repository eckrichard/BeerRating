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
import hu.bme.aut.android.hf.beerrating.data.database.query.DBUpdate
import hu.bme.aut.android.hf.beerrating.databinding.FragmentChangePassBinding

class ChangePass : Fragment() {
    private lateinit var binding : FragmentChangePassBinding
    private lateinit var database: DataFromDB
    private lateinit var dbUpdate: DBUpdate

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangePassBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity: MainActivity = activity as MainActivity
        database = mainActivity.dataFromDB!!
        dbUpdate = DBUpdate(mainActivity.dbHelper)

        (activity as MainActivity).binding.ibProfile.setOnClickListener {
            findNavController().navigate(R.id.action_changePass_to_profileView)
        }

        binding.btnUpdate.setOnClickListener {
            if(binding.etOldPass.text.toString().isNotEmpty() &&
                binding.etNewPass.text.toString().isNotEmpty() &&
                binding.etNewPassCon.text.toString().isNotEmpty()
            ){
                if (database.user.password == binding.etOldPass.text.toString()){
                    if(binding.etNewPass.text.toString().equals(binding.etNewPassCon.text.toString())){
                        val pass: String = binding.etNewPass.text.toString()

                        dbUpdate.updatePass(pass, database)
                        findNavController().popBackStack()
                        Snackbar.make(it, R.string.passsaved, Snackbar.LENGTH_LONG).show()
                    }
                    else {
                        Snackbar.make(it, R.string.newpassincor, Snackbar.LENGTH_LONG).show()
                    }
                }
                else {
                    Snackbar.make(it, R.string.oldpassincor, Snackbar.LENGTH_LONG).show()
                }
            }
            else {
                Snackbar.make(it, R.string.emptyData, Snackbar.LENGTH_LONG).show()
            }
        }
    }
}