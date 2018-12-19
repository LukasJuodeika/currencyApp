package lt.lukas.currencyapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import lt.lukas.currencyapp.data.objects.Rate

@Dao
interface RateDao{

    @Insert(onConflict = REPLACE)
    fun insertRate(rate: Rate)

    @Insert(onConflict = REPLACE)
    fun insertRates(rates: ArrayList<Rate>)

    @Query("SELECT * FROM Rate")
    fun getRates(): LiveData<List<Rate>>

    @Query ("DELETE FROM Rate")
    fun deleteAllData()
}