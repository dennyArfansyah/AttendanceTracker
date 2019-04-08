package denny.com.attendancetracker.kotlin.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import denny.com.attendancetracker.R
import denny.com.attendancetracker.kotlin.signin.SignInActivityKt
import denny.com.attendancetracker.kotlin.signup.SIgnUpActivityKt
import org.jetbrains.anko.startActivity

class MainActivityKt : AppCompatActivity(), MainKtContract.View{

    private lateinit var presenter: MainKtPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_authentication)
        presenter = MainKtPresenter(this)
    }

    override fun showSignInScreen() {
        startActivity(Intent(this, SignInActivityKt::class.java))
    }

    override fun showSignUpScreen() {
        startActivity(Intent(this, SIgnUpActivityKt::class.java))
    }



}