package com.newstone.vaccine_newspaper.view.main.video

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.material.button.MaterialButton
import com.newstone.vaccine_newspaper.R


class PlaybackActivity: ComponentActivity(), Player.Listener {
    companion object{
        val VIDEO_URL = "VIDEO_URL"
        val VIDEO_TITLE = "VIDEO_TITLE"
    }

    private lateinit var player: SimpleExoPlayer
    private lateinit var playerView: PlayerView
    private lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playback)

        url = intent.getStringExtra(VIDEO_URL) ?: "https://www.youtube.com/watch?v=L8Xq15NTuCc"
        val title = intent.getStringExtra(VIDEO_TITLE) ?: "Unknown"
        val videoTitleTextView = findViewById<TextView>(R.id.videoTitleTextView)
        videoTitleTextView.text = title

        val backBtn = findViewById<Button>(R.id.videoBackBtn)
        backBtn.setOnClickListener{
            finish()
        }

        playerView = findViewById(R.id.playerView)
        initExoPlayer()
    }

    override fun onStart() {
        super.onStart()
        player.play()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onPlayerError(error: ExoPlaybackException) {
        super.onPlayerError(error)
        initExoPlayer()
        player.play()
    }

    fun initExoPlayer() {
        val trackSelector = DefaultTrackSelector(this)
        trackSelector.setParameters(
            trackSelector.buildUponParameters().setMaxVideoSizeSd()
        )
        player = SimpleExoPlayer.Builder(this)
                .setTrackSelector(trackSelector)
                .build()
        player.addListener(this)
        playerView.player = player

        val mediaItem =
            MediaItem.Builder()
                .setUri(url)
                .build()

        player.setMediaItem(mediaItem)
        player.prepare()
    }

    fun releasePlayer() {
        player.removeListener(this)
        player.release()
    }
}