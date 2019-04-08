package denny.com.attendancetracker.kotlin.main

interface MainKtContract {

    interface View{
        fun showSignInScreen()
        fun showSignUpScreen()
    }

    interface Presenter{
        fun handleSignInClicked()
        fun handleSignUpClicked()
    }
}