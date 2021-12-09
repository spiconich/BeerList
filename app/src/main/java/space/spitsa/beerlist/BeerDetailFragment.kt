package space.spitsa.beerlist

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import com.squareup.picasso.Picasso

interface ClickedImageInterface{
    fun getImage():Drawable?
}
class BeerDetailFragment(private val imageListener: ClickedImageInterface): Fragment() {
    private val TAG="BeerDetailFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_beer_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar= view.findViewById<Toolbar>(R.id.toolbar_layout)
        toolbar.setNavigationIcon(R.drawable.ic_action_name);
        toolbar.setNavigationOnClickListener {
            Log.e(TAG,"back arrow pressed")
            getFragmentManager()?.popBackStackImmediate();
        }
        val bundle = this.arguments
        val id = bundle?.getInt("id")
        val name = bundle?.getString("name")
        val tagline = bundle?.getString("tagline")
        val firstBrewed = bundle?.getString("firstBrewed")
        val description = bundle?.getString("description")
        val ibu = bundle?.getFloat("ibu")
        val abv = bundle?.getFloat("abv")
        val imageUrl = bundle?.getString("imageUrl")
        val descriptionTV = view.findViewById<TextView>(R.id.beer_list_detail_description)
        val taglineTV = view.findViewById<TextView>(R.id.beer_list_detail_tagline)
        val firstBrewedTV = view.findViewById<TextView>(R.id.beer_list_detail_firstBrewed)
        val idTV = view.findViewById<TextView>(R.id.beer_list_detail_id)
        val toolbarV = view.findViewById<Toolbar>(R.id.toolbar_layout)
        val ibuTV=view.findViewById<TextView>(R.id.beer_list_detail_ibu)
        val abvTV=view.findViewById<TextView>(R.id.beer_list_detail_abv)
        val imageV=view.findViewById<ImageView>(R.id.beer_list_detail_image)
        val image = imageListener.getImage()
        if (image==null){
            Log.e(TAG,"image is empty")
            Picasso.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(imageV)
        }
        else
        {
            Log.e(TAG,"image is not empty")
            imageV.setImageDrawable(image)
        }
        descriptionTV.text = description
        taglineTV.text = getResources().getString(R.string.tagline)+" "+tagline;
        firstBrewedTV.text = getResources().getString(R.string.firstBrewed)+" "+firstBrewed
        idTV.text = getResources().getString(R.string.id)+" "+id.toString()
        toolbarV.title = name //TODO:set beer title
        ibuTV.text="Ibu: "+ibu.toString()
        abvTV.text="Abv: "+abv.toString()
    }

}