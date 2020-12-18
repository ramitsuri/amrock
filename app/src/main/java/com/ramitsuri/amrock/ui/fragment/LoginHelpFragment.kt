package com.ramitsuri.amrock.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ramitsuri.amrock.R


class LoginHelpFragment private constructor() : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login_help, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments?.getBoolean(SHOW_ERROR) == true) {
            view.findViewById<View>(R.id.textError).visibility = View.VISIBLE
        }
    }

    companion object {
        val TAG: String = LoginHelpFragment::class.java.simpleName
        const val SHOW_ERROR: String = "show_error"

        fun newInstance(): LoginHelpFragment {
            return LoginHelpFragment()
        }
    }
}