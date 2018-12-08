package lt.lukas.currencyapp.data.ws

import com.google.gson.annotations.SerializedName
import lt.lukas.currencyapp.data.objects.Rate

class MainResponseBody
{
    @SerializedName("details")
    var details: String = ""

    @SerializedName("version")
    var version: String? = null

    @SerializedName("rates")
    var rates: ArrayList<Rate> = arrayListOf()
}