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
import com.example.fastdo.databinding.FragmentShowStagnantBinding
import com.example.fastdo.firebase.FireStore
import com.example.fastdo.stagnantapdaters.StagnantShowAdapter
import com.example.fastdo.stagnantapdaters.StagnantShowModel
import com.example.fastdo.utils.Constants
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ShowStagnantFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private lateinit var mProgressDialog: Dialog
    private var param2: String? = null
    private val list = ArrayList<StagnantShowModel>()
    private val ref =
        FirebaseDatabase.getInstance().getReference(Constants.MYSTAGNANTS).child(Constants.DETAILS)
            .child(FireStore().getCurrentUserID())
    private lateinit var binding: FragmentShowStagnantBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowStagnantBinding.inflate(inflater, container, false)

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

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShowStagnantFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onStart() {
        super.onStart()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (data in snapshot.children) {
                        val model = data.getValue(StagnantShowModel::class.java)!!
                        list.add(model)
                    }
                    try {
                        binding.stagnantItemsRecyclerView.layoutManager =
                            LinearLayoutManager(requireContext())
                        binding.stagnantItemsRecyclerView.adapter =
                            StagnantShowAdapter(requireContext(), list)

                    }catch (ex:Exception){}


                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    override fun onStop() {
        super.onStop()
        list.clear()
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