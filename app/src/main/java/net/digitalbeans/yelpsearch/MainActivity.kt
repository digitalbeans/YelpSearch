package net.digitalbeans.yelpsearch

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import android.util.Log
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.location.Location
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import java.util.jar.Manifest
import android.content.Intent
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v7.widget.RecyclerView
import android.view.MenuInflater
import kotlinx.android.synthetic.main.business_list_item.view.*
import net.digitalbeans.yelpsearch.R.id.linearLayoutManager
import net.digitalbeans.yelpsearch.R.id.rv_business_list

class MainActivity : AppCompatActivity() {

    private val BASE_URL = "https://api.yelp.com/"
    private var businessList: ArrayList<Business> = ArrayList()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val ADD_TASK_REQUEST = 1
    private var currentLocation: Location? = null
    private lateinit var scrollListener: RecyclerView.OnScrollListener
    private var category: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->

                currentLocation = location
                getBusinesses(currentLocation, null, 0, true)
            }
        }

//        getBusinesses(null, null)

        rv_business_list.layoutManager = LinearLayoutManager(this)
        rv_business_list.adapter = BusinessAdapter(businessList, this)

        setRecyclerViewScrollListener()
    }

    private fun setRecyclerViewScrollListener() {
        scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = rv_business_list.layoutManager.itemCount
                val linearLayoutManager = rv_business_list.layoutManager as LinearLayoutManager
                val lastVisible = linearLayoutManager.findLastVisibleItemPosition()
                if (totalItemCount == lastVisible + 1) {
                    Log.d("MyTAG", "Load new list")
                    getBusinesses(currentLocation, category, totalItemCount, false)
                }
            }
        }
        rv_business_list.addOnScrollListener(scrollListener)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_filter -> {
                val intent = Intent(this, CategoriesActivity::class.java)
                startActivityForResult(intent, ADD_TASK_REQUEST)
            }
        }

        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val selectedCategory = data.extras.getString("result")
                category = selectedCategory
                if (selectedCategory.isNotEmpty()) {
                    getBusinesses(currentLocation, selectedCategory, 0, true)
                }
            }
        }
    }

    fun getBusinesses(location: Location?, category: String?, offset: Int, reload: Boolean = true) {

        if (location != null) {
            var retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            var searchApi = retrofit.create((Search::class.java))
            var call =
                searchApi.getBusinesses("restaurants", offset, location.latitude, location.longitude, "distance", category)

            call.enqueue(object : Callback<Businesses> {

                override fun onResponse(call: Call<Businesses>, response: Response<Businesses>) {
                    var categories = response.body()
                    var businesses = categories?.businesses
                    businessList = ArrayList(businesses)

                    var adapter: BusinessAdapter = rv_business_list.adapter as BusinessAdapter
                    if (reload) {
                        adapter.items = businessList
                    }
                    else {
                        adapter.items.addAll(businessList)
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<Businesses>?, t: Throwable?) {
                    Log.v("Error", t.toString())
                }
            })
        }
    }
}
