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

interface ClickedImageInterface {

    fun getImage(): Drawable?
}

class BeerDetailFragment(

    private val imageListener: ClickedImageInterface

) : Fragment() {

    private val TAG = "BeerDetailFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_beer_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar_layout)
        toolbar.setNavigationIcon(R.drawable.ic_action_name);
        toolbar.setNavigationOnClickListener {

            Log.e(TAG, "back arrow pressed")

            /**
             * Лучше так requireActivity().supportFragmentManager.popBackStack()
             *
             * Активити, которой прикреплен фрагмент можно получить через
             *
             * свойство activity - в таком случае возвращаемый тип FragmentActivity! (nullable)
             * метод requireActivity() вернет FragmentActivity (not-nullable)
             */
            getFragmentManager()?.popBackStackImmediate()
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
        val toolbarTV = view.findViewById<TextView>(R.id.toolbar_text)
        val ibuTV = view.findViewById<TextView>(R.id.beer_list_detail_ibu)
        val abvTV = view.findViewById<TextView>(R.id.beer_list_detail_abv)
        val imageV = view.findViewById<ImageView>(R.id.beer_list_detail_image)
        val image = imageListener.getImage()

        if (image == null) {
            Log.e(TAG, "image is empty")
            Picasso.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(imageV)
        } else {

            Log.e(TAG, "image is not empty")
            imageV.setImageDrawable(image)
        }
        descriptionTV.text = description
        taglineTV.text = getResources().getString(R.string.tagline) + " " + tagline;
        firstBrewedTV.text = getResources().getString(R.string.firstBrewed) + " " + firstBrewed
        idTV.text = getResources().getString(R.string.id) + " " + id.toString()
        toolbarTV.text = name
        ibuTV.text = "Ibu: " + ibu.toString()
        abvTV.text = "Abv: " + abv.toString()

        /**
         * Тут ниже я покажу как лучше работать со строками на примерах полей выше
         *
         * Если ты хочешь вставить в строку ресурсов какое-то значение, то после идентификатора строки
         * необходимо передать аргументы:
         *
         * Для передачи одного аргумента в ресурсах указываем %s в месте, где хотим вставить строку
         *
         * Так же существуют разные модификаторы для разных типов данных - %s, %d, %f и т.д.
         *
         * Для передачи нескольких аргументов используем нумерацию $1%s, $2%s и т.д.
         *
         * Пример:
         *
         * <string name="my_custom_example_template">Список аргументов: аргумент1=$1%s, аргумент2=$2%s</string>
         *
         * resources.getString(
         *     R.string.my_custom_example_template,
         *     firstArgument,
         *     secondArgument
         * ) = "Список аргументов: аргумент1=Foo, аргумент2=Bar"
         *
         */
        taglineTV.text = resources.getString(

            R.string.tagline_renat_fixes,
            tagline
        )

        /**
         * Что тут происходит?
         *
         * Это называется string templates
         *
         * Для java : String asd = "n = " + n;
         * Для котлина : val asd = "n = $n"
         *
         * Если ты хочешь вызвать таким образом какое-то свойство класса, то тогда
         * val asd = "n = ${object.n}"
         *
         * Что происходит под капотом?
         *
         * для переменной n, object.n вызывается метод toString()
         *
         * Далее строки конкатенируются как в java
         *
         * под капотом:
         * abvTV.text = StringBuilder().append("Abv: ").append(abv.toString)
         */
        abvTV.text = "Abv: $abv"
    }

}