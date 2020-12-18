package com.ramitsuri.amrock.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ramitsuri.amrock.R
import com.ramitsuri.amrock.databinding.FragmentRepositoriesBinding
import com.ramitsuri.amrock.entities.RepositoryInfo
import com.ramitsuri.amrock.ui.adapter.RepositoryAdapter
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
        val list = listOf(
            RepositoryInfo("Repo1", "12/21/2020", "This a very nice repository"),
            RepositoryInfo("Repo2", "12/21/2020", "This a very nice repository"),
            RepositoryInfo("Repo3", "12/21/2020", "This a very nice repository"),
            RepositoryInfo("Repo4", "12/21/2020", "This a very nice repository"),
            RepositoryInfo("Repo5", "12/21/2020", "This a very nice repository"),
            RepositoryInfo("Repo6", "12/21/2020", "This a very nice repository"),
            RepositoryInfo("Repo7", "12/21/2020", "This a very nice repository"),
            RepositoryInfo("Repo8", "12/21/2020", "This a very nice repository"),
            RepositoryInfo("Repo9", "12/21/2020", "This a very nice repository"),
            RepositoryInfo("Repo10", "12/21/2020", "This a very nice repository"),
            RepositoryInfo("Repo11", "12/21/2020", "This a very nice repository"),
            RepositoryInfo("Repo12", "12/21/2020", "This a very nice repository"),
            RepositoryInfo("Repo13", "12/21/2020", "This a very nice repository"),
            RepositoryInfo("Repo14", "12/21/2020", "This a very nice repository"),
            RepositoryInfo("Repo15", "12/21/2020", "This a very nice repository"),
            RepositoryInfo("Repo16", "12/21/2020", "This a very nice repository"),
            RepositoryInfo("Repo17", "12/21/2020", "This a very nice repository"),
            RepositoryInfo("Repo18", "12/21/2020", "This a very nice repository"),
            RepositoryInfo("Repo19", "12/21/2020", "This a very nice repository"),
            RepositoryInfo("Repo20", "12/21/2020", "This a very nice repository")
        )
        val adapter = RepositoryAdapter(list)
        adapter.onItemClick = {
            onRepoSelected(it)
        }
        binding.listView.layoutManager = LinearLayoutManager(activity)
        binding.listView.adapter = adapter

        binding.btnLogout.setOnClickListener {
            it.findNavController()
                .navigate(R.id.nav_action_logout)
        }
    }

    private fun onRepoSelected(repositoryInfo: RepositoryInfo) {
        val action = RepositoriesFragmentDirections.navActionRepoDetail()
        action.repositoryInfo = repositoryInfo
        findNavController().navigate(action)
    }
}