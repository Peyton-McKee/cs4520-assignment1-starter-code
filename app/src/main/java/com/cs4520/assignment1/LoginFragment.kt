package com.cs4520.assignment1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cs4520.assignment1.databinding.LoginBinding

class LoginFragment: Fragment(), View.OnClickListener {
    private lateinit var binding: LoginBinding

    companion object {
        fun newInstance(): LoginFragment {
            return LoginFragment();
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = LoginBinding.inflate(inflater, container, false)

        val signInButton = binding.root.findViewById<Button>(R.id.signInButton)
        signInButton.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.signInButton -> {
                val usernameTextField = view.rootView.findViewById<EditText>(R.id.editTextUsername);
                val passwordTextField = view.rootView.findViewById<EditText>(R.id.editTextTextPassword)
                if (usernameTextField.text.toString() == "admin" && passwordTextField.text.toString() == "admin") {
                    usernameTextField.text.clear()
                    passwordTextField.text.clear()
                    findNavController().navigate(R.id.action_loginFragment_to_productListFragment)
                } else {
                    Toast.makeText(requireContext().applicationContext, "Incorrect Username or Password", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}