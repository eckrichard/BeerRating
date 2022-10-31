package hu.bme.aut.android.hf.beerrating.fragments

class DatePickerHelper {
    fun makeDateString(day: Int, month: Int, year: Int): String {
        return year.toString() + " " + getMonthFormat(month) + " " + day.toString()
    }

    fun getMonthFormat(month: Int): String? {
        if (month == 1) return "JAN"
        if (month == 2) return "FEB"
        if (month == 3) return "MAR"
        if (month == 4) return "APR"
        if (month == 5) return "MAY"
        if (month == 6) return "JUN"
        if (month == 7) return "JUL"
        if (month == 8) return "AUG"
        if (month == 9) return "SEP"
        if (month == 10) return "OCT"
        if (month == 11) return "NOV"
        return if (month == 12) "DEC" else "JAN"
    }

    fun getMonthFormat(month: String): String? {
        if (month == "JAN") return "01"
        if (month == "FEB") return "02"
        if (month == "MAR") return "03"
        if (month == "APR") return "04"
        if (month == "MAY") return "05"
        if (month == "JUN") return "06"
        if (month == "JUL") return "07"
        if (month == "AUG") return "08"
        if (month == "SEP") return "09"
        if (month == "OCT") return "10"
        if (month == "NOV") return "11"
        return if (month == "DEC") "12" else "01"
    }
}