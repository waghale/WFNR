package com.org.wfnr_2024.WFNR_ROOM_DATABASE.CMPL_Local_Model

data class DisplayItem(val name: String, val type: ItemType)

enum class ItemType {
    CHAIR, SPEAKER
}
