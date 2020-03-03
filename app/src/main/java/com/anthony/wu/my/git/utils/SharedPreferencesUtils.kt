package com.anthony.wu.my.git.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesUtils(private val context: Context) {

    private var sharedPreferencesUtils:SharedPreferences? = null

    companion object{

        private const val  AUTHORIZATION = "Authorization"

    }

    init {

        sharedPreferencesUtils =  context.getSharedPreferences(AUTHORIZATION,0)

    }


    fun setAuthorization(authorization:String){



        sharedPreferencesUtils?.edit()
            ?.putString(AUTHORIZATION, authorization)
            ?.apply()

    }


    fun getAuthorization(): String? {

        return sharedPreferencesUtils?.getString(AUTHORIZATION,"")
    }

    fun clearAll(){

        sharedPreferencesUtils?.edit()?.clear()?.apply()

    }


}