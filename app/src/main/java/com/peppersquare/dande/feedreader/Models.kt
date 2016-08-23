package com.peppersquare.dande.feedreader

import java.io.Serializable

/**
 * Created by dande on 12-08-2016.
 */
data class FeedReaderModel(val id: Int =0 ,
                           val title: String,
                           val author: String,
                           val description: String,
                           val image: String  ,
                           val published: Boolean = true,
                           val likes: Int = 0)
