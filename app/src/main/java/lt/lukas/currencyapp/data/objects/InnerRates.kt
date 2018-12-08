package lt.lukas.currencyapp.data.objects

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
class InnerRates{

    @SerializedName("super_reduced")
    var superReduced: Float? = null

    @SerializedName("reduced")
    var reduced: Float? = null

    @SerializedName("reduced1")
    var reduced1: Float? = null

    @SerializedName("reduced2")
    var reduced2: Float? = null

    @SerializedName("standard")
    var standard: Float? = null

    @SerializedName("parking")
    var parking: Float? = null


}