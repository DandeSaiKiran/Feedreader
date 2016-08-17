package com.peppersquare.dande.feedreader

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_post_data.view.*
import kotlinx.android.synthetic.main.feedlist.view.*
import java.io.Serializable
import java.util.*

/**
 * Created by dande on 11-08-2016.
 */
class FeedAdapter(val feedItems: ArrayList<FeedModel>) : RecyclerView.Adapter<FeedAdapter.FeedHolder>() {

    override fun getItemCount(): Int {
        return feedItems.size
    }

    override fun onBindViewHolder(holder: FeedHolder?, position: Int) {
        holder?.bindData(feedItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedHolder? {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.feedlist, parent, false)
        return FeedHolder(view)
    }

    class FeedHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(feedItems: ArrayList<FeedModel>) {

            val feedmodel = feedItems[adapterPosition]

            itemView.textView_title.text = feedmodel.title
            itemView.textView_author.text = feedmodel.author
            itemView.textView_description.text = feedmodel.description
            itemView.imageView.setImageResource(feedmodel.image)
        }

    }

}

