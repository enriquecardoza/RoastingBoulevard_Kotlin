package com.careradish.roastingboulevard.tools

import android.app.Application
import android.content.Context
import android.view.View
import androidx.annotation.StringRes
import com.app.myapplication.CustomSnackbar
import com.google.android.material.tabs.TabLayout

class App : Application() {
    companion object {
        lateinit var context: Context
        lateinit var contentView: View
        var foodSnackBar: CustomSnackbar?=null
        lateinit var tabLayout: TabLayout
        var logged=false
        fun hideFoodSnackbar() {
            if(foodSnackBar!=null) {
                foodSnackBar!!.dismiss()
                foodSnackBar=null
            }
            UnlockTabs()
        }

        fun LockTabs(){
            tabLayout.isClickable=false
        }
        fun UnlockTabs(){
            tabLayout.isClickable=true
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }


}

object Strings {
    fun get(@StringRes stringRes: Int, vararg formatArgs: Any = emptyArray()): String {
        return App.context.getString(stringRes, *formatArgs)
    }
}