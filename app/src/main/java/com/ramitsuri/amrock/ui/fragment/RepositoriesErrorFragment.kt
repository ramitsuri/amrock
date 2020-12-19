package com.ramitsuri.amrock.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ramitsuri.amrock.R


class RepositoriesErrorFragment private constructor() : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_repository_error, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString(MESSAGE)?.let { text ->
            view.findViewById<TextView>(R.id.textMessage).text = text
        }
    }

    companion object {
        val TAG: String = RepositoriesErrorFragment::class.java.simpleName
        const val MESSAGE: String = "message"

        fun newInstance(): RepositoriesErrorFragment {
            return RepositoriesErrorFragment()
        }
    }
}