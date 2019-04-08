package denny.com.attendancetracker.kotlin.main

class MainKtPresenter(val view: MainKtContract.View) : MainKtContract.Presenter{

    override fun handleSignInClicked() {
        view.showSignInScreen()
    }

    override fun handleSignUpClicked() {
        view.showSignUpScreen()
    }


}