package com.pinkienort.sample.fragmenttrans

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import java.lang.ClassCastException

interface FragmentSwitcher {
    fun switchTo(fragment: Fragment)
    fun switchTo(fragment: Fragment, sharedElement: View)
    fun switchTo(fragment: Fragment, sharedElements: Collection<View>?)
    fun goBack()
}

@Throws(ClassCastException::class)
fun castFragmentSwitcher(context: Context?): FragmentSwitcher {
    return (context as? FragmentSwitcher)
        ?: throw ClassCastException("$context must impl ${FragmentSwitcher::class}")
}

interface OnBackPressedListener {
    fun onBackPressed(): Boolean
}

class SharedViewModel : ViewModel() {
    val pagerAdapterPosition: MutableLiveData<Int> = MutableLiveData()

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SharedViewModel() as T
        }
    }

    companion object {
        fun get(owner: ViewModelStoreOwner): SharedViewModel {
            return ViewModelProvider(owner, Factory())[SharedViewModel::class.java]
        }
    }
}

class MainActivity : AppCompatActivity(), FragmentSwitcher {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SharedViewModel.get(this)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
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

    override fun onBackPressed() {
        val current = supportFragmentManager.fragments.lastOrNull() ?: return super.onBackPressed()

        var handled = false
        if (current is OnBackPressedListener) {
            handled = current.onBackPressed()
        }
        if (!handled) {
            super.onBackPressed()
        }
    }
}
