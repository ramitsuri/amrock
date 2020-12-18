package com.ramitsuri.amrock.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import com.ramitsuri.amrock.databinding.FragmentRepoDetailBinding
import com.ramitsuri.amrock.entities.RepositoryInfo
import timber.log.Timber


class RepoDetailFragment : BaseFragment() {
    private var _binding: FragmentRepoDetailBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                goUp()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.i("CreateView")
        _binding = FragmentRepoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupViews() {
        val args = arguments
        var repositoryInfo: RepositoryInfo? = null
        if (args != null) {
            repositoryInfo = RepoDetailFragmentArgs.fromBundle(args).repositoryInfo
        }
        binding.webView.webViewClient = WebViewClient()
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.canGoBack()
        binding.webView.setOnKeyListener(View.OnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK && binding.webView.canGoBack()) {
                binding.webView.goBack()
                return@OnKeyListener true
            }
            false
        })

        binding.btnBack.setOnClickListener {
            it.findNavController()
                .navigateUp()
        }

        if (repositoryInfo?.url != null) {
            loadRepo(repositoryInfo.url)
        }
    }

    private fun loadRepo(url: String) {
        binding.webView.loadUrl(url)
    }
}