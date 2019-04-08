package denny.com.attendancetracker.kotlin.signup

import denny.com.attendancetracker.kotlin.base.BaseViewKt

interface SignUpContract{

    interface View: BaseViewKt{
        fun success(message: String)
    }

    interface Presenter{
        fun signUp()
    }

}