package hu.bme.aut.android.hf.beerrating.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
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
import hu.bme.aut.android.hf.beerrating.data.database.query.DBInsert
import hu.bme.aut.android.hf.beerrating.data.database.query.DBSelect
import hu.bme.aut.android.hf.beerrating.databinding.FragmentRegisterBinding
import java.time.LocalDateTime
import java.util.*


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
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var datePickerHelper: DatePickerHelper


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

        initDatePicker(mainActivity)
        datePickerHelper = DatePickerHelper()

        binding.Female.isChecked = true

        val current = LocalDateTime.now()
        binding.btndob.setText(datePickerHelper
            .makeDateString(current.dayOfMonth,
                current.monthValue,
                current.year))

        (activity as MainActivity).binding.ibProfile.setOnClickListener (null)

        binding.btndob.setOnClickListener {
            openDatePicker(it)
        }

        binding.btnRegister.setOnClickListener {
            var sexId: Int

            if (binding.Female.isChecked)
                sexId = 1
            else if (binding.Male.isChecked)
                sexId = 2
            else
                sexId = 3

            val exist = dbSelect.checkUserAlreadyExist(binding.etUsername.text.toString(), binding.etEmail.text.toString())
            val dobList = binding.btndob.text.toString().split(" ")
            var day: String
            if (dobList.get(2).toInt() <= 9)
                day = "0" + dobList.get(2)
            else
                day = dobList.get(2)

            val DoB = dobList.get(0) + "-" + datePickerHelper.getMonthFormat(dobList.get(1)) + "-" + day

            if (!exist) {
                dbInsert.insertUser(binding.etEmail.text.toString(),
                    binding.etUsername.text.toString(),
                    binding.etPassword.text.toString(),
                    DoB,
                    binding.etForeName.text.toString(),
                    binding.etSureName.text.toString(),
                    sexId)

                dbSelect.checkLogin(binding.etUsername.text.toString(),
                    binding.etPassword.text.toString(),
                    database)

                findNavController().navigate(R.id.action_register_to_loggedIn)
                Snackbar.make(it, R.string.regsuccess, Snackbar.LENGTH_LONG).show()
            }
            else {
                binding.etEmail.setText("")
                binding.etUsername.setText("")
                binding.etPassword.setText("")
                binding.etPasswordAgain.setText("")
                binding.btndob.setText(datePickerHelper
                    .makeDateString(current.dayOfMonth,
                        current.monthValue,
                        current.year))
                binding.etForeName.setText("")
                binding.etSureName.setText("")
                binding.Female.isChecked = true
                Snackbar.make(it, R.string.alreadyexist, Snackbar.LENGTH_LONG).show()
            }

        }
    }

    private fun initDatePicker(context: Context) {
        val dateSetListener =
            OnDateSetListener { datePicker, year, month, day ->
                var month = month
                month = month + 1
                val date: String = datePickerHelper.makeDateString(day, month, year)
                binding.btndob.setText(date)
            }

        val cal: Calendar = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        val month: Int = cal.get(Calendar.MONTH)
        val day: Int = cal.get(Calendar.DAY_OF_MONTH)

        val style: Int = AlertDialog.THEME_HOLO_LIGHT

        datePickerDialog = DatePickerDialog(context, style, dateSetListener, year, month, day)
    }

    fun openDatePicker(view: View?) {
        datePickerDialog.show()
    }
}