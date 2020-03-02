package com.anthony.wu.my.git.base

import android.view.View
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

open class BaseFragment : Fragment() {

    protected var compositeDisposable = CompositeDisposable()


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

}