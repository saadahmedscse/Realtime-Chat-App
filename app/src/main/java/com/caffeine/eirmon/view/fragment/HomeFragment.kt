package com.caffeine.eirmon.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.caffeine.eirmon.R
import com.caffeine.eirmon.databinding.FragmentHomeBinding
import com.caffeine.eirmon.util.Constants
import com.caffeine.eirmon.util.DataState
import com.caffeine.eirmon.view.adapter.UserAdapter
import com.caffeine.eirmon.viewmodel.UserViewModel

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private val userViewModel : UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.profilePicture.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.home_to_profile)
        }

        binding.usersRecyclerView.layoutManager = Constants.getHorizontalLayoutManager(requireContext())
        binding.chatsRecyclerView.layoutManager = Constants.getVerticalLayoutManager(requireContext())

        userViewModel.getUsers()

        userViewModel.userLiveData.observe(viewLifecycleOwner){
            when(it){
                is DataState.Loading -> {
                    //Loading
                }

                is DataState.Success -> {
                    val adapter = UserAdapter(it.data!!, requireContext())
                    binding.usersRecyclerView.adapter = adapter
                }

                is DataState.Failed -> {
                    Constants.showSnackBar(requireContext(), binding.root, it.message!!, Constants.SNACK_LONG)
                }
            }
        }

        return binding.root
    }
}