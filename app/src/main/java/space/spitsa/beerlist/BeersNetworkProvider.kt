package space.spitsa.beerlist

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object BeerApiClient {
    private const val BASE_URL = "https://api.punkapi.com/v2/"
    val apiClient: BeerServiceApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return@lazy retrofit.create(BeerServiceApi::class.java)
    }
}

interface BeerServiceApi{
    @GET("beers")
    fun getData() : Call<List<Beer>>


}

class BeersNetworkProvider {
    val TAG = "BeersNetworkProvider"
    suspend fun provide()= withContext(Dispatchers.IO){
        val call = BeerApiClient.apiClient.getData()
        var beers:List<Beer>?=null
        try {
            val response = call.awaitResponse()
            if (!response.isSuccessful){
                Log.e(TAG,"response is not successful")
            }
            else
            {
                beers = response.body()!!
                Log.e(TAG,response.body().toString())
                beers!!.forEach{beer -> Log.e(TAG,beer.name.orEmpty())}
            }
        } catch (t:Throwable){
            Log.e(TAG,t.toString())
        }
        beers
    }

}