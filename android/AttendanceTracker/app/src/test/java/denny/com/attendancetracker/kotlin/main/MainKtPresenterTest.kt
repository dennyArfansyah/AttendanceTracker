package denny.com.attendancetracker.kotlin.main

import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MainKtPresenterTest {

    @Mock
    private lateinit var view : MainKtContract.View

    private lateinit var presenter: MainKtPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MainKtPresenter(view)
    }

    @Test
    fun handleSignInClicked() {
        presenter.handleSignInClicked()
        Mockito.verify(view).showSignInScreen()
    }

    @Test
    fun handleSignUpClicked() {
        presenter.handleSignUpClicked()
        Mockito.verify(view).showSignUpScreen()
    }
}