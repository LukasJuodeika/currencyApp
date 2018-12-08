package lt.lukas.currencyapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import lt.lukas.currencyapp.data.objects.InnerRates
import lt.lukas.currencyapp.data.objects.Rate
import lt.lukas.currencyapp.model.MainRepository

class MainViewModel(private val mainRepository: MainRepository) : ViewModel()
{

    private var innerRates: MutableLiveData<InnerRates> = MutableLiveData()

    private var sumAfterTax: MutableLiveData<Float> = MutableLiveData()

    private var taxes: MutableLiveData<Float> = MutableLiveData()

    private var sum: Float = 0F



    fun getFullRates() = mainRepository.getFullRates()

    fun getSumAfterTax() = sumAfterTax

    fun updateTaxes(taxes: Float)
    {
        this.taxes.value = taxes
        calculateTaxes()
    }

    fun updateAmount(sum: Float)
    {
        this.sum = sum
        calculateTaxes()

    }


    fun updateDatabase()
    {
        mainRepository.parseNewData()
    }

    fun setInnerRates(rate: Rate)
    {
        if(rate.periods.size > 0)
            innerRates.value = rate.periods[0].innerRates
    }

    fun getInnerRates(): LiveData<InnerRates>
    {
        return innerRates
    }

    fun calculateTaxes(){
        var newTax = 0F
        if(taxes.value!=null)
            newTax = taxes.value!!
        sumAfterTax.value = sum * ( 1 + newTax/100)
    }
}