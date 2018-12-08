package lt.lukas.currencyapp.data.objects

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class Rate{
    @SerializedName("name")
    var name: String = ""

    @PrimaryKey
    @SerializedName("code")
    var code: String = ""

    @SerializedName("countryCode")
    var countryCode: String = ""

    @Ignore
    @SerializedName("periods")
    var periods: ArrayList<Period> = arrayListOf()
}