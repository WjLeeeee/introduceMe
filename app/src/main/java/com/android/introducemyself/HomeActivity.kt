package com.android.introducemyself

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlin.random.Random

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val calledID = findViewById<TextView>(R.id.call_id)
        val calledName = findViewById<TextView>(R.id.call_name)
        val finishBtn = findViewById<Button>(R.id.btn_finish)
        
//        val idData = intent.getStringExtra("IdData")
//        val nameData = intent.getStringExtra("nameData")
        val userData = intent.getSerializableExtra("UserData") as? User.ListType
        if(userData != null){
            var id = userData.myEmail
            var name = userData.myName
            calledID.setText(id)
            calledName.setText(name)
        }

        val imageView: ImageView = findViewById(R.id.myImage)
        val imageArray = arrayOf(
            R.drawable.myimage,
            R.drawable.myimage2,
            R.drawable.myimage3,
            R.drawable.myimage4,
            R.drawable.myimage5,
        )
        val randomImage = imageArray[Random.nextInt(imageArray.size)]
        imageView.setImageResource(randomImage)

        finishBtn.setOnClickListener {
            finish()
        }

    }
}
