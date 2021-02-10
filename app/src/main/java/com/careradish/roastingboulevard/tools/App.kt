package com.careradish.roastingboulevard.tools

import android.app.Activity
import android.app.Application
import android.content.Context
import android.view.View
import androidx.core.content.edit
import com.careradish.roastingboulevard.activities.MainActivity
import com.careradish.roastingboulevard.adapters.CustomSnackbar
import com.careradish.roastingboulevard.classes.Delivery
import com.careradish.roastingboulevard.classes.User
import com.google.android.material.tabs.TabLayout


class App : Application() {
    companion object {

        var user: User? = null
        lateinit var context: Context
        lateinit var contentView: View
        var foodSnackBar: CustomSnackbar? = null
        lateinit var tabLayout: TabLayout
        val logged: Boolean get() = user != null
        var actualDelivery: Delivery? = null


        fun hideFoodSnackbar() {
            if (foodSnackBar != null) {
                foodSnackBar!!.dismiss()
                foodSnackBar = null
            }
            MainActivity.UnlockTabs()
        }



        fun Init(activity: Activity) {
            context = activity.applicationContext

        }

        fun recoverPrefUser(activity: Activity) {
            val sharedPref = context?.getSharedPreferences(
                Constants.prefUser, MODE_PRIVATE
            )
            val email = sharedPref.getString(Constants.prefUserLoggedEmail, "")
            val password = sharedPref.getString(Constants.prefUserLoggedPassword, "")
            if (email != ""&&password!="") {
                FirebaseConnection.LoginUser(email!!,password!!,activity)
            }
        }

        public fun storePrefUser(email:String,password:String,) {
            val sharedPref = context?.getSharedPreferences(
                Constants.prefUser, MODE_PRIVATE
            )
            val editor = sharedPref.edit()
            editor.putString(Constants.prefUserLoggedEmail, email)
            editor.putString(Constants.prefUserLoggedPassword, password)
            editor.apply()
        }

        public fun erasePrefUser(){
            val sharedPref = context?.getSharedPreferences(
                Constants.prefUser, MODE_PRIVATE
            )
            sharedPref.edit(commit = true) {
                clear()
            }
        }


    }



}

