package com.caffeine.eirmon.view.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.caffeine.eirmon.R
import com.caffeine.eirmon.databinding.FragmentEditProfileBinding
import com.caffeine.eirmon.util.AlertDialog
import com.caffeine.eirmon.util.Constants
import com.caffeine.eirmon.util.DataState
import com.caffeine.eirmon.viewmodel.UserViewModel

class EditProfileFragment : Fragment() {

    private lateinit var binding : FragmentEditProfileBinding
    private val viewModel : UserViewModel by viewModels()
    private val user by navArgs<EditProfileFragmentArgs>()

    private lateinit var name : String
    private var uri : Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileBinding.inflate(inflater)

        binding.name.setText(user.user.name)

        binding.update.setOnClickListener{
            initialize()
            if (Constants.internetAvailable(requireContext())){
                if (validate()){
                    val v = if (uri!=null) uri else Uri.parse("android.resource://com.caffeine.eirmon/" + R.drawable.profile)
                    viewModel.updateProfile(user.user, name, v!!)
                }
            }
            else{
                Constants.showSnackBar(requireContext(), it, "Check your internet connection", Constants.SNACK_LONG)
            }
        }

        viewModel.updateLiveData.observe(viewLifecycleOwner){
            when (it){
                is DataState.Loading -> {
                    binding.btnTxt.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }

                is DataState.Success -> {
                    Constants.showSnackBar(requireContext(), binding.root, it.data!!, Constants.SNACK_LONG)
                    binding.progressBar.visibility = View.GONE
                    binding.btnTxt.visibility = View.VISIBLE
                }

                is DataState.Failed -> {
                    AlertDialog.getInstance(requireContext()).showAlertDialog(it.message!!, "Close")
                    binding.progressBar.visibility = View.GONE
                    binding.btnTxt.visibility = View.VISIBLE
                }
            }
        }

        binding.profilePicture.setOnClickListener{
            importPicture()
        }

        return binding.root
    }

    private fun initialize(){
        name = binding.name.text.toString()
    }

    private fun validate() : Boolean{
        var v = true

        if (name.isEmpty()){
            AlertDialog.getInstance(requireContext()).showAlertDialog("Name filed cannot be empty", "Close")
            v = false
        }

        return v
    }

    private fun importPicture(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.data != null){
            uri = data.data!!
            binding.profilePicture.setImageURI(uri)
            binding.placeholder.visibility = View.GONE
        }
    }
}