package com.example.VideoStreamer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_user.*


class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        val user = FirebaseAuth.getInstance().currentUser
        val userid = user!!.uid

        textView2.text="You are ${user}"


    }
}