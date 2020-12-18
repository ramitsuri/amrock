package com.ramitsuri.amrock.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import com.ramitsuri.amrock.R
import com.ramitsuri.amrock.databinding.FragmentRepositoriesBinding
import timber.log.Timber

class RepositoriesFragment : BaseFragment() {
    private var _binding: FragmentRepositoriesBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("Create")
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                exit()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.i("CreateView")
        _binding = FragmentRepositoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.i("Created")
        if (requireLogin()) {
            navigateToLogin()
            return
        }
        setupViews()
    }

    override fun onResume() {
        super.onResume()
        if (requireLogin()) {
            navigateToLogin()
        }
    }

    private fun setupViews() {
        binding.btnDetail.setOnClickListener {
            it.findNavController()
                .navigate(R.id.nav_action_repo_detail)
        }

        binding.btnLogout.setOnClickListener {
            it.findNavController()
                .navigate(R.id.nav_action_logout)
        }
    }
}