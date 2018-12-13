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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //mainViewModel = ViewModelProviders.of(this)
          //  .get(MainViewModel(application as BaseApplication)::class.java)


        Log.d("myUrl", DebugDB.getAddressLog())

        spinnerAdapter = RateAdapter(this,android.R.layout.simple_spinner_dropdown_item,mListItems)


        country_spinner.adapter = spinnerAdapter



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
                country_spinner.adapter = null
                spinnerAdapter.addAll(mListItems)
                country_spinner.adapter = spinnerAdapter
            })


        get_info_button.setOnClickListener {
            Toast.makeText(this, "Network call started", Toast.LENGTH_SHORT).show()
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
                country_spinner.setSelection(it, true)
            })

    }

    fun setRadioButtons(innerRates: InnerRates)
    {
        taxes_radio_group.removeAllViews()
        var view : RadioButton
        if(innerRates.parking != null)
        {
            view = layoutInflater.inflate(R.layout.taxes_radio_button,null)as RadioButton
            view.text = innerRates.parking.toString()
            view.setOnClickListener {
                mainViewModel.updateTaxes(innerRates.parking!!)
            }
            taxes_radio_group.addView(view)
        }
        if(innerRates.reduced != null)
        {
            view = layoutInflater.inflate(R.layout.taxes_radio_button,null)as RadioButton
            view.text = innerRates.reduced.toString()
            view.setOnClickListener {
                mainViewModel.updateTaxes(innerRates.reduced!!)
            }
            taxes_radio_group.addView(view)
        }
        if(innerRates.reduced1 != null)
        {
            view = layoutInflater.inflate(R.layout.taxes_radio_button,null)as RadioButton
            view.text = innerRates.reduced1.toString()
            view.setOnClickListener {
                mainViewModel.updateTaxes(innerRates.reduced1!!)

            }
            taxes_radio_group.addView(view)
        }
        if(innerRates.reduced2 != null)
        {
            view = layoutInflater.inflate(R.layout.taxes_radio_button,null)as RadioButton
            view.text = innerRates.reduced2.toString()
            view.setOnClickListener {
                mainViewModel.updateTaxes(innerRates.reduced2!!)
            }
            taxes_radio_group.addView(view)
        }
        if(innerRates.standard != null)
        {
            view = layoutInflater.inflate(R.layout.taxes_radio_button,null)as RadioButton
            view.text = innerRates.standard.toString()
            view.setOnClickListener {
                mainViewModel.updateTaxes(innerRates.standard!!)

            }
            taxes_radio_group.addView(view)
            view.performClick()
        }
        if(innerRates.superReduced != null)
        {
            view = layoutInflater.inflate(R.layout.taxes_radio_button,null)as RadioButton
            view.text = innerRates.superReduced.toString()
            view.setOnClickListener {
                mainViewModel.updateTaxes(innerRates.superReduced!!)
            }
            taxes_radio_group.addView(view)
        }
    }
}
