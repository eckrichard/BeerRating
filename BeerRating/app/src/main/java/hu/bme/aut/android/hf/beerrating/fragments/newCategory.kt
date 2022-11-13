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
import hu.bme.aut.android.hf.beerrating.data.database.query.DBInsert
import hu.bme.aut.android.hf.beerrating.data.database.query.DBSelect
import hu.bme.aut.android.hf.beerrating.databinding.FragmentNewCategoryBinding

class NewCategory : Fragment() {
    private lateinit var binding : FragmentNewCategoryBinding
    private lateinit var database: DataFromDB
    private lateinit var dbInsert: DBInsert
    private lateinit var dbSelect: DBSelect

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewCategoryBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity: MainActivity = activity as MainActivity
        database = mainActivity.dataFromDB!!
        dbInsert = DBInsert(mainActivity.dbHelper)
        dbSelect = DBSelect(mainActivity.dbHelper)

        (activity as MainActivity).binding.ibProfile.setOnClickListener {
            findNavController().navigate(R.id.action_newCategory_to_profileView)
        }

        binding.btnSaveNc.setOnClickListener {
            if(binding.etcategoryname.text.toString().isNotEmpty()){
                var exists = false
                for (c in database.categorys){
                    if (c.name.toString().equals(binding.etcategoryname.text.toString(), ignoreCase = true)){
                        exists = true
                        break
                    }
                }
                if(!exists){
                    dbInsert.insertCategory(binding.etcategoryname.text.toString())
                    dbSelect.refreshCategories(database)
                    findNavController().popBackStack()
                    Snackbar.make(it, R.string.newcategory, Snackbar.LENGTH_LONG).show()
                }
                else {
                    Snackbar.make(it, R.string.categoryExists, Snackbar.LENGTH_LONG).show()
                }
            }
            else {
                Snackbar.make(it, R.string.emptyData, Snackbar.LENGTH_LONG).show()
            }
        }
    }
}