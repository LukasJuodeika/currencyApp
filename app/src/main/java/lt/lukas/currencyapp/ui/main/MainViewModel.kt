package lt.lukas.currencyapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import lt.lukas.currencyapp.data.objects.InnerRates
import lt.lukas.currencyapp.data.objects.Rate
import lt.lukas.currencyapp.repository.MainRepository
import java.math.BigDecimal

class MainViewModel(private val mainRepository: MainRepository?) : ViewModel()
{

    private var innerRates: MutableLiveData<InnerRates> = MutableLiveData()

    private var sumAfterTax: MutableLiveData<BigDecimal> = MutableLiveData()

    private var taxes: MutableLiveData<Float> = MutableLiveData()

    private var sum: BigDecimal = BigDecimal.ZERO

    var selectedCountry: MutableLiveData<Int> = MutableLiveData()


    fun getSelectedCountry() = selectedCountry as LiveData<Int>

    fun getFullRates() = mainRepository!!.getFullRates()

    fun getSumAfterTax() = sumAfterTax

    fun updateTaxes(taxes: Float)
    {
        this.taxes.value = taxes
        sumAfterTax.value = calculateTaxes(taxes, sum)
    }

    fun getTaxes() = taxes as LiveData<Float>

    fun updateAmount(sum: BigDecimal)
    {
        this.sum = sum
        sumAfterTax.value = calculateTaxes(taxes.value, sum)
    }
    

    fun setInnerRates(rate: Rate)
    {
        if(rate.periods.size > 0 &&
            (innerRates.value == null || !(innerRates.value!!.equals(rate.periods[0].innerRates))))
        {
            updateTaxes(rate.periods[0].innerRates!!.standard!!)
            innerRates.value = rate.periods[0].innerRates
        }

    }

    fun getInnerRates(): LiveData<InnerRates>
    {
        return innerRates
    }

    fun calculateTaxes(tax: Float?, sum: BigDecimal): BigDecimal{
        var newTax = 0F
        if(tax != null)
            newTax = tax
        return sum.multiply(BigDecimal((1 + newTax/100).toString())).setScale(2,BigDecimal.ROUND_HALF_UP)
    }
}