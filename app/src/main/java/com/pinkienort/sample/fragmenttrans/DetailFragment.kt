package com.pinkienort.sample.fragmenttrans

import android.content.Context
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    lateinit var switcher: FragmentSwitcher
    lateinit var titleText: String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        switcher = castFragmentSwitcher(context)
        titleText = arguments!!.getString("title")!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())

        val boundsTrans = inflater.inflateTransition(R.transition.change_bounds)
        sharedElementEnterTransition = boundsTrans

        val fadeTrans = inflater.inflateTransition(R.transition.fade)
        fadeTrans.removeTarget(R.id.title)
        fadeTrans.removeTarget(R.id.photo)
        enterTransition = fadeTrans
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title.text = titleText
        back_button.setOnClickListener {
            activity?.onBackPressed()
        }
        val drawable = when (titleText) {
            "Red" -> R.drawable.omuraisu
            "Blue" -> R.drawable.sachertorte
            else -> R.drawable.darjeeling
        }
        photo.setImageResource(drawable)
        see_photos_button.setOnClickListener {
            val fragment = PhotosFragment.newInstance(drawable)
            switcher.switchTo(fragment, photo)
        }
    }

    companion object {
        fun newInstance(title: String) = DetailFragment().apply {
            arguments = Bundle(1).apply {
                putString("title", title)
            }
        }
    }

}
