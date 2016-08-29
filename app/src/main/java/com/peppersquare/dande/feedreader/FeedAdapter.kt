package com.peppersquare.dande.feedreader

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.nfc.Tag
import android.support.v7.widget.RecyclerView
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_post_data.view.*
import kotlinx.android.synthetic.main.feedlist.view.*
import nl.komponents.kovenant.ui.defaultDispatcherContextBuilder
import java.io.Serializable
import java.util.*

/**
 * Created by dande on 11-08-2016.
 */
class FeedAdapter(val feedItems: ArrayList<FeedReaderModel>,val feedItemListener:FeedItemListener) : RecyclerView.Adapter<FeedAdapter.FeedHolder>() {

    val TAG = "FeedAdapter"
    override fun getItemCount(): Int {
        return feedItems.size
    }

    override fun onBindViewHolder(holder: FeedHolder?, position: Int) {
        holder?.bindData(feedItems)
        Log.e(TAG, "onBindViewHolder $feedItems")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedHolder? {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.feedlist, parent, false)
        return FeedHolder(view,feedItemListener)
        Log.e(TAG, "onCreateViewHolder $feedItems")
    }

    class FeedHolder(itemView: View,val feedItemListener: FeedItemListener) : RecyclerView.ViewHolder(itemView) {
        val TAG = "FeedHolder"
        fun bindData(feedItems: ArrayList<FeedReaderModel>) {
            val feedmodel = feedItems[adapterPosition]
            itemView.textView_title.text = feedmodel.title
            itemView.textView_author.text = feedmodel.author
            itemView.textView_description.text = feedmodel.description
            itemView.likes.text = feedmodel.likes.toString()

            if (!feedmodel.image.isNullOrBlank()) {
                val imageAsBytes = Base64.decode(feedmodel.image, Base64.NO_PADDING)
                itemView.imageView.setImageBitmap(
                        BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.size))
                Log.d(TAG, "bindData  $imageAsBytes")
            }

            itemView.imageView_like.setOnClickListener {
                var likeCount = feedmodel.likes
                likeCount = likeCount.plus(1)
                feedmodel.likes = likeCount
                itemView.likes.text = likeCount.toString()
                feedItemListener.onLikeClicked(feedmodel)
            }


            itemView.imageView_share.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT, feedmodel.title + "\n" + feedmodel.author + "\n" + feedmodel.description)
                itemView.context.startActivity(Intent.createChooser(intent, "share the post"))
            }
        }
    }

    interface FeedItemListener {
        fun onLikeClicked(model: FeedReaderModel)
    }
}




