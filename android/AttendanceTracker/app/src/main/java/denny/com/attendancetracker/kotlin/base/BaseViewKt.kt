package denny.com.attendancetracker.kotlin.base

interface BaseView {

    fun noInternetConnection()
    fun showLoading()
    fun hideLoading()
    fun error(val errorMessage : String)
}