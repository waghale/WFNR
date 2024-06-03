package com.org.wfnr_2024.Utils



import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_SESSION.Get_Session


class SessionManager_Login() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var mContext: Context
    private val privateMode = 0


    companion object{
        private val PREF_NAME="WFNR_Login"

        val KEEP_Login = "KEEP_Login"
        val DIRECT_Login = "DIRECT_Login"


    }

    constructor(context: Context):this()
    {
        mContext=context
        sharedPreferences=context.getSharedPreferences(PREF_NAME,privateMode)
        editor=sharedPreferences.edit()
    }

    fun setKeepLogin(token:Boolean)
    {
        editor.putBoolean(KEEP_Login,token)
        editor.apply()
    }

    fun setDirectLogin(token:Boolean)
    {
        editor.putBoolean(DIRECT_Login,token)
        editor.apply()
    }




    fun getKeepLogin():Boolean?
    {
        return sharedPreferences.getBoolean(KEEP_Login,false)
    }

    fun getDirectLogin():Boolean?
    {
        return sharedPreferences.getBoolean(DIRECT_Login,false)
    }










}