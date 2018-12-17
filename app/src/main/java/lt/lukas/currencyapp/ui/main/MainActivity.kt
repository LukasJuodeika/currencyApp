package lt.lukas.currencyapp.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*
import com.amitshekhar.DebugDB
import kotlinx.android.synthetic.main.activity_main.*
import lt.lukas.currencyapp.R
import lt.lukas.currencyapp.RateAdapter
import lt.lukas.currencyapp.data.objects.InnerRates
import lt.lukas.currencyapp.data.objects.Rate
import lt.lukas.currencyapp.workers.DownloadWorker
import org.koin.android.viewmodel.ext.android.viewModel
import java.math.BigDecimal
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private var mListItems: ArrayList<Rate> = arrayListOf()
    private lateinit var spinnerAdapter: RateAdapter
    private var currentTaxRate = -1F


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("myUrl", DebugDB.getAddressLog())

        spinnerAdapter = RateAdapter(this,android.R.layout.simple_spinner_dropdown_item,mListItems)


        country_spinner.adapter = spinnerAdapter

        mainViewModel.getTaxes()
            .observe(this, Observer {
                currentTaxRate = it
            })



        country_spinner.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(mListItems.size > 0){
                    mainViewModel.setInnerRates(mListItems[position])
                    mainViewModel.selectedCountry.value = position
                }
            }
        }

        mainViewModel.getFullRates()
            .observe(this, Observer {
                Log.d("observer","data changed")
                mListItems = it
                spinnerAdapter = RateAdapter(this,android.R.layout.simple_spinner_dropdown_item,mListItems)
                country_spinner.adapter = spinnerAdapter
            })


        get_info_button.setOnClickListener {
            mainViewModel.updateDatabase()
        }



        amount.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.toString().toBigDecimalOrNull() != null)
                {
                    mainViewModel.updateAmount(s.toString().toBigDecimal())
                }
                else
                    mainViewModel.updateAmount(BigDecimal.ZERO)
            }
        })


        mainViewModel.getInnerRates()
            .observe(this, Observer {
                setRadioButtons(it)
            })


        mainViewModel.getSumAfterTax()
            .observe(this, Observer {
                after_tax_edittext.text = it.toString()
            })


        mainViewModel.getSelectedCountry()
            .observe(this, Observer {
                country_spinner.post {
                    country_spinner.setSelection(it, false)
                }
            })

    }

    private fun setRadioButton(value: Float?)
    {
        if(value != null)
        {
            var view : RadioButton = layoutInflater.inflate(R.layout.taxes_radio_button,null)as RadioButton
            view.text = value.toString()
            view.setOnClickListener {
                mainViewModel.updateTaxes(value)
            }
            taxes_radio_group.addView(view)
            if(value == currentTaxRate)
                view.performClick()
        }
    }

    private fun setRadioButtons(innerRates: InnerRates)
    {
        taxes_radio_group.removeAllViews()
        setRadioButton(innerRates.parking)
        setRadioButton(innerRates.reduced)
        setRadioButton(innerRates.reduced1)
        setRadioButton(innerRates.reduced2)
        setRadioButton(innerRates.standard)
        setRadioButton(innerRates.superReduced)
    }
}
