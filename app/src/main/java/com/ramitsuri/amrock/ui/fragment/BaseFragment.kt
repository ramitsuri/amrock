package com.ramitsuri.amrock.ui.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ramitsuri.amrock.App
import com.ramitsuri.amrock.R
import timber.log.Timber

abstract class BaseFragment : Fragment() {
    private val TAG: String = this::class.java.simpleName

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.i("%s OnAttach", TAG)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        Timber.i("%s OnCreateView", TAG)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.i("%s OnViewCreated", TAG)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Timber.i("%s OnViewStateRestored", TAG)
    }

    override fun onStart() {
        super.onStart()
        Timber.i("%s OnAttach", TAG)
    }

    override fun onResume() {
        super.onResume()
        Timber.i("%s OnResume", TAG)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.i("%s OnSaveInstanceState", TAG)
    }

    override fun onPause() {
        super.onPause()
        Timber.i("%s OnPause", TAG)
    }

    override fun onStop() {
        super.onStop()
        Timber.i("%s OnStop", TAG)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.i("%s OnDestroyView", TAG)
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("%s OnDestroy", TAG)
    }

    override fun onDetach() {
        super.onDetach()
        Timber.i("%s OnDetach", TAG)
    }

    protected fun goUp() {
        val activity: Activity? = activity
        if (activity != null) {
            (activity as AppCompatActivity).onSupportNavigateUp()
        } else {
            Timber.w("Up requested -> Activity is null")
        }
    }

    protected fun exit() {
        val activity: Activity? = activity
        if (activity != null) {
            (activity as AppCompatActivity).finish()
        } else {
            Timber.w("Exit requested -> Activity is null")
        }
    }

    protected fun hideKeyboard(context: Context, view: View) {
        val imm: InputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    protected fun requireLogin(): Boolean {
        return App.instance.injector.loginManager.requireLogin()
    }

    protected fun navigateToLogin() {
        findNavController().navigate(R.id.nav_action_logout)
    }
}