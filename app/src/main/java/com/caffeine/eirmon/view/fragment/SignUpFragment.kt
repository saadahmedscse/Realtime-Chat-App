package com.caffeine.eirmon.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.caffeine.eirmon.R
import com.caffeine.eirmon.databinding.FragmentSignUpBinding
import com.caffeine.eirmon.util.AlertDialog
import com.caffeine.eirmon.util.Constants
import com.caffeine.eirmon.util.DataState
import com.caffeine.eirmon.view.activity.DashboardActivity
import com.caffeine.eirmon.viewmodel.AuthViewModel

class SignUpFragment : Fragment() {

    private lateinit var binding : FragmentSignUpBinding

    private lateinit var name : String
    private lateinit var email : String
    private lateinit var password : String
    private lateinit var confirmPassword : String

    private val authViewModel : AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater)

        binding.changePageBtn.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.sign_up_to_sign_in)
        }

        binding.authBtn.setOnClickListener{
            initialize()
            if (validate()){
                if (Constants.internetAvailable(requireContext())){
                    authViewModel.createUser(
                        name,
                        email,
                        password,
                        "null"
                    )
                }
                else{
                    Constants.showSnackBar(requireContext(), it, "Check your internet connection", Constants.SNACK_LONG)
                }
            }
        }

        authViewModel.createUserLiveData.observe(viewLifecycleOwner){
            when(it){
                is DataState.Loading -> {
                    binding.authText.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }

                is DataState.Success -> {
                    Constants.intentToActivity(requireActivity(), DashboardActivity::class.java)
                }

                is DataState.Failed -> {
                    binding.progressBar.visibility = View.GONE
                    binding.authText.visibility = View.VISIBLE
                    AlertDialog.getInstance(requireContext()).showAlertDialog(it.message!!, "Close")
                }
            }
        }

        return binding.root
    }

    private fun initialize(){
        name = binding.name.text.toString()
        email = binding.email.text.toString()
        password = binding.password.text.toString()
        confirmPassword = binding.confirmPassword.text.toString()
    }

    private fun validate() : Boolean{
        var v = true

        if (name.isEmpty()){
            AlertDialog.getInstance(requireContext()).showAlertDialog("Your must have to enter your name", "Close")
            v = false
        }

        else if (email.isEmpty()){
            AlertDialog.getInstance(requireContext()).showAlertDialog("Your must have to enter your email address", "Close")
            v = false
        }

        else if (password.isEmpty()){
            AlertDialog.getInstance(requireContext()).showAlertDialog("You must have to enter your password", "Close")
            v = false
        }

        else if (password.length < 6){
            AlertDialog.getInstance(requireContext()).showAlertDialog("Your password should be at leaset 6 characters long", "Close")
            v = false
        }

        else if (password != confirmPassword){
            AlertDialog.getInstance(requireContext()).showAlertDialog("Password did not match. Check again", "Close")
            v = false
        }

        return v
    }
}