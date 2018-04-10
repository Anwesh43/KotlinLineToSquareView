package ui.anwesome.com.linetosquareview

/**
 * Created by anweshmishra on 11/04/18.
 */

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View

class LineToSquareView (ctx : Context) : View(ctx) {

    val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}