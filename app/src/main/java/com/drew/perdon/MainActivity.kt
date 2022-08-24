package com.drew.perdon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == ACTION_DOWN) {
        Toast.makeText(this, "I got touched!",Toast.LENGTH_SHORT).show()
        }
        return super.onTouchEvent(event)
    }
}