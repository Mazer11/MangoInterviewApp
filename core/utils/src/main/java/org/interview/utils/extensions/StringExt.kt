package org.interview.utils.extensions

import org.interview.utils.models.Zodiac
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun String.dateToZodiac(): Zodiac? {
    val dateFormatter = DateTimeFormatter.ISO_DATE
    val date = LocalDate.parse(this, dateFormatter)
    val day = date.dayOfMonth
    val month = date.monthValue

    return when {
        (month == 1 && day >= 20) || (month == 2 && day <= 18) -> Zodiac.Aquarius
        (month == 2 && day >= 19) || (month == 3 && day <= 20) -> Zodiac.Pisces
        (month == 3 && day >= 21) || (month == 4 && day <= 19) -> Zodiac.Aries
        (month == 4 && day >= 20) || (month == 5 && day <= 20) -> Zodiac.Taurus
        (month == 5 && day >= 21) || (month == 6 && day <= 20) -> Zodiac.Gemini
        (month == 6 && day >= 21) || (month == 7 && day <= 22) -> Zodiac.Cancer
        (month == 7 && day >= 23) || (month == 8 && day <= 22) -> Zodiac.Leo
        (month == 8 && day >= 23) || (month == 9 && day <= 22) -> Zodiac.Virgo
        (month == 9 && day >= 23) || (month == 10 && day <= 22) -> Zodiac.Libra
        (month == 10 && day >= 23) || (month == 11 && day <= 21) -> Zodiac.Scorpio
        (month == 11 && day >= 22) || (month == 12 && day <= 21) -> Zodiac.Sagittarius
        (month == 12 && day >= 22) || (month == 1 && day <= 19) -> Zodiac.Capricorn
        else -> null
    }
}

fun String.toShortDate(): String? {
    val dateFormatter = DateTimeFormatter.ISO_DATE
    val date = LocalDate.parse(this, dateFormatter)

    return try {
        date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    } catch (e: Exception) {
        null
    }
}