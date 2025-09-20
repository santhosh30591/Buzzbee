package dev.miyatech.buzzbee.ui_components

import android.app.Activity
import android.content.Context
import com.google.gson.Gson
import dev.miyatech.buzzbee.model.LoginResponseModel
import dev.miyatech.buzzbee.model.LoginResult


class DBHelper(act: Activity) {


    var preference_file_key = "Buzzbee"
    var login = "loginApi"
    val sharedPref = act.getSharedPreferences(
        preference_file_key, Context.MODE_PRIVATE
    )


    fun saveLogin(logins: String) {


        val editor = sharedPref.edit()
        editor.putString(login, logins)
        editor.apply()

    }

    fun isLogin(): Boolean {
        var login = sharedPref.getString(login, "").toString()
        if (login.length == 0) {

            return false
        } else {

            return true;
        }

    }

    fun getdetails(): String {
        return sharedPref.getString(login, "").toString()


    }

    fun loginGetDetails(): LoginResult {
        var login = sharedPref.getString(login, "").toString()
        var result = LoginResult()
        if (login.equals("")) {

            return result;

        } else {
            try {
                var gson = Gson()
                var result1 = gson.fromJson(login, LoginResponseModel::class.java)

                result = result1.results
            } catch (e: Exception) {
            }
        }
        return result;
    }
}

