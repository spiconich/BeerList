package space.spitsa.beerlist

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
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
    suspend fun provide():List<Beer>?{
        val call = BeerApiClient.apiClient.getData()
        var beers:List<Beer>?=null
        call.enqueue(object: Callback<List<Beer>>{
            override fun onResponse(call: Call<List<Beer>>, response: Response<List<Beer>>) {
                beers = response.body()!!
                beers!!.forEach{beer -> Log.e(TAG,beer.name.orEmpty())}
            }

            override fun onFailure(call: Call<List<Beer>>, t: Throwable) {
                Log.e(TAG,t.toString())
            }
        })
        return beers
    }

}