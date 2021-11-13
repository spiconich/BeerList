package space.spitsa.beerlist

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BeerListFragment : Fragment() {
    private val TAG ="BeerListFragment"
    private val recyclerView:RecyclerView?=null
    //TODO: непонятно, почему сьехала шапка у фрагмента (как-то связно с тем, что используем кастомный title)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentView=inflater.inflate(R.layout.fragment_beer_list, container, false)
        val recyclerView = fragmentView.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager=LinearLayoutManager(context)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //здесь будем стратовать запрос в сеть а после - передавать полученные данные в адаптер
        GlobalScope.launch {
            //
            val beersAnswer = BeersNetworkProvider()
            val beerList = beersAnswer.provide()
            if (beerList!=null){
                recyclerView!!.adapter= BeerAdapter(beerList,R.layout.beer_list_item)
            }
            else
                Log.d(TAG,"beer list is empty")

        }

        super.onViewCreated(view, savedInstanceState)
    }
}