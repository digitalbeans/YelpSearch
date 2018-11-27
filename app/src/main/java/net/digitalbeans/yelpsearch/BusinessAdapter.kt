package net.digitalbeans.yelpsearch

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.business_list_item.view.*

class BusinessAdapter(var items : ArrayList<Business>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.business_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val business = items[position]
        holder.title.text = business.name
        holder.subtitle.text = business.location?.description

        val miles = business.distance / 1000 * 0.621371
        holder.distance.text = "${"%.2f".format(miles)} miles"

        if (business.imageURL.isNotBlank()) {
            Picasso.get().load(business.imageURL).into(holder.image)
        }
    }

    fun swap(list: ArrayList<Business>) {
        if (items != null) {
            items.clear()
            items.addAll(list)
        } else {
            items = list
        }
        notifyDataSetChanged()
    }
}

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val title = view.title
    val subtitle = view.subtitle
    val distance = view.distance
    val image = view.image
}
