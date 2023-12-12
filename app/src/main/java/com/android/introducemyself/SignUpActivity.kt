package com.android.introducemyself

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val signBtn = findViewById<Button>(R.id.btn_signup)
        val editName = findViewById<EditText>(R.id.et_name)
        val editId = findViewById<EditText>(R.id.et_id)
        val editPassword = findViewById<EditText>(R.id.et_password)


        signBtn.setOnClickListener {
            if(editName.text.isNotEmpty() && editId.text.isNotEmpty() && editPassword.text.isNotEmpty()){
                val myListType = ListType(editId.text.toString(), editName.text.toString(), editPassword.text.toString())
                SignInActivity.myIdList.add(myListType)
                Toast.makeText(this, "회원가입이 완료되었습니다..", Toast.LENGTH_SHORT).show()
                // 회원가입된 아이디 값을 signinActivity로 보내기
                val intent = Intent(this, SignInActivity::class.java)
                intent.putExtra("id", editId.text.toString())
                intent.putExtra("password", editPassword.text.toString())
                setResult(RESULT_OK, intent)
                finish()
            }else{
                Toast.makeText(this, "입력되지 않은 정보가 있습니다.", Toast.LENGTH_SHORT).show()
            }
        }

    }
}