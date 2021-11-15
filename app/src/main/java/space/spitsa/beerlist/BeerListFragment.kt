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
    private val TAG = "BeerListFragment"
    lateinit var mContext: Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_beer_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)


        //здесь будем стратовать запрос в сеть а после - передавать полученные данные в адаптер
        GlobalScope.launch {
            //
            val beersAnswer = BeersNetworkProvider()
            val beerList = beersAnswer.provide()
            if (beerList != null) {
                recyclerView!!.adapter = BeerAdapter(beerList, R.layout.beer_list_item)
            } else
                Log.e(TAG, "beer list is empty")

        }

        super.onViewCreated(view, savedInstanceState)
    }
}