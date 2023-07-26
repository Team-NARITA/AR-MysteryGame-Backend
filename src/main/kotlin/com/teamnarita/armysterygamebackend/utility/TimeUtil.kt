package com.teamnarita.armysterygamebackend.utility

import org.springframework.stereotype.Component
import java.sql.Timestamp

@Component
class TimeUtil {

    fun getCurrentTimeStamp(): Long {
        return Timestamp(System.currentTimeMillis()).time
    }
}