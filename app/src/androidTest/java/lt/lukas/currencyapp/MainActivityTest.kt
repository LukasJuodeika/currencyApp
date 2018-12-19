package lt.lukas.currencyapp

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import lt.lukas.currencyapp.data.objects.Rate
import lt.lukas.currencyapp.ui.main.MainActivity
import org.hamcrest.Matchers.instanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest
{
    @Rule
    @JvmField
    val rule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun countryPickTest(){

        Espresso.onView(withId(R.id.country_spinner)).perform(click())
        Espresso.onData(instanceOf(Rate::class.java))
            .atPosition(19)
            .perform(click())

        Espresso.onView(withText("21.0")).check(matches(isChecked()))

    }
    @Test
    fun taxChangeTest() {

        val text = "100"
        val textWithTax1 = "121.00"
        val textWithTax2 = "104.00"

        Espresso.onView(withId(R.id.amount))
            .perform(ViewActions.typeText(text))

        Espresso.onView(withId(R.id.after_tax_edittext))
            .check(matches(withText(textWithTax1)))

        Espresso.onView(withText("4.0")).perform(click())

        Espresso.onView(withId(R.id.after_tax_edittext))
            .check(matches(withText(textWithTax2)))

    }

}
