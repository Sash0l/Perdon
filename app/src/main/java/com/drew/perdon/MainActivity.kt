package com.drew.perdon

import android.content.res.Configuration
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_UP
import android.widget.ImageView
import kotlin.collections.HashMap

private const val TAG = "MainActivity"
private val soundPlayer = SoundPool.Builder().setMaxStreams(1).build()
private val soundsDictionary = HashMap<String, Int>()

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeSounds()
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == ACTION_DOWN) {
            Log.d(TAG, "Screen got touched")
            slapTable()
            playSound()
        }
        if (event?.action == ACTION_UP) {
            unSlapTable()
        }
        return super.onTouchEvent(event)
    }

    private fun initializeSounds() {
        val uiMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        Log.d(TAG, "UI theme: $uiMode")
        when (uiMode) {
            Configuration.UI_MODE_NIGHT_YES ->
                soundsDictionary["perdon"] = soundPlayer.load(this, R.raw.perdon_negro, 1)
            else ->
                soundsDictionary["perdon"] = soundPlayer.load(this, R.raw.perdon_blanco, 1)
        }
        Log.d(TAG, "Sounds initialized")
    }

    private fun playSound() {
        soundPlayer.play(
            soundsDictionary["perdon"]!!,
            1f,
            1f,
            1,
            0,
            1f
        )
        Log.d(TAG, "Played perdon sound")
    }

    private fun slapTable() {
        findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.hand_on_table)
    }

    private fun unSlapTable() {
        findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.hand_in_air)
    }
}