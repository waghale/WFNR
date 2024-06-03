package com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_DAY_ROOM.Get_Day_Room
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.LOGIN.LOGIN_LOCAL
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.Topic_Question


@Dao
interface LOGIN_LOCAL_DAO {

    @Query("SELECT * FROM Login_Local")
    fun getAll_Login_Local(): LiveData<List<LOGIN_LOCAL>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert_Login_Local_list(Login_Local_List: List<LOGIN_LOCAL>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert_Login_Local(Login_Local: LOGIN_LOCAL)


    @Query("DELETE FROM Login_Local")
    suspend fun delete_All_Login_Local()

    @Query("SELECT * FROM Login_Local WHERE wcnr_reg_no = :wcnrRegNo AND user_email_id = :userEmailId")
    fun getLoginLocalByCredentials(wcnrRegNo: String, userEmailId: String): LiveData<LOGIN_LOCAL?>


    @Query("SELECT COUNT(*) FROM Login_Local WHERE wcnr_reg_no = :wcnrRegNo AND user_email_id = :userEmailId")
    fun checkCredentialsExist(wcnrRegNo: String, userEmailId: String): LiveData<Boolean>

    @Query("SELECT * FROM Login_Local WHERE user_email_id = :emailId")
    fun getLoginLocalByEmail(emailId: String): LOGIN_LOCAL?






}