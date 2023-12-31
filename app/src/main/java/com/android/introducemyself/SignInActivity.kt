package com.android.introducemyself

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class SignInActivity : AppCompatActivity() {
    //ActivityResultLauncher 자료형인 resultLauncher변수를 전역변수로 선언.
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private lateinit var myId: EditText
    private lateinit var myPassword: EditText
    private lateinit var signup: ImageView
    private lateinit var login: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        myId = findViewById(R.id.et_loginId)
        myPassword = findViewById(R.id.et_loginPassword)
        signup = findViewById(R.id.img_signup)
        login = findViewById(R.id.img_login)

        //회원가입할때 적은 아이디 비밀번호 가져오는 함수
        setResult()

        login.setOnClickListener {
            val loginIntent = Intent(this, HomeActivity::class.java)
            val myIds = myId.text.toString()
            val myPasswords = myPassword.text.toString()
            if(myIds.isNotEmpty() && myPasswords.isNotEmpty()){
                for(value in User.myIdList){
                    if(value.myEmail == myIds && value.myPassword == myPasswords){
                        message(R.string.sign_in_login_success)
                        loginIntent.putExtra("UserData", value)
                        startActivity(loginIntent)
                        //intent의 task관리 공부.
                        //string을 넘기지 말고 user클래스를 만들어서 객체를 넘기기
                    }else{
                        message(R.string.sign_in_noData)
                    }
                }
            } else if(myIds.isNotEmpty() && myPasswords.isEmpty()){
                message(R.string.sign_in_put_password)
            } else if(myIds.isEmpty() && myPasswords.isNotEmpty()){
                message(R.string.sign_in_put_email)
            } else{
                message(R.string.sign_in_login_fail)
            }
        }
        signup.setOnClickListener {
            val signUpIntent = Intent(this, SignUpActivity::class.java)
//            startActivity(signUpIntent)
            //signupActivity에서 회원가입한 아이디와 비밀번호를 가져오기 위해서 startActivity대신 resultLauncher사용.
            resultLauncher.launch(signUpIntent)
        }
    }

    //회원가입한 내용 가져오기
    private fun setResult(){
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val id = result.data?.getStringExtra("id") ?: ""
                val password = result.data?.getStringExtra("password") ?: ""
                myId.setText(id)
                myPassword.setText(password)
            }
        }
    }

    //Toast메세지 많이 쓰니깐 함수로.
    private fun message(value:Int){
        Toast.makeText(this, getString(value), Toast.LENGTH_SHORT).show()
    }

}