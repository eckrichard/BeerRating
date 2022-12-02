package hu.bme.aut.android.hf.beerrating.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
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
import hu.bme.aut.android.hf.beerrating.data.database.query.DBUpdate
import hu.bme.aut.android.hf.beerrating.databinding.FragmentUpdateProfileBinding

class UpdateProfile : Fragment() {
    private lateinit var binding : FragmentUpdateProfileBinding
    private lateinit var database: DataFromDB
    private lateinit var dbUpdate: DBUpdate
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var datePickerHelper: DatePickerHelper

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
        datePickerHelper = DatePickerHelper()

        val initdobList = database.user.dob.toString().split("-")
        binding.btndobUP.text = datePickerHelper
            .makeDateString(initdobList.get(0).toInt(),
                initdobList.get(1).toInt(),
                initdobList.get(2).toInt())

        var sexId = database.user.sexId

        initDatePicker(mainActivity,
            initdobList.get(0).toInt(),
            initdobList.get(1).toInt() - 1,
            initdobList.get(2).toInt())

        binding.etEmail.setText(database.user.email)
        binding.etForeName.setText(database.user.forename)
        binding.etSureName.setText(database.user.surename)

        if (sexId == 1)
            binding.Female.isChecked = true
        else if (sexId == 2)
            binding.Male.isChecked = true
        else
            binding.Other.isChecked = true

        (activity as MainActivity).binding.ibProfile.setOnClickListener {
            findNavController().navigate(R.id.action_updateProfile_to_profileView)
        }

        binding.btndobUP.setOnClickListener {
            openDatePicker()
        }

        binding.btnUpdateUP.setOnClickListener {
            if(binding.etEmail.text.toString().isNotEmpty() &&
                binding.etForeName.text.toString().isNotEmpty() &&
                binding.etSureName.text.toString().isNotEmpty()
            ){
                if (binding.Female.isChecked)
                    sexId = 1
                else if (binding.Male.isChecked)
                    sexId = 2
                else
                    sexId = 3

                val dobList = binding.btndobUP.text.toString().split(" ")
                val day: String
                if (dobList.get(0).toInt() <= 9)
                    day = "0" + dobList.get(2)
                else
                    day = dobList.get(2)

                val DoB = dobList.get(0) + "-" + datePickerHelper.getMonthFormat(dobList.get(1)) + "-" + day

                dbUpdate.updateUser(binding.etEmail.text.toString(),
                    binding.etForeName.text.toString(),
                    binding.etSureName.text.toString(),
                    DoB,
                    sexId,
                    database)
                findNavController().popBackStack()
                Snackbar.make(it, R.string.updateprofile, Snackbar.LENGTH_LONG).show()
            }
            else{
                Snackbar.make(it, R.string.emptyData, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun initDatePicker(context: Context, year: Int, month: Int, day: Int) {
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                var month = month
                month += 1
                val date: String = datePickerHelper.makeDateString(day, month, year)
                binding.btndobUP.setText(date)
            }

        val style: Int = AlertDialog.THEME_HOLO_LIGHT

        datePickerDialog = DatePickerDialog(context, style, dateSetListener, year, month, day)
    }

    fun openDatePicker() {
        datePickerDialog.show()
    }
}