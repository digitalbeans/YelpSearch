package net.digitalbeans.yelpsearch

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.ProgressBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.android.synthetic.main.activity_categories.*

class CategoriesActivity : AppCompatActivity() {

    private val SEARCH_URL = "https://api.yelp.com/"
    private var categoryList: ArrayList<Category> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        getCategories()
        rv_category_list.layoutManager = LinearLayoutManager(this)
        rv_category_list.adapter = CategoryAdapter(categoryList, this)
    }

    fun getCategories() {

        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(SEARCH_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var searchApi = retrofit.create((CategoryApi::class.java))
        var call = searchApi.categories

        call.enqueue(object : Callback<Categories> {

            override fun onResponse(call: Call<Categories>, response: Response<Categories>) {
                var categories = response.body()
                categoryList = ArrayList(categories?.categories?.filter { s -> s.parentAliases?.contains("restaurants") })
                categoryList = ArrayList(categoryList.sortedWith(compareBy( { it.title })))

                var adapter: CategoryAdapter = rv_category_list.adapter as CategoryAdapter
                adapter.items = categoryList
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<Categories>?, t: Throwable?) {
                Log.v("Error", t.toString())
            }
        })
    }
}