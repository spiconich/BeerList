package space.spitsa.beerlist

import android.util.Log
import retrofit2.Retrofit

interface BeerServiceApi{
    fun getData() {
        //описывается метод получения данных
        
    }
}

class BeersNetworkProvider:BeerServiceApi {
    private val TAG ="BeersNetworkProvider"
    private var retrofit: Retrofit?=null
    private val id: Int? = null
    private val name :String? = null
    private val tagline:String? = null
    private val first_brewed:String? =null
    private val description:String?=null
    private val image_url:String?=null
    private val abv:Int?=null
    private val target_fg:Int?=null
    private val target_og:Int?=null
    private val ebc:Int?=null
    private val srm:Int?=null
    private val ph:Int?=null
    private val attenuation_level:Int?=null
    fun provide(){
        Log.e(TAG,"provide method starts")
    }

}