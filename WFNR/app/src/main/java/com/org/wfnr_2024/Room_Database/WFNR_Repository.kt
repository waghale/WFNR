package com.org.wfnr_2024.Room_Database


import androidx.lifecycle.LiveData
import com.org.wfnr_2024.Room_Database.ROOM_DAO.Days_DAO
import com.org.wfnr_2024.Room_Database.ROOM_DAO.SessionDataClass_DAO
import com.org.wfnr_2024.Room_Database_Model.Days_Local
import com.org.wfnr_2024.Room_Database_Model.SessionDataClass_Local

class WFNR_Repository(
    private val SessionDataClass_DAO: SessionDataClass_DAO,
 private val Days_DAO: Days_DAO,
    private val database: WFNR_Room_Database
) {


    val getAllSessionData:LiveData<List<SessionDataClass_Local>> = SessionDataClass_DAO.getAllSessionData()
    val getAllDaysData : LiveData<List<Days_Local>> = Days_DAO.getAllDaysData()

}