package com.example.fastdo.stagnantactivities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fastdo.R
import com.example.fastdo.databinding.FragmentRequestedStagnantBinding
import com.example.fastdo.firebase.FireStore
import com.example.fastdo.stagnantapdaters.RequestedStagnantsAdapter
import com.example.fastdo.stagnantapdaters.SearchStagnantAdapter
import com.example.fastdo.stagnantapdaters.SearchStagnantModel
import com.example.fastdo.utils.Constants
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RequestedStagnantFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RequestedStagnantFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentRequestedStagnantBinding
    var mbase // Create object of the
            : DatabaseReference? = null
    var adapter // Create Object of the Adapter class
            : RequestedStagnantsAdapter? = null

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
        // Inflate the layout for this fragment
        binding = FragmentRequestedStagnantBinding.inflate(inflater, container, false)
        mbase = FirebaseDatabase.getInstance().getReference(Constants.REQUESTEDSTAGNANT)
            .child(Constants.DETAILS).child(FireStore().getCurrentUserID())
        val options: FirebaseRecyclerOptions<SearchStagnantModel?> =
            FirebaseRecyclerOptions.Builder<SearchStagnantModel>()
                .setQuery(mbase!!, SearchStagnantModel::class.java)
                .build()
        adapter = RequestedStagnantsAdapter(options, requireContext())
        binding.searchStagnantRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchStagnantRecyclerView.adapter = adapter
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RequestedStagnantFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RequestedStagnantFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter!!.stopListening()
    }
}