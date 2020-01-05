package com.pinkienort.sample.fragmenttrans

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_master.*

class MasterFragment : Fragment() {

    lateinit var switcher: FragmentSwitcher

    override fun onAttach(context: Context) {
        super.onAttach(context)
        switcher = castFragmentSwitcher(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_master, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        root.setBackgroundResource(
            arguments!!.getInt("color")
        )
        title.text = arguments!!.getString("title")!!
        bottom.setOnClickListener {
            val fragment = DetailFragment.newInstance(arguments!!.getString("title")!!)
            switcher.switchTo(fragment, title)
        }
    }

    companion object {
        fun newInstance(
            @ColorRes color: Int,
            title: String
        ) = MasterFragment().apply {
            arguments = Bundle(2).apply {
                putInt("color", color)
                putString("title", title)
            }
        }
    }

}
