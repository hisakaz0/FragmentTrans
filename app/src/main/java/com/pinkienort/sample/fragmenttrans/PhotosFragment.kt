package com.pinkienort.sample.fragmenttrans

import android.content.Context
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_photos.*

class PhotosFragment : Fragment() {

    lateinit var switcher: FragmentSwitcher

    override fun onAttach(context: Context) {
        super.onAttach(context)
        switcher = castFragmentSwitcher(context)
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
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        photo.setImageResource(arguments!!.getInt("image"))
        back_button.setOnClickListener {
            switcher.goBack()
        }
    }

    companion object {
        fun newInstance(
            @DrawableRes drawable: Int
        ) = PhotosFragment().apply {
            arguments = Bundle(1).apply {
                putInt("image", drawable)
            }
        }
    }
}
