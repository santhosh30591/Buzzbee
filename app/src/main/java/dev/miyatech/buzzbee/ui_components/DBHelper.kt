package dev.miyatech.buzzbee.ui_components

import android.app.Activity
import android.content.Context
import com.google.gson.Gson
import dev.miyatech.buzzbee.model.LoginResponseModel
import dev.miyatech.buzzbee.model.LoginResult
import java.text.SimpleDateFormat
import java.util.Locale


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

fun dateFormateYMD_HMA(s: String): String {
    try {


        val inputFormat = SimpleDateFormat("yyyy-mm-dd HH:mm:ss", Locale.ENGLISH)

        // Define the output format (12-hour with AM/PM)
        val outputFormat = SimpleDateFormat("dd-MM-yy hh:mm a", Locale.ENGLISH)

        // Parse the input string into a Date object
        val date = inputFormat.parse(s)

        // Format the Date object into the desired 12-hour string
        return outputFormat.format(date)

    } catch (e: Exception) {
        println("date err $s")
        return s
    }
}

val downloadAppLink="https://miyahosting.co.in/buzzbee/portal/api/notificationview?id=267"

