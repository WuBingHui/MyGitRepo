package com.anthony.wu.my.git.base

import android.view.View
import androidx.fragment.app.DialogFragment

import io.reactivex.disposables.CompositeDisposable

open class BaseDialogFragment : DialogFragment() {

    protected var compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }


}