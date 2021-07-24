package com.example.VideoStreamer

class CustomItem {
    var image: String = ""
    var title: String = ""
    var videoUrl: String = ""

    constructor(image: String, title: String, videoUrl: String) : this() {
        this.image = image
        this.title = title
        this.videoUrl = videoUrl

    }

    constructor()
}