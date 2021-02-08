package com.example.fastdo.stagnantactivities

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fastdo.R
import com.example.fastdo.databinding.FragmentSearchStagnantBinding
import com.example.fastdo.stagnantapdaters.SearchStagnantAdapter
import com.example.fastdo.stagnantapdaters.SearchStagnantModel
import com.example.fastdo.utils.Constants
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SearchStagnantFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mProgressDialog: Dialog
    private lateinit var binding: FragmentSearchStagnantBinding
    private val list = ArrayList<SearchStagnantModel>()
    private val myListSearch = ArrayList<SearchStagnantModel>()
    private val ref =
        FirebaseDatabase.getInstance().getReference(Constants.MARKET).child(Constants.DETAILS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onPause() {
        super.onPause()
        myListSearch.clear()
        list.clear()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchStagnantBinding.inflate(inflater, container, false)

        showProgressDialog()
        ref.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                hideProgressDialog()
            } else {
                hideProgressDialog()
                Toast.makeText(requireContext(), "failed to load data", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }


    override fun onStart() {
        super.onStart()
        list.clear()
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (data in snapshot.children) {
                        val model = data.getValue(SearchStagnantModel::class.java)!!
                        list.add(model)
                    }
                    try {
                        binding.searchStagnantRecyclerView.layoutManager =
                            LinearLayoutManager(requireContext())
                        binding.searchStagnantRecyclerView.adapter =
                            SearchStagnantAdapter(requireContext(), list)
                    } catch (ex: Exception) {
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                search(newText!!)
                return true
            }


        })

    }


    private fun search(word: String) {
        myListSearch.clear()
        for (data in list) {
            if (data.stagnantName.toLowerCase(Locale.ROOT)
                    .contains(word.toLowerCase(Locale.ROOT))
            ) {
                myListSearch.add(data)
            }
        }
        binding.searchStagnantRecyclerView.adapter =
            SearchStagnantAdapter(requireContext(), myListSearch)
    }


    private fun showProgressDialog() {
        mProgressDialog = Dialog(requireContext())
        mProgressDialog.setContentView(R.layout.dialog_progress)
        mProgressDialog.show()
    }

    private fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }


}
