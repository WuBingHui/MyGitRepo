package com.anthony.wu.my.git.koin



import com.anthony.wu.my.git.model.GitModel
import com.anthony.wu.my.git.viewmodel.GitViewModel
import org.koin.dsl.module

val repositoryModule = module {

    factory { GitModel(get()) }

}