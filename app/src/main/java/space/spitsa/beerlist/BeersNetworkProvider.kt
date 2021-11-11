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
    fun getData() : Call<BeerResponse>


}

class BeersNetworkProvider {
    val TAG = "BeersNetworkProvider"
    suspend fun provide(){
        val call = BeerApiClient.apiClient.getData()
        call.enqueue(object: Callback<BeerResponse>{
            override fun onResponse(call: Call<BeerResponse>, response: Response<BeerResponse>) {
                val beers = response.body()!!.results
                beers.forEach{beer -> Log.e(TAG,beer.name.orEmpty())}
            }

            override fun onFailure(call: Call<BeerResponse>, t: Throwable) {
                Log.e(TAG,t.toString())
            }
        })
        //Log.e(TAG,"provide method starts")
        //дергаем метод интерфейса
        //возвращает лист дата класса
    }

}