package lt.lukas.currencyapp.data.objects


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(foreignKeys =
[
    ForeignKey(
        entity = Rate::class,
        parentColumns = arrayOf("code"),
        childColumns = arrayOf("rateCode"),
        onDelete = CASCADE)
])
class Period{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @SerializedName("effective_from")
    var name: String = ""

    @Embedded
    @SerializedName("rates")
    var innerRates: InnerRates? = null

    var rateCode: String = ""
}