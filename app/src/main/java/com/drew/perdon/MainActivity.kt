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
import android.widget.Toast
import com.github.nisrulz.sensey.Sensey
import com.github.nisrulz.sensey.ShakeDetector
import kotlin.collections.HashMap

private const val TAG = "MainActivity"
private val soundPlayer = SoundPool.Builder().setMaxStreams(1).build()
private val soundsDictionary = HashMap<String, Int>()

val shakeListener = object : ShakeDetector.ShakeListener {
    override fun onShakeDetected() {
        Log.d(TAG, "Phone got shaken")
        soundPlayer.play(soundsDictionary["boom"]!!, 1f, 1f, 1, 0, 1f)
    }
    override fun onShakeStopped() {
        Log.d(TAG, "Shake stopped")
    }
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeSounds()
        Sensey.getInstance().init(this)
        Sensey.getInstance().startShakeDetection(20f, 300, shakeListener)
    }

    override fun onDestroy() {
        Sensey.getInstance().stop()
        super.onDestroy()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == ACTION_DOWN) {
            Log.d(TAG, "Screen got touched")
            slapTable()
            playSound("perdon")
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
        soundsDictionary["boom"] = soundPlayer.load(this, R.raw.boom, 1)
        Log.d(TAG, "Sounds initialized")
    }

    private fun playSound(soundName: String) {
        soundPlayer.play(
            soundsDictionary[soundName]!!,
            1f,
            1f,
            1,
            0,
            1f
        )
        Log.d(TAG, "Played $soundName sound")
    }

    private fun slapTable() {
        findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.hand_on_table)
    }

    private fun unSlapTable() {
        findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.hand_in_air)
    }
}