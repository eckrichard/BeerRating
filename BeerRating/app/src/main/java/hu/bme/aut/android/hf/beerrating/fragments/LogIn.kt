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
import hu.bme.aut.android.hf.beerrating.data.SaveSharedPreference
import hu.bme.aut.android.hf.beerrating.data.database.query.DBSelect
import hu.bme.aut.android.hf.beerrating.databinding.FragmentLogInBinding

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

        val mainActivity: MainActivity? = activity as MainActivity?
        mainActivity!!.binding.ibProfile.setOnClickListener (null)

        binding.btnLoginLogin.setOnClickListener {
            val database = mainActivity.dataFromDB
            val dbSelect = DBSelect(mainActivity.dbHelper)

            dbSelect.checkLogin(binding.etUsername.text.toString(), binding.etPassword.text.toString(), database)

            if (database != null) {
                if (database.user != null && database.user.password.equals(binding.etPassword.text.toString())){

                    binding.etUsername.text.clear()
                    binding.etPassword.text.clear()
                    SaveSharedPreference.setUserName(mainActivity, database.user.username)
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