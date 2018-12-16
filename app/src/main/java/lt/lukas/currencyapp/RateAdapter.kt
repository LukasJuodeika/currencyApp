package lt.lukas.currencyapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import lt.lukas.currencyapp.data.objects.Rate

class RateAdapter(context: Context, private val layoutResource: Int, private var rates: ArrayList<Rate>):
    ArrayAdapter<Rate>(context, layoutResource, rates) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return createViewFromResource(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return createViewFromResource(position, convertView, parent)
    }

    private fun createViewFromResource(position: Int, convertView: View?, parent: ViewGroup?): View{
        val view: TextView = convertView as TextView? ?: LayoutInflater.from(context).inflate(layoutResource, parent, false) as TextView
        view.text = rates[position].name
        return view
    }

    override fun addAll(vararg items: Rate?) {
//        clear()
//        rates = items as ArrayList<Rate>
//        this.notifyDataSetChanged()
        super.addAll(*items)
    }
}