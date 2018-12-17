package lt.lukas.currencyapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import lt.lukas.currencyapp.data.objects.InnerRates
import lt.lukas.currencyapp.data.objects.Rate
import lt.lukas.currencyapp.ui.main.MainViewModel
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnit
import java.math.BigDecimal


class MainViewModelTest
{


    @get:Rule
    val mockitoRule = MockitoJUnit.rule()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @InjectMocks
    lateinit var mainViewModel: MainViewModel

    @Test
    fun testUpdateTaxes2() {
        val taxes = 40F
        mainViewModel.updateTaxes(taxes)
        val taxesResult =mainViewModel.getTaxes().value
        Assert.assertEquals(taxes,taxesResult)

        mainViewModel.updateAmount(BigDecimal("100"))
        val sumAfterTaxResult = mainViewModel.getSumAfterTax().value
        Assert.assertEquals(BigDecimal("140.00"),sumAfterTaxResult)
    }

    @Test
    fun testTaxCalculation()
    {
        val a = mainViewModel.calculateTaxes(21F, BigDecimal("100"))
        Assert.assertEquals(BigDecimal("121.00"), a)
    }


    
}

