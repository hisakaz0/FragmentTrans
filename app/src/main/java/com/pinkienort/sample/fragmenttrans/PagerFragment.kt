package com.pinkienort.sample.fragmenttrans

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlinx.android.synthetic.main.fragment_pager.*

class PagerFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedViewModel = SharedViewModel.get(activity!!)
        sharedViewModel.pagerAdapterPosition.observe(this, Observer {
            view_pager.currentItem = it
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PagerAdapter(this)
        view_pager.isUserInputEnabled = false
        view_pager.adapter = adapter
    }

    private class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        private val creators = listOf(
            { MasterFragment.newInstance(android.R.color.holo_red_light, "Red") },
            { MasterFragment.newInstance(android.R.color.holo_blue_light, "Blue") },
            { MasterFragment.newInstance(android.R.color.holo_green_light, "Green") }
        )

        override fun createFragment(position: Int): Fragment {
            return creators[position]()
        }

        override fun getItemCount(): Int {
            return creators.size
        }
    }

    companion object {
        fun newInstance() = PagerFragment()
    }
}
