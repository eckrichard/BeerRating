package hu.bme.aut.android.hf.beerrating.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.hf.beerrating.MainActivity
import hu.bme.aut.android.hf.beerrating.R
import hu.bme.aut.android.hf.beerrating.data.DataFromDB
import hu.bme.aut.android.hf.beerrating.data.database.dbHelper
import hu.bme.aut.android.hf.beerrating.data.database.query.DBSelect
import hu.bme.aut.android.hf.beerrating.databinding.FragmentLogInBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LogIn.newInstance] factory method to
 * create an instance of this fragment.
 */
class LogIn : Fragment() {
    private lateinit var binding : FragmentLogInBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).binding.ibProfile.setOnClickListener (null)

        binding.btnLoginLogin.setOnClickListener {
            val mainActivity: MainActivity? = activity as MainActivity?
            if (mainActivity != null) {
                var dataFromDB = mainActivity.dataFromDB
                var dbSelect = DBSelect(mainActivity.dbHelper)
                dbSelect.checkLogin(binding.etUsername.text.toString(), binding.etPassword.text.toString(), dataFromDB)

                if (dataFromDB != null) {
                    if (dataFromDB.user != null && dataFromDB.user.password.equals(binding.etPassword.text.toString())){

                        binding.etUsername.text.clear()
                        binding.etPassword.text.clear()
                        findNavController().navigate(R.id.action_logIn_to_loggedIn)
                    }
                    else {
                        binding.etUsername.text.clear()
                        binding.etPassword.text.clear()
                        Snackbar.make(it, R.string.loginfailed, Snackbar.LENGTH_LONG).show()
                    }
                }

            }
        }
    }
}