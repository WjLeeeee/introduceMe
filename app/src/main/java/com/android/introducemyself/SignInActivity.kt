package com.android.introducemyself

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

data class ListType(
    val myId:String,
    val myName:String,
    val myPassword:String
)
class SignInActivity : AppCompatActivity() {
    companion object{
        val myIdList = mutableListOf<ListType>()
    }

    //ActivityResultLauncher 자료형인 resultLauncher변수를 전역변수로 선언.
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private lateinit var myId: EditText
    private lateinit var myPassword: EditText
    private lateinit var signup: ImageView
    private lateinit var login: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

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
                for(value in myIdList){
                    if(value.myId == myIds && value.myPassword == myPasswords){
                        Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                        loginIntent.putExtra("IdData", value.myId)
                        loginIntent.putExtra("nameData", value.myName)
                        startActivity(loginIntent)
                    }else{
                        Toast.makeText(this, "등록된 정보가 없습니다. 회원가입을 먼저 진행해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
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

}