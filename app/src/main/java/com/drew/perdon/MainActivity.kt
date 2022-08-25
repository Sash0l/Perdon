package com.drew.perdon

import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
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
            playSound()
        }
        return super.onTouchEvent(event)
    }

    private fun initializeSounds() {
        soundsDictionary["perdonNegro"] = soundPlayer.load(this, R.raw.perdon_negro, 1)
        soundsDictionary["perdonBlanco"] = soundPlayer.load(this, R.raw.perdon_blanco, 1)
        Log.d(TAG,"Sounds initialized")
    }

    private fun playSound() {
        //if phone has dark theme enabled:
        soundPlayer.play(
            soundsDictionary["perdonNegro"]!!,
            1f,
            1f,
            1,
            0,
            1f
        )
        Log.d(TAG, "Played perdonNegro sound")
        //TODO: if phone has light theme enabled:
    }
}