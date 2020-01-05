package com.pinkienort.sample.fragmenttrans

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        root.setBackgroundResource(
            arguments!!.getInt("color")
        )
        title.text = arguments!!.getString("title")!!
    }

    companion object {
        fun newInstance(
            @ColorRes color: Int,
            title: String
        ) = ListFragment().apply {
            arguments = Bundle(2).apply {
                putInt("color", color)
                putString("title", title)
            }
        }
    }

}
