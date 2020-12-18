package com.ramitsuri.amrock.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ramitsuri.amrock.App
import com.ramitsuri.amrock.R
import com.ramitsuri.amrock.databinding.FragmentLoginBinding
import timber.log.Timber

class LoginFragment : BaseFragment() {
    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!

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
        setupViews()
    }

    private fun setupViews() {
        binding.btnLogin.setOnClickListener {
            handleLoginClicked()
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

    private fun handleLoginClicked() {
        App.instance.loginManager.setLoggedIn(true)
        findNavController()
            .navigate(R.id.nav_action_repositories)
    }
}