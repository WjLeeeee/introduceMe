package com.android.introducemyself

import java.io.Serializable

class User {
    data class ListType(
        val myId:String,
        val myName:String,
        val myPassword:String
    ) : Serializable // 객체의 상태를 외부에 저장하거나 전송할 수 있도록 하는 인터페이스.
    companion object{
        val myIdList = mutableListOf<ListType>()
    }

}