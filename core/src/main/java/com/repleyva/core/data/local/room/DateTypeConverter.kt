package com.repleyva.core.data.local.room

import androidx.room.TypeConverter
import com.repleyva.core.domain.enums.ConverterDate
import com.repleyva.core.domain.extensions.toDate
import com.repleyva.core.domain.extensions.toString
import java.util.Date

class DateTypeConverter {

    @TypeConverter
    fun fromDateToString(date: Date?): String? {
        return date?.toString(ConverterDate.SQL_DATE)
    }

    @TypeConverter
    fun fromStringToDate(dateString: String?): Date? {
        return dateString?.toDate()
    }

}