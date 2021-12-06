package space.spitsa.beerlist

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.launch
import java.lang.Exception


class BeerListFragment : MyInterface, ClickedImageInterface, Fragment() {

    private val TAG = "BeerListFragment"
    private var beerList: List<Beer>? = null
    lateinit var mContext: Context
    private var clickedImage: Drawable? = null

    @Override
    override fun getImage(): Drawable? {
        //TODO:drawable
        return clickedImage
    }

    @Override
    override fun onClick(beer: Beer, image: Drawable?) {

        Log.e("interface", beer.name!!)

        val bundle = Bundle()
        try {

            bundle.putInt("id", beer.id!!)
            bundle.putString("name", beer.name)
            bundle.putString("tagline", beer.tagline!!)
            bundle.putString("firstBrewed", beer.firstBrewed!!)
            bundle.putString("description", beer.description!!)
            bundle.putString("imageUrl", beer.imageUrl!!)
            bundle.putFloat("abv", beer.abv!!)
            bundle.putFloat("ibu", beer.ibu!!)

        } catch (e: Exception) {
            Log.e("interface", "Some row was empty")
        }

        clickedImage = image //TODO: приемлимо?

        /**
         * val manager = requireActivity().supportFragmentManager
         *
         * и не нужен mContext
         */
        val manager = (mContext as FragmentActivity).supportFragmentManager
        val transaction = manager.beginTransaction()
        val beerDetailFragment = BeerDetailFragment(this@BeerListFragment)
        beerDetailFragment.arguments = bundle
        transaction.replace(R.id.list_fragment, beerDetailFragment)
        transaction.addToBackStack(beerDetailFragment::class.java.name);
        transaction.commit()
    }

    /**
     * Ошибка из прошлого приложения, контекст так не сохраняем, мы его просто из фрагмента в любом месте получить можем
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_beer_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        /**
         * Можно писать вот так, чтобы NPE не было:
         *
         * val asd = getNullableValue() ?: return
         *
         * getNullableValue() возвращает Qwerty?
         *
         * после такой проверки asd - Qwerty
         *
         * ?: - называется елвис оператор
         *
         */
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view) ?: return
        recyclerView.layoutManager = LinearLayoutManager(context)
        //здесь будем стратовать запрос в сеть а после - передавать полученные данные в адаптер
        lifecycleScope.launch {
            //
            if (beerList == null) {
                val beersAnswer = BeersNetworkProvider()
                beerList = beersAnswer.provide()
            } else {
                Log.e(TAG, "Using downloaded data")
            }
            if (beerList != null) {
                recyclerView!!.adapter =
                    BeerAdapter(this@BeerListFragment, beerList!!, R.layout.beer_list_item)
                val shimmer = view.findViewById<ShimmerFrameLayout>(R.id.shimmer_list_container)
                shimmer.stopShimmer()
                shimmer.hideShimmer()
                /**
                 * Можно вот так, чтобы не импортить View
                 *
                 * shimmer.isVisible = false
                 * recyclerView.isVisible = true
                 */
                shimmer.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            } else
                Log.e(TAG, "beer list is empty")

        }
        super.onViewCreated(view, savedInstanceState)
    }
}