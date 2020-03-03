package com.anthony.wu.my.git.koin



import com.anthony.wu.my.git.service.GitService
import org.koin.dsl.module

val serviceModule = module {

    factory<GitService> { createService(get()) }

}