package com.example.uppgift5

import android.app.Activity
import android.content.Intent
import android.graphics.*
import android.icu.number.IntegerWidth
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import android.graphics.LightingColorFilter as LightingColorFilter1


private const val FILE_NAME="photo.jpg"
private lateinit var photoFile:File
private const val REQUEST_CODE=42
public lateinit var takenIMage:Bitmap

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val imageView=findViewById<ImageView>(R.id.imageView)

        val darkenButton = findViewById<Button>(R.id.darken_button)

        darkenButton.setOnClickListener {
            var redint : Int
            var blueint= Int
            var greenint= Int

            var green = findViewById<EditText>(R.id.greennessValue).text.toString().toInt()
            var blue = findViewById<EditText>(R.id.bluenessValue).text.toString().toInt()
            var red = findViewById<EditText>(R.id.rednessValue).text.toString().toInt()



            FilterPicture(red, green, blue)




        }

        photo.setOnClickListener {
            val takePictureIntent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            photoFile=getPhotoFile(FILE_NAME)
           val fileProvider= FileProvider.getUriForFile(
               this,
               "com.example.uppgift5.fileprovider",
               photoFile
           )
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
            if(takePictureIntent.resolveActivity(this.packageManager)!=null){
                startActivityForResult(takePictureIntent, REQUEST_CODE)
            }
            else{
                println("unable open la camera")
            }
        }
}

    private fun FilterPicture(red: Int, green: Int, blue: Int) {
        imageView.clearColorFilter()
        val hex = java.lang.String.format("#%02x%02x%02x", red, green, blue)
        val color: Int = Color.parseColor(hex)
        val imageView =  findViewById<ImageView>(R.id.imageView)
        imageView.clearColorFilter()
        imageView.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        println(imageView.drawable.toBitmap().getPixel(20,20))






    }

    private fun getPhotoFile(fileName: String): File {
        val storageDirectory= getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDirectory)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            takenIMage = BitmapFactory.decodeFile(photoFile.absolutePath)

            imageView.setImageBitmap(takenIMage)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}






