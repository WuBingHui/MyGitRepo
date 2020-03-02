package com.anthony.wu.my.git.viewmodel

import androidx.lifecycle.MutableLiveData
import com.anthony.wu.my.git.base.BaseViewModel
import com.anthony.wu.my.git.dto.Resource
import org.koin.core.KoinComponent
import org.koin.core.inject

class GitViewModel : BaseViewModel(), KoinComponent {

//    val onAccountCheck: MutableLiveData<Resource<AccountCheckResponse>> by lazy { MutableLiveData<Resource<AccountCheckResponse>>() }
//
//    fun accountCheck() {
//
//        val gameModel: GameModel by inject()
//
//        gameModel.accountCheck(Properties.getZbToken()).ioToUi().subscribe(
//                { dto ->
//                    if (dto.responseError == null ) {
//                        onAccountCheck.value = Resource.success(dto)
//                    } else {
//                        onAccountCheck.value = Resource.error(dto.responseError.message, dto)
//                    }
//                }, { t: Throwable? -> onAccountCheck.value = Resource.error(t?.message, null) }
//        ).addTo(compositeDisposable)
//
//    }

}
