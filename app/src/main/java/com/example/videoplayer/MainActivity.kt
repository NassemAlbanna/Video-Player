package com.example.videoplayer

import android.os.Bundle
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView

class MainActivity : ComponentActivity() {

    // Declaring the ExoPlayer and PlayerView variables to be initialized later
    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView

    // The onCreate method is called when the activity is first created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("lifecycle", "onCreate Called") // Logging the lifecycle event
        setContentView(R.layout.activity_main) // Setting the content view to activity_main layout

        playerView = findViewById(R.id.playerView) // Finding the PlayerView by its ID

        // Finding the RecyclerView by its ID and setting its layout manager to a GridLayoutManager with 2 columns
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        // Creating a list of GridItem objects with video titles and URIs
        val items = listOf(
            GridItem("Testing The Durability Of A One Dollar Paper Bill", getVideoUri(R.raw.sample1)),
            GridItem("Woman Looking Through A Microscope", getVideoUri(R.raw.sample2)),
            GridItem("A Doctor Doing A Test Using A Microscope", getVideoUri(R.raw.sample3)),
            GridItem("Person In a Hazmat Suit Checking a Driver's Temperature", getVideoUri(R.raw.sample4)),
            GridItem("Video Of People Walking", getVideoUri(R.raw.sample5)),
            GridItem("Person Searching Using His Smartphone", getVideoUri(R.raw.sample6)),
            GridItem("Person Having DNA Test", getVideoUri(R.raw.sample7)),
            GridItem("A shot from a film", getVideoUri(R.raw.sample8))
        )

        // Creating and setting an adapter for the RecyclerView
        // When an item is clicked, play the corresponding video and show a toast message
        val adapter = GridAdapter(this, items) { item ->
            playVideo(item.videoUri)
            Toast.makeText(this, "Clicked: ${item.title}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = adapter

        // Initializing the ExoPlayer
        initializePlayer()
    }

    // Helper function to get a video URI from a raw resource ID
    private fun getVideoUri(rawId: Int): Uri {
        return Uri.parse("android.resource://${packageName}/${rawId}")
    }

    // Function to play a video given its URI
    private fun playVideo(videoUri: Uri) {
        val mediaItem = MediaItem.fromUri(videoUri)
        player.setMediaItem(mediaItem) // Setting the media item to the player
        player.prepare() // Preparing the player
        player.playWhenReady = true // Starting playback when ready
    }

    // Function to initialize the ExoPlayer and set it to the PlayerView
    private fun initializePlayer() {
        player = ExoPlayer.Builder(this).build()
        playerView.player = player
    }

    // The onStart method is called when the activity becomes visible to the user
    override fun onStart() {
        super.onStart()
        if (::player.isInitialized) { // Checking if the player is initialized
            player.playWhenReady = true // Starting playback
            Log.i("lifecycle", "onStart Called") // Logging the lifecycle event
        }
    }

    // The onResume method is called when the activity starts interacting with the user
    override fun onResume() {
        super.onResume()
        if (::player.isInitialized) { // Checking if the player is initialized
            player.playWhenReady = true // Starting playback
            Log.i("lifecycle", "onResume Called") // Logging the lifecycle event
        }
    }

    // The onPause method is called when the activity is partially obscured by another activity
    override fun onPause() {
        super.onPause()
        if (::player.isInitialized) { // Checking if the player is initialized
            player.playWhenReady = false // Pausing playback
            Log.i("lifecycle", "onPause Called") // Logging the lifecycle event
        }
    }

    // The onStop method is called when the activity is no longer visible to the user
    override fun onStop() {
        super.onStop()
        if (::player.isInitialized) { // Checking if the player is initialized
            player.playWhenReady = false // Pausing playback
            Log.i("lifecycle", "onStop Called") // Logging the lifecycle event
        }
    }

    // The onDestroy method is called when the activity is being destroyed
    override fun onDestroy() {
        super.onDestroy() // Call the superclass method
        if (::player.isInitialized) { // Check if the player has been initialized
            player.release() // Release the player to free up resources
        }
    }
}
