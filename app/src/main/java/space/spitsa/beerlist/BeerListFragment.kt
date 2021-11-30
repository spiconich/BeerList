package space.spitsa.beerlist

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.launch

class BeerListFragment : MyInterface,Fragment() {
    private val TAG = "BeerListFragment"
    private var beerList:List<Beer>?=null
    lateinit var mContext: Context

    @Override
    override fun onClick(beer:Beer){
        Log.e("interface", beer.name!!)
        val bundle = Bundle()
        bundle.putInt("id",beer.id!!)
        bundle.putString("name",beer.name)
        bundle.putString("tagline",beer.tagline!!)
        bundle.putString("firstBrewed",beer.firstBrewed!!)
        bundle.putString("description",beer.description!!)
        bundle.putString("imageUrl",beer.imageUrl!!)
        bundle.putFloat("abv",beer.abv!!)
        bundle.putFloat("ibu",beer.ibu!!)
        val manager = (mContext as FragmentActivity).supportFragmentManager
        val transaction = manager.beginTransaction()
        val beerDetailFragment = BeerDetailFragment()
        beerDetailFragment.arguments = bundle
        transaction.replace(R.id.list_fragment, beerDetailFragment)
        transaction.addToBackStack(beerDetailFragment::class.java.name);
        transaction.commit()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext=context
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
        lifecycleScope.launch {
            //
            if (beerList==null) {
                val beersAnswer = BeersNetworkProvider()
                beerList = beersAnswer.provide()
            }
            else
            {
                Log.e(TAG,"Using downloaded data")
            }
                if (beerList != null) {
                    recyclerView!!.adapter =
                        BeerAdapter(this@BeerListFragment, beerList!!, R.layout.beer_list_item)
                    val shimmer = view.findViewById<ShimmerFrameLayout>(R.id.shimmer_list_container)
                    shimmer.stopShimmer()
                    shimmer.hideShimmer()
                    shimmer.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                } else
                    Log.e(TAG, "beer list is empty")

        }
        super.onViewCreated(view, savedInstanceState)
    }
}