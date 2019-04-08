package denny.com.attendancetracker.kotlin.signin

import denny.com.attendancetracker.kotlin.base.BaseViewKt

interface SignInContract {

    interface View: BaseViewKt {
        fun success(message: String)
    }

    interface Presenter{
        fun signInButtonClicked(email: String, password: String)
    }
}