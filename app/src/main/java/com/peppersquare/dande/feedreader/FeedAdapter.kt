package com.peppersquare.dande.feedreader

import android.app.Activity
import android.app.Application
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_post_data.view.*
import kotlinx.android.synthetic.main.feedlist.view.*
import nl.komponents.kovenant.ui.defaultDispatcherContextBuilder
import java.io.Serializable
import java.util.*

/**
 * Created by dande on 11-08-2016.
 */
class FeedAdapter(val feedItems: ArrayList<FeedReaderModel>) : RecyclerView.Adapter<FeedAdapter.FeedHolder>() {


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

        fun bindData(feedItems: ArrayList<FeedReaderModel>) {

            val feedmodel = feedItems[adapterPosition]

            itemView.textView_title.text = feedmodel.title
            itemView.textView_author.text = feedmodel.author
            itemView.textView_description.text = feedmodel.description
            Picasso.with(itemView.context).load(feedmodel.image).into(itemView.imageView)
        }

    }

}

