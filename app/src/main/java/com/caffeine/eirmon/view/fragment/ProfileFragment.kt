package com.caffeine.eirmon.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.caffeine.eirmon.R
import com.caffeine.eirmon.databinding.FragmentProfileBinding
import com.caffeine.eirmon.service.model.UserModel
import com.caffeine.eirmon.util.AlertDialog
import com.caffeine.eirmon.util.Constants
import com.caffeine.eirmon.util.DataState
import com.caffeine.eirmon.view.activity.AuthenticationActivity
import com.caffeine.eirmon.viewmodel.UserViewModel
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding
    private val viewModel : UserViewModel by viewModels()
    var user = UserModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater)

        viewModel.getMyDetails()

        viewModel.myLiveData.observe(viewLifecycleOwner) {
            when (it){
                is DataState.Loading -> {}

                is DataState.Success -> {
                    user = it.data!!
                    if (user.picture != "null"){
                        Picasso.get().load(user.picture).into(binding.profilePicture)
                    }

                    binding.name.text = user.name
                    binding.email.text = user.email
                }

                is DataState.Failed -> {
                    Constants.showSnackBar(requireContext(), binding.root, it.message!!, Constants.SNACK_LONG)
                }

            }
        }

        binding.logout.setOnClickListener{
            Constants.auth.signOut()
            Constants.intentToActivity(requireActivity(), AuthenticationActivity::class.java)
        }

        binding.editProfile.setOnClickListener{
            val action = ProfileFragmentDirections.profileToEditProfile(user)
            Navigation.findNavController(it).navigate(action)
        }

        return binding.root
    }
}