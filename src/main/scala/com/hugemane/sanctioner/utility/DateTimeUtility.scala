package com.hugemane.sanctioner.utility

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

object DateTimeUtility {
  val DEFAULT_DATE_TIME_VALUE_FORMAT = "dd.MM.yyyy HH:mm:ss"

  private def format_date_time(dateTime: DateTime, formatPattern: String) = DateTimeFormat.forPattern(formatPattern).print(dateTime)

  implicit class DateTimeFormatter(dateTime: DateTime) {
    def to_value(format: String = DEFAULT_DATE_TIME_VALUE_FORMAT): String = {
      if (dateTime == null) return null
      format_date_time(dateTime, format)
    }
  }

  implicit class StringDateTime(dateTimeValue: String) {
    def to_date(format: String = DEFAULT_DATE_TIME_VALUE_FORMAT): DateTime = {
      if (dateTimeValue == null) return null
      val formatter = DateTimeFormat.forPattern(format)
      formatter.parseDateTime(dateTimeValue)
    }
  }

}
