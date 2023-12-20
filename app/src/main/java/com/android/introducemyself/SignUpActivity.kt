package com.android.introducemyself

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener

class SignUpActivity : AppCompatActivity() {

    private val etName: EditText by lazy { findViewById(R.id.et_name) }
    private val tvNameError: TextView by lazy { findViewById(R.id.tv_name_error) }
    private val etEmail: EditText by lazy { findViewById(R.id.et_email) }
    private val etEmailProvider: EditText by lazy { findViewById(R.id.et_provider) }
    private val serviceProvider: Spinner by lazy { findViewById(R.id.service_provider) }
    private val tvEmailError: TextView by lazy { findViewById(R.id.tv_email_error) }
    private val etPassword: EditText by lazy { findViewById(R.id.et_password) }
    private val tvPasswordError: TextView by lazy { findViewById(R.id.tv_password_error) }
    private val etPasswordConfirm: EditText by lazy { findViewById(R.id.et_password_confirm) }
    private val tvPasswordConfirmError: TextView by lazy { findViewById(R.id.tv_password_confirm_error) }
    private val btConfirm: Button by lazy { findViewById(R.id.bt_confirm) }


    private val editTexts
        get() = listOf(
            etName,
            etEmail,
            etEmailProvider,
            etPassword,
            etPasswordConfirm
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        initView()

        btConfirm.setOnClickListener {
            val finalEmail = etEmail.text.toString() + "@" + etEmailProvider.text.toString()
            val myType = User.ListType(
                finalEmail,
                etName.text.toString(),
                etPassword.text.toString()
            )
            User.myIdList.add(myType)
            Toast.makeText(this, "회원가입이 완료되었습니다..", Toast.LENGTH_SHORT).show()
            // 회원가입된 아이디 값을 signinActivity로 보내기
            val intent = Intent(this, SignInActivity::class.java)
            intent.putExtra("id", finalEmail)
            intent.putExtra("password", etPassword.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun initView() {

        setTextChangedListener()

        // focus out 처리
        setOnFocusChangedListener()

        // 이메일 서비스 제공자 처리
        setServiceProvider()
    }

    /**
     * spinner활용
     */
    private fun setServiceProvider() {

        val emailArray = arrayOf(
            getString(R.string.sign_up_email_provider_gmail),
            getString(R.string.sign_up_email_provider_kakao),
            getString(R.string.sign_up_email_provider_naver),
            getString(R.string.sign_up_email_provider_direct)
        )

        serviceProvider.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item, emailArray
        )

        serviceProvider.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) = Unit

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val isVisibleProvider = position == serviceProvider.adapter.count - 1
                if(isVisibleProvider) {
                    etEmailProvider.isVisible = isVisibleProvider
                    etEmailProvider.setText("")
                }
                else{
                    etEmailProvider.isVisible = isVisibleProvider
                    etEmailProvider.setText(emailArray[position])
                }
            }
        }
    }


    private fun setTextChangedListener() {
        editTexts.forEach { editText ->
            editText.addTextChangedListener {
                editText.setErrorMessage()
                setConfirmButtonEnable()
            }
        }
    }

    private fun setOnFocusChangedListener() {
        editTexts.forEach { editText ->
            editText.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus.not()) {
                    editText.setErrorMessage()
                    setConfirmButtonEnable()
                }
            }
        }
    }

    private fun EditText.setErrorMessage() {
        when (this) {
            etName -> tvNameError.text = getMessageValidName()
            etEmail -> tvEmailError.text = getMessageValidEmail()
            etEmailProvider -> tvEmailError.text = getMessageValidEmailProvider()
            etPassword -> {
                tvPasswordError.setTextColor(
                    if (etPassword.text.toString().isBlank()) {
                        ContextCompat.getColor(this@SignUpActivity, android.R.color.darker_gray)
                    } else {
                        ContextCompat.getColor(this@SignUpActivity, android.R.color.holo_red_dark)
                    }
                )
                tvPasswordError.text = if (etPassword.text.toString().isBlank()) {
                    getString(R.string.sign_up_password_hint)
                } else {
                    getMessageValidPassword()
                }
            }

            etPasswordConfirm -> tvPasswordConfirmError.text = getMessageValidPasswordConfirm()

            else -> Unit
        }
    }

    private fun getMessageValidName(): String = if (etName.text.toString().isBlank()) {
        getString(R.string.sign_up_name_error)
    } else {
        ""
    }

    private fun getMessageValidEmail(): String {
        val text = etEmail.text.toString()
        return when {
            text.isBlank() -> getString(R.string.sign_up_email_error_blank)
            text.contains("@") -> getString(R.string.sign_up_email_error_at)
            else -> ""
        }
    }
    private fun getValidProvider():String{
        val text = etEmailProvider.text.toString()
        return when{
            text.isBlank() -> getString(R.string.sign_up_email_error_provider)
            text.contains("@") -> getString(R.string.sign_up_email_error_at)
            else -> ""
        }
    }

    private fun getMessageValidEmailProvider(): String {
        val providerRex = Regex("[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        val text = etEmailProvider.text.toString()
        return if (
            etEmailProvider.isVisible
            && (etEmailProvider.text.toString().isBlank()
                    || providerRex.matches(text).not())
        ) {
            getValidProvider()
        } else {
            getMessageValidEmail()
        }
    }

    private fun getMessageValidPassword(): String {
        val text = etPassword.text.toString()
        val specialCharacterRegex = Regex("[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]+")
        val upperCaseRegex = Regex("[A-Z]")
        return when {
            text.length < 10 -> getString(R.string.sign_up_password_error_length)
            specialCharacterRegex.containsMatchIn(text)
                .not() -> getString(R.string.sign_up_password_error_special)

            upperCaseRegex.containsMatchIn(text)
                .not() -> getString(R.string.sign_up_password_error_upper)

            else -> ""
        }
    }

    private fun getMessageValidPasswordConfirm(): String =
        if (etPassword.text.toString() != etPasswordConfirm.text.toString()) {
            getString(R.string.sign_up_confirm_error)
        } else {
            ""
        }

    private fun setConfirmButtonEnable() {
        btConfirm.isEnabled = getMessageValidName().isBlank()
                && getMessageValidEmail().isBlank()
                && getMessageValidEmailProvider().isBlank()
                && getMessageValidPassword().isBlank()
                && getMessageValidPasswordConfirm().isBlank()
    }
}