package lt.lukas.currencyapp.data.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import lt.lukas.currencyapp.data.objects.Period

@Dao
interface PeriodDao{

    @Insert(onConflict = REPLACE)
    fun insertPeriod(period: Period)

    @Insert(onConflict = REPLACE)
    fun insertPeriods(periods: ArrayList<Period>)

    @Query("SELECT * FROM Period WHERE rateCode = :code")
    fun getPeriodsByCodeSynchronous(code: String): List<Period>

    @Query ("DELETE FROM Period")
    fun deleteAllData()
}