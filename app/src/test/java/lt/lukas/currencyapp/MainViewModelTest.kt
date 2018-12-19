package lt.lukas.currencyapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import lt.lukas.currencyapp.data.objects.InnerRates
import lt.lukas.currencyapp.ui.main.MainViewModel
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnit
import java.math.BigDecimal


class   MainViewModelTest
{
    @get:Rule
    val mockitoRule = MockitoJUnit.rule()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @InjectMocks
    lateinit var mainViewModel: MainViewModel

    @Test
    fun `test updateTaxes and updateAmount`() {
        val taxes = 40F
        mainViewModel.updateTaxes(taxes)
        val taxesResult =mainViewModel.getTaxes().value
        Assert.assertEquals(taxes,taxesResult)

        mainViewModel.updateAmount(BigDecimal("100"))
        val sumAfterTaxResult = mainViewModel.getSumAfterTax().value
        Assert.assertEquals(BigDecimal("140.00"),sumAfterTaxResult)
    }

    @Test
    fun `test calculateTaxes method`()
    {
        val a = mainViewModel.calculateTaxes(21F, BigDecimal("100"))
        Assert.assertEquals(BigDecimal("121.00"), a)
    }

    @Test
    fun `test InnerRates equals method`()
    {
        val innerRates1 = InnerRates()
        val innerRates2 = InnerRates()
        innerRates1.reduced = 5F
        innerRates1.parking = 11F
        innerRates1.standard = 22F

        innerRates2.reduced = 5F
        innerRates2.parking = 11F
        innerRates2.standard = 22F

        Assert.assertEquals(innerRates1, innerRates2)
    }
}

