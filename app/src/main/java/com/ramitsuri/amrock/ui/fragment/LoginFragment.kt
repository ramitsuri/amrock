package com.ramitsuri.amrock.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ramitsuri.amrock.App
import com.ramitsuri.amrock.R
import com.ramitsuri.amrock.auth.AuthResult
import com.ramitsuri.amrock.data.Result
import com.ramitsuri.amrock.databinding.FragmentLoginBinding
import com.ramitsuri.amrock.viewmodel.LoginViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginFragment : BaseFragment() {
    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.i("CreateView")
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = App.instance.injector.getLoginViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
        setupViews()
    }

    private fun setupViews() {
        binding.editEmail.setText(viewModel.getEmail())
        binding.editEmail.setSelection(binding.editEmail.length())

        binding.editPassword.setText(viewModel.getPassword())
        binding.editPassword.setSelection(binding.editPassword.length())

        binding.btnLogin.setOnClickListener {
            handleLoginClicked(getEmail(), getPassword())
        }

        binding.btnHelp.setOnClickListener {
            invokeLoginHelp(false)
        }
    }

    private fun invokeLoginHelp(showError: Boolean) {
        val fragment = LoginHelpFragment.newInstance()
        val bundle = Bundle()
        bundle.putBoolean(LoginHelpFragment.SHOW_ERROR, showError)
        fragment.arguments = bundle
        activity?.supportFragmentManager?.let { supportFragmentManager ->
            fragment.show(supportFragmentManager, LoginHelpFragment.TAG)
        }
    }

    private fun handleLoginClicked(email: String, password: String) {
        context?.let { context ->
            hideKeyboard(context, binding.editEmail)
            binding.editEmail.clearFocus()
            binding.editPassword.clearFocus()
        }

        lifecycleScope.launch {
            viewModel.login(email, password).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        showProgress(true)
                    }
                    is Result.Success -> {
                        showProgress(false)
                        processLogin(result.data)
                    }
                    is Result.Error -> {
                        showProgress(false)
                        invokeLoginHelp(true)
                    }

                }
            }
        }
    }

    private fun processLogin(result: AuthResult) {
        when (result) {
            AuthResult.Success -> {
                findNavController()
                    .navigate(R.id.nav_action_repositories)
            }
            else -> {
                invokeLoginHelp(true)
            }
        }
    }

    private fun showProgress(show: Boolean) {
        if (show) {
            binding.progress.visibility = View.VISIBLE
        } else {
            binding.progress.visibility = View.GONE
        }
    }

    private fun getEmail(): String {
        return binding.editEmail.text.toString().trim()
    }

    private fun getPassword(): String {
        return binding.editPassword.text.toString()
    }
}