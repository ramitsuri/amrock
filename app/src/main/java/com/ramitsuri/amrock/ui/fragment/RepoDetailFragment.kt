package com.ramitsuri.amrock.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import com.ramitsuri.amrock.R
import com.ramitsuri.amrock.databinding.FragmentRepoDetailBinding
import com.ramitsuri.amrock.entities.RepositoryInfo
import com.ramitsuri.amrock.webview.CustomWebViewClient
import kotlinx.android.synthetic.main.fragment_repo_detail.view.*
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
        args?.let {
            repositoryInfo = RepoDetailFragmentArgs.fromBundle(it).repositoryInfo
            if (repositoryInfo == null) {
                showError(getString(R.string.detail_error))
                return
            }
        }

        val client = CustomWebViewClient()
        binding.webView.webViewClient = client
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.setOnKeyListener(View.OnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK && binding.webView.canGoBack()) {
                binding.webView.goBack()
                return@OnKeyListener true
            }
            false
        })
        client.onPageStarted = {
            disableNav()
        }
        client.onPageFinished = {
            enableNav(binding.webView.canGoBack(), binding.webView.canGoForward())
        }

        binding.btnBack.setOnClickListener {
            it.findNavController()
                .navigateUp()
        }
        binding.btnBackward.setOnClickListener {
            if (binding.webView.canGoBack()) {
                binding.webView.goBack()
            }
        }
        binding.btnForward.setOnClickListener {
            if (binding.webView.canGoForward()) {
                binding.webView.goForward()
            }
        }
        binding.btnRefresh.setOnClickListener {
            binding.webView.reload()
        }

        repositoryInfo?.let { info ->
            loadRepo(info.url)
            binding.actionBar.title.text = info.name
        }
    }

    private fun enableNav(enableBack: Boolean, enableForward: Boolean) {
        binding.navBar.btnBackward.isEnabled = enableBack
        binding.navBar.btnRefresh.isEnabled = true
        binding.navBar.btnForward.isEnabled = enableForward
    }

    private fun disableNav() {
        binding.navBar.btnBackward.isEnabled = false
        binding.navBar.btnRefresh.isEnabled = false
        binding.navBar.btnForward.isEnabled = false
    }

    private fun loadRepo(url: String) {
        binding.webView.loadUrl(url)
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