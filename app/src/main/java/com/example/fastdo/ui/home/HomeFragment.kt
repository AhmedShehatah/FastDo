package com.example.fastdo.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fastdo.databinding.FragmentHomeBinding
import com.example.fastdo.login.LoginActivity
import com.example.fastdo.register.RegisterActivity


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.btnPharmacyJoin.setOnClickListener {
            val intent = Intent(context, RegisterActivity::class.java)
            startActivity(intent)
            requireActivity().finish()

        }
        binding.btnPharmacyLogin.setOnClickListener {
            requireActivity().startActivity(Intent(requireContext(), LoginActivity::class.java))
        }
        binding.btnJoinAsStore.setOnClickListener {
            Toast.makeText(requireContext(), "soon", Toast.LENGTH_SHORT).show()
        }


        return binding.root
    }
}