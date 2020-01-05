package com.pinkienort.sample.fragmenttrans

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

interface FragmentSwitcher {
    fun swtichFragment(fragment: Fragment)
    fun switchFragment(fragment: Fragment, sharedElements: Collection<View>?)
}

class MainActivity : AppCompatActivity(), FragmentSwitcher {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, MasterFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun swtichFragment(fragment: Fragment) {
        switchFragment(fragment, null)
    }

    override fun switchFragment(fragment: Fragment, sharedElements: Collection<View>?) {
        supportFragmentManager
            .beginTransaction()
            .apply {
                sharedElements?.forEach {
                    addSharedElement(it, it.transitionName)
                }
            }
            .replace(R.id.container, fragment)
            .addToBackStack(null)
    }
}
