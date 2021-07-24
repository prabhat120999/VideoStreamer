package com.example.VideoStreamer

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Movies.newInstance] factory method to
 * create an instance of this fragment.
 */
class Movies : Fragment() {
    var databaseReference: DatabaseReference?=null;
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var layout = inflater.inflate(R.layout.fragment_movies, container, false)
        val connectivityManager =
            layout.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

        var recyclerView = layout.findViewById<RecyclerView>(R.id.movies_recycler_view)
        var progressBar = layout.findViewById<ProgressBar>(R.id.movieProgressBar)
        var coming_soon = layout.findViewById<ImageView>(R.id.comingsoon_movies)
        if(!isConnected) {
            Toast.makeText(context,"You are not connected to the internet", Toast.LENGTH_SHORT).show()
            progressBar.visibility=View.GONE
        }
        databaseReference = FirebaseDatabase.getInstance().reference.child("movies")
        if(!isConnected) {
            Toast.makeText(layout.context, "You are not connected to the internet.", Toast.LENGTH_LONG).show()
            progressBar.visibility=View.GONE
        }
        databaseReference?.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
                progressBar.visibility = View.GONE
                Picasso.get()
                    .load("https://firebasestorage.googleapis.com/v0/b/kuchbhi-3dcaf.appspot.com/o/c0ca3aaa6472b43cbfa394b71919b8e6.jpg?alt=media&token=d6d7365a-40a1-4027-b697-37b9e79593a5")
                    .into(coming_soon)
                coming_soon.visibility = View.VISIBLE


            }

            override fun onDataChange(p0: DataSnapshot) {
                Toast.makeText(context,p0.exists().toString(),Toast.LENGTH_SHORT).show()

                if (p0.exists()) {
                    var items = ArrayList<CustomItem>()
                    for (x: DataSnapshot in p0.children) {

                        var image = x.child("image").value.toString()
                        var video = x.child("video").value.toString()
                        var title = x.child("title").value.toString()
                        items.add(CustomItem(image, title, video))

                    }

                    if (items.size != 0) {
                        var adapter = MyAdapter(layout.context, items)
                        recyclerView.layoutManager = GridLayoutManager(context,2)
                        recyclerView.adapter = adapter;
                    } else {
                        Picasso.get()
                            .load("https://firebasestorage.googleapis.com/v0/b/kuchbhi-3dcaf.appspot.com/o/c0ca3aaa6472b43cbfa394b71919b8e6.jpg?alt=media&token=d6d7365a-40a1-4027-b697-37b9e79593a5")
                            .into(coming_soon)
                        coming_soon.visibility = View.VISIBLE
                    }
                    progressBar.visibility = View.GONE

                } else {
                    Toast.makeText(layout.context, "Something went wrong", Toast.LENGTH_SHORT).show()
                    progressBar.visibility = View.GONE

                }
            }
        })

        return layout
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Movies.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Movies().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}