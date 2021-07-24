package com.example.VideoStreamer

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.FrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    var firebaseauth: FirebaseAuth? = null
    var firebaseuser: FirebaseUser? = null
    var firebasedatabase: FirebaseDatabase? = null
    var databasereference: DatabaseReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseauth = FirebaseAuth.getInstance()
        var currentUser: FirebaseUser?
        currentUser = firebaseauth!!.currentUser
        if (currentUser == null) {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        } else {
            val connectivityManager =
                applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
            if (!isConnected) {
                var dialog = Dialog(this)
                dialog.setContentView(R.layout.nointernet_dialog)
                dialog.show()
                dialog.setCancelable(false)
                dialog.setCanceledOnTouchOutside(false)

                var exit = dialog.findViewById<Button>(R.id.exit)
                var try_again = dialog.findViewById<Button>(R.id.try_again)
                exit.setOnClickListener {
                    finish()
                }
                try_again.setOnClickListener {
                    finish()
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                }
            } else {
                if (true) {
                    var nav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
                    var frameLayout = findViewById<FrameLayout>(R.id.framelayout)
                    var songs = Songs()
                    var movies = Movies()
                    var webseries = web_series()
                    var fragmentchanger = supportFragmentManager.beginTransaction()
                    fragmentchanger.replace(R.id.framelayout, songs)
                    fragmentchanger.commit()
                    nav.setOnNavigationItemSelectedListener { item ->
                        when (item.itemId) {
                            R.id.song -> {
                                var fragmentchanger = supportFragmentManager.beginTransaction()
                                fragmentchanger.replace(R.id.framelayout, songs)
                                fragmentchanger.commit()
                                true
                            }
                            R.id.movie -> {
                                var fragmentchanger = supportFragmentManager.beginTransaction()
                                fragmentchanger.replace(R.id.framelayout, movies)
                                fragmentchanger.commit()
                                true

                            }
                            R.id.webseries -> {
                                var fragmentchanger = supportFragmentManager.beginTransaction()
                                fragmentchanger.replace(R.id.framelayout, webseries)
                                fragmentchanger.commit()
                                true

                            }
                            else -> {
                                true
                            }
                        }

                    }
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        if (item.itemId == R.id.user_option) {
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        }
        if (item.itemId == R.id.main_logout_option) {
            firebaseauth?.signOut()
            var intent: Intent = Intent(this@MainActivity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }

        return true

    }


}