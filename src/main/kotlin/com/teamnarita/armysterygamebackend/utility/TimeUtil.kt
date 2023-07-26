package com.teamnarita.armysterygamebackend.utility

import java.sql.Timestamp

object TimeUtil {

    fun getCurrentTimeStamp(): Long {
        return Timestamp(System.currentTimeMillis()).time
    }
}