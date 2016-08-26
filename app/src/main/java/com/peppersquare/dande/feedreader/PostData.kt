package com.peppersquare.dande.feedreader


import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_post_data.*
import kotlinx.android.synthetic.main.feedlist.view.*
import java.io.ByteArrayOutputStream
import java.io.File


class PostData : AppCompatActivity() {

    private val TAG = "Post Data"
    var imageLink: String? = null
    private val PICK_FROM_CAMERA = 100
    var imageTemp: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_data)

        upload_Image.setOnClickListener {
            val i = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE, null)
            startActivityForResult(i, PICK_FROM_CAMERA)

        }
        postButton.setOnClickListener {

            val returnIntent = Intent()
            returnIntent.putExtra("title", titleEdit.text.toString())
            returnIntent.putExtra("author", author.text.toString())
            returnIntent.putExtra("description", description.text.toString())
            returnIntent.putExtra("image", imageLink)
            Log.d(TAG,"setOnClickListener postButton Click :  $imageLink")
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode === PICK_FROM_CAMERA) {
            val photo = data!!.extras.get("data") as Bitmap
            previewImage.setImageBitmap(photo)
            imageLink = BitMapToString(photo)
            Log.d(TAG,"onActivityResult :  $imageLink")
        }
    }
    fun BitMapToString(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        imageTemp = Base64.encodeToString(b, Base64.DEFAULT)
        Log.d(TAG,"BitMapToString Return String :  $imageTemp")
        return imageTemp!!
    }

}
