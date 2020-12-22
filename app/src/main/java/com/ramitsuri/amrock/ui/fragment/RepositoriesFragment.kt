package com.ramitsuri.amrock.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ramitsuri.amrock.App
import com.ramitsuri.amrock.R
import com.ramitsuri.amrock.data.Result
import com.ramitsuri.amrock.databinding.FragmentRepositoriesBinding
import com.ramitsuri.amrock.entities.RepositoryInfo
import com.ramitsuri.amrock.ui.adapter.RepositoryAdapter
import com.ramitsuri.amrock.viewmodel.RepositoriesViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class RepositoriesFragment : BaseFragment() {
    private var _binding: FragmentRepositoriesBinding? = null

    private val binding get() = _binding!!
    private var rootView: View? = null
    private lateinit var viewModel: RepositoriesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("Create")
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                exit()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
        val factory = App.instance.injector.getRepositoriesViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(RepositoriesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.i("CreateView")
        val root = rootView
        _binding = if (root == null) {
            FragmentRepositoriesBinding.inflate(inflater, container, false)
        } else {
            FragmentRepositoriesBinding.bind(root)
        }
        rootView = binding.root
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    override fun onResume() {
        super.onResume()
        if (requireLogin()) {
            navigateToLogin()
        }
    }

    private fun setupViews() {
        val adapter = RepositoryAdapter(
            mutableListOf(),
            App.instance.injector.getDateTimeHelper(),
            getString(R.string.date_format)
        )
        adapter.onItemClick = {
            onRepoSelected(it)
        }
        binding.listView.layoutManager = LinearLayoutManager(activity)
        binding.listView.adapter = adapter

        binding.btnLogout.setOnClickListener {
            viewModel.onLogout()
            it.findNavController()
                .navigate(R.id.nav_action_logout)
        }
        lifecycleScope.launch {
            viewModel.getRepositories().collect { result ->
                when (result) {
                    is Result.Loading -> {
                        showProgress(true)
                    }
                    is Result.Success -> {
                        showProgress(false)
                        adapter.update(result.data)
                    }
                    is Result.Error -> {
                        showProgress(false)
                        showError(result.message)
                    }

                }
            }
        }
    }

    private fun onRepoSelected(repositoryInfo: RepositoryInfo) {
        val action = RepositoriesFragmentDirections.navActionRepoDetail()
        action.repositoryInfo = repositoryInfo
        findNavController().navigate(action)
    }

    private fun showProgress(show: Boolean) {
        if (show) {
            binding.progress.visibility = View.VISIBLE
        } else {
            binding.progress.visibility = View.GONE
        }
    }

    private fun showError(message: String) {
        val fragment = ErrorFragment.newInstance()
        val bundle = Bundle()
        bundle.putString(ErrorFragment.MESSAGE, message)
        fragment.arguments = bundle
        activity?.supportFragmentManager?.let { supportFragmentManager ->
            fragment.show(supportFragmentManager, ErrorFragment.TAG)
        }
    }
}