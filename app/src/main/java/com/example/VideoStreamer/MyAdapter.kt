package com.example.VideoStreamer

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import tcking.github.com.giraffeplayer2.GiraffePlayer
import tcking.github.com.giraffeplayer2.VideoInfo

class MyAdapter(var context: Context, data: ArrayList<CustomItem>) :
    RecyclerView.Adapter<MyAdapter.VideoHolder>() {
    var data = ArrayList<CustomItem>()

    init {
        this.data = data
    }

    override fun onBindViewHolder(p0: VideoHolder, p1: Int) {
        Picasso.get().load(data[p1].image).into(p0.image)
        p0.videocard.setOnClickListener {

            val videoInfo = VideoInfo(data[p1].videoUrl)
                .setTitle(data[p1].title)
                .setAspectRatio(VideoInfo.AR_ASPECT_FILL_PARENT).setBgColor(Color.BLACK)
                .setShowTopBar(true)
                .setPortraitWhenFullScreen(false).setFullScreenOnly(true)
            GiraffePlayer.play(context, videoInfo)
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): VideoHolder {
        var view = LayoutInflater.from(p0.context).inflate(R.layout.custom_item, p0, false)
        return VideoHolder(view)
    }

    class VideoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById<ImageView>(R.id.item_image)
        var videocard = itemView.findViewById<CardView>(R.id.video)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}