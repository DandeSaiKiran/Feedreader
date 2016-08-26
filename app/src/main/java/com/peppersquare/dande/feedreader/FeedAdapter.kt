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
class FeedAdapter(val feedItems: ArrayList<FeedReaderModel>) : RecyclerView.Adapter<FeedAdapter.FeedHolder>() {

    val TAG = "FeedAdapter"
    override fun getItemCount(): Int {
        return feedItems.size
    }

    override fun onBindViewHolder(holder: FeedHolder?, position: Int) {
        holder?.bindData(feedItems)
        Log.e(TAG,"onBindViewHolder $feedItems")

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedHolder? {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.feedlist, parent, false)
        return FeedHolder(view)
        Log.e(TAG,"onCreateViewHolder $feedItems")
    }

    class FeedHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var like: Int = 0
        val TAG = "FeedHolder"
        fun bindData(feedItems: ArrayList<FeedReaderModel>) {

            val feedmodel = feedItems[adapterPosition]

            itemView.textView_title.text = feedmodel.title
            itemView.textView_author.text = feedmodel.author
            itemView.textView_description.text = feedmodel.description

            if (!feedmodel.image.isNullOrBlank()) {
                val imageAsBytes = Base64.decode(feedmodel.image, Base64.NO_PADDING)
                itemView.imageView.setImageBitmap(
                        BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.size))
                Log.d(TAG, "bindData  $imageAsBytes")
            }
            itemView.imageView_like.setOnClickListener {
                var i: Int = 1
                if (i >= 0) {
                    like = i++

                    Log.e(TAG,"bindData : $like")

                 //   itemView.text_likes.setText(like)
                }

                //feedmodel.likes = like!!
                Toast.makeText(itemView.context, "likes $like", Toast.LENGTH_SHORT).show()
            }

            itemView.imageView_share.setOnClickListener {

                val intent = Intent()
                intent.setAction(Intent.ACTION_SEND)
                intent.putExtra(Intent.EXTRA_TEXT, feedmodel.title + "\n" + feedmodel.author + "\n" + feedmodel.description)
                itemView.context.startActivity(Intent.createChooser(intent, "share the post"))
            }

        }
    }

}



