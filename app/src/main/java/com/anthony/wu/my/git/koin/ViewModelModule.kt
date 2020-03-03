package com.anthony.wu.my.git.koin


import com.anthony.wu.my.git.viewmodel.GitViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { GitViewModel(get()) }

}