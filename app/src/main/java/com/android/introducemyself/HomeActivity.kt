package com.android.introducemyself

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlin.random.Random

class HomeActivity : AppCompatActivity() {

    private val calledId :TextView by lazy {findViewById(R.id.call_id)}
    private val calledName :TextView by lazy {findViewById(R.id.call_name)}
    private val finishBtn :TextView by lazy {findViewById(R.id.btn_finish)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val userData = intent.getSerializableExtra("UserData") as? User.ListType
        if(userData != null){
            var id = userData.myEmail
            var name = userData.myName
            calledId.setText(id)
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
