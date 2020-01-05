package com.pinkienort.sample.fragmenttrans

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_main.*
import java.lang.ClassCastException

interface ContainerFragmentSwitcher {
    fun switchContainerTo(fragment: Fragment)
    fun switchContainerTo(fragment: Fragment, sharedElement: View?)
}

fun castContainerFragmentSwitcher(fragment: Fragment): ContainerFragmentSwitcher {
    var target = fragment.parentFragment
    while (target != null) {
        if (target is ContainerFragmentSwitcher) return target
        target = target.parentFragment
    }
    throw ClassCastException("$fragment must impl ${ContainerFragmentSwitcher::class}")
}

class MainFragment : Fragment(),
    ContainerFragmentSwitcher,
    OnBackPressedListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = SharedViewModel.get(activity!!)

        if (savedInstanceState == null) {
            childFragmentManager
                .beginTransaction()
                .replace(R.id.container, PagerFragment.newInstance())
                .commit()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottom_navigation.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(menu: MenuItem): Boolean {
        // TODO: childFragmentも考慮してfragment入れ替える
        when (menu.itemId) {
            R.id.one -> sharedViewModel.pagerAdapterPosition.value = 0
            R.id.two -> sharedViewModel.pagerAdapterPosition.value = 1
            R.id.three -> sharedViewModel.pagerAdapterPosition.value = 2
        }
        return true
    }

    override fun onBackPressed(): Boolean {
        if (childFragmentManager.backStackEntryCount > 0) {
            childFragmentManager.popBackStack()
            return true
        }
        return false
    }

    override fun switchContainerTo(fragment: Fragment) {
        switchContainerTo(fragment, null)
    }

    override fun switchContainerTo(fragment: Fragment, sharedElement: View?) {
        childFragmentManager
            .beginTransaction()
            .apply {
                sharedElement?.let {
                    addSharedElement(it, it.transitionName)
                }
            }
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
