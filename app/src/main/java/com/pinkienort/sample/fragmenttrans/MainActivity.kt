package com.pinkienort.sample.fragmenttrans

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import java.lang.ClassCastException

interface FragmentSwitcher {
    fun switchTo(fragment: Fragment)
    fun switchTo(fragment: Fragment, sharedElement: View)
    fun switchTo(fragment: Fragment, sharedElements: Collection<View>?)
    fun goBack()
}

@Throws(ClassCastException::class)
fun castFragmentSwitcher(context: Context) : FragmentSwitcher {
    return (context as? FragmentSwitcher)
        ?: throw ClassCastException("${context::class} must impl ${FragmentSwitcher::class}")
}

class MainActivity : AppCompatActivity(), FragmentSwitcher {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, PagerFragment.newInstance())
                .commit()
        }
    }

    override fun switchTo(fragment: Fragment) {
        switchTo(fragment, null)
    }

    override fun switchTo(fragment: Fragment, sharedElement: View) {
        switchTo(fragment, listOf(sharedElement))
    }

    override fun switchTo(fragment: Fragment, sharedElements: Collection<View>?) {
        supportFragmentManager
            .beginTransaction()
            .apply {
                sharedElements?.forEach {
                    addSharedElement(it, it.transitionName)
                }
            }
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun goBack() {
        supportFragmentManager.popBackStack()
    }
}
