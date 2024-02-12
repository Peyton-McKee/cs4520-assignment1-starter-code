package com.cs4520.assignment1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class LoginFragment: Fragment(), View.OnClickListener {
    companion object {
        fun newInstance(): LoginFragment {
            return LoginFragment();
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.login, container, false)

        val signInButton = view.findViewById<Button>(R.id.signInButton)
        signInButton.setOnClickListener(this)



        return view
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
                }
            }
        }
    }
}