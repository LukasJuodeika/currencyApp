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


    override fun equals(other: Any?): Boolean {
        if(other !is InnerRates)
            return false

        val o : InnerRates = other

        if(o.superReduced == superReduced &&
            o.reduced == reduced &&
            o.reduced1 == reduced1 &&
            o.reduced2 == reduced2 &&
            o.standard == standard &&
            o.parking == parking)
            return true

        return false
    }
}