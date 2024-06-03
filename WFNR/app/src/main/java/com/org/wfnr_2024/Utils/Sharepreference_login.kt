import android.content.Context
import android.content.SharedPreferences
import com.org.wfnr_2024.Model.Login.Data

class Sharepreference_login(context: Context) {


    companion object {
        const val PREF_NAME = "UserData"
        const val KEY_USER_ID = "userId"
        const val KEY_USER_NAME = "userName"
        const val KEY_USER_EMAIL = "userEmail"
        const val KEY_USER_MOBILE = "userMobile"
        const val KEY_USER_REG_NO = "userRegNo"
        const val KEY_USER_CITY = "userCity"
        const val KEY_USER_COUNTRY = "userCountry"
        const val KEY_USER_REG_TYPE = "userRegType"
        const val KEY_LOGIN_STATUS = "loginStatus"
        const val KEY_IS_LOGGED_IN = "isLoggedIn"

    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_LOGIN_STATUS, false)
    }
    fun saveUserLoggedIn(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_IS_LOGGED_IN, isLoggedIn).apply()
    }

    fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun saveUserData(userData: Data) {
        sharedPreferences.edit().apply {
            putInt(KEY_USER_ID, userData.id)
            putString(
                KEY_USER_NAME,
                "${userData.title} ${userData.fname} ${userData.lname}"
            )
            putString(KEY_USER_EMAIL, userData.email)
            putString(KEY_USER_MOBILE, userData.mobile)
            putString(KEY_USER_REG_NO, userData.WFNR_reg_no)
            putString(KEY_USER_CITY, userData.city)
            putString(KEY_USER_COUNTRY, userData.country)
            putString(KEY_USER_REG_TYPE, userData.reg_type)
            putBoolean(KEY_LOGIN_STATUS, true)
            apply()
        }
    }

    fun clearUserData() {
        sharedPreferences.edit().clear().apply()
    }

    fun getUserData(): Data? {
        if (!isLoggedIn()) {
            return null
        }
        val userId = sharedPreferences.getInt(KEY_USER_ID, 0)
        val userName = sharedPreferences.getString(KEY_USER_NAME, "")
        val userEmail = sharedPreferences.getString(KEY_USER_EMAIL, "")
        val userMobile = sharedPreferences.getString(KEY_USER_MOBILE, "")
        val userRegNo = sharedPreferences.getString(KEY_USER_REG_NO, "")
        val userCity = sharedPreferences.getString(KEY_USER_CITY, "")
        val userCountry = sharedPreferences.getString(KEY_USER_COUNTRY, "")
        val userRegType = sharedPreferences.getString(KEY_USER_REG_TYPE, "")

        return Data(
            WFNR_reg_no = userRegNo ?: "",
            city = userCity ?: "",
            country = userCountry ?: "",
            created_at = "",
            email = userEmail ?: "",
            fname = userName?:"",
            id = userId,
            isactive = 0,
            lname = "",
            login_count = "",
            mobile = userMobile ?: "",
            reg_type = userRegType ?: "",
            title = "",
            updated_at = ""
        )
    }


}
