package lt.lukas.currencyapp.data.ws

import io.reactivex.Maybe
import io.reactivex.Single
import retrofit2.http.GET

interface WebService
{
    @GET(".")
    fun getData(): Single<MainResponseBody>
}