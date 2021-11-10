package space.spitsa.beerlist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BeerList : Fragment() {
    //TODO: непонятно, почему сьехала шапка у фрагмента (как-то связно с тем, что используем кастомный title)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_beer_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //здесь будем стратовать запрос в сеть а после - передавать полученные данные в адаптер
        GlobalScope.launch {
            val beersAnswer= BeersNetworkProvider()
            beersAnswer.provide()
        }

        super.onViewCreated(view, savedInstanceState)
    }
}