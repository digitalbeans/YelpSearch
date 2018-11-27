package net.digitalbeans.yelpsearch

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.category_list_item.view.*

class CategoryAdapter(var items : ArrayList<Category>, val context: Context) : RecyclerView.Adapter<CategoryViewHolder>() {

    val activity: CategoriesActivity = context as CategoriesActivity

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.category_list_item, parent, false), activity)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = items[position]
        holder.title.text = category.title
        holder.itemView.setOnClickListener { v ->

            val returnIntent: Intent = Intent()
            returnIntent.putExtra("result", category.alias)
            activity.setResult(Activity.RESULT_OK, returnIntent)
            activity.finish()

        }
    }

    fun swap(list: ArrayList<Category>) {
        if (items != null) {
            items.clear()
            items.addAll(list)
        } else {
            items = list
        }
        notifyDataSetChanged()
    }
}

class CategoryViewHolder(view: View, activity: CategoriesActivity): RecyclerView.ViewHolder(view) {
    private var view: View = view
    val title = view.title
    val categoriesActivity = activity

    companion object {
        private val CATEGORY_KEY = "CATEGORY"
    }
}
