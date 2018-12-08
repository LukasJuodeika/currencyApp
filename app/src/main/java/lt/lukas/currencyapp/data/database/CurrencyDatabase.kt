package lt.lukas.currencyapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import lt.lukas.currencyapp.data.objects.Period
import lt.lukas.currencyapp.data.objects.Rate


@Database(
    entities =
    [
        Rate::class,
        Period::class
    ],
    version = 2,
    exportSchema = false
)
abstract class CurrencyDatabase : RoomDatabase()
{
    abstract fun ratesDao(): RateDao
    abstract fun periodDao(): PeriodDao
}
