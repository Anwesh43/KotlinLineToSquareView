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

    data class State (var prevScale : Float = 0f, var dir : Float = 0f, var j : Int = 0) {

        val scales : Array<Float> = arrayOf(0f, 0f, 0f)

        fun update (stopcb : (Float) -> Unit) {
            scales[j] += dir * 0.1f
            if (Math.abs(scales[j] - prevScale) > 1) {
                scales[j] = prevScale + dir
                j += dir.toInt()
                if (j == scales.size || j == -1) {
                    j -= dir.toInt()
                    dir = 0f
                    prevScale = scales[j]
                    stopcb(prevScale)
                }
            }
        }

        fun startUpdating (startcb : () -> Unit) {
            if (dir == 0f) {
                dir = 1 - 2 * prevScale
                startcb()
            }
        }
    }

    data class Animator (var view : View, var animated : Boolean = false) {
        fun animate (updatecb : () -> Unit) {
            if (animated) {
                updatecb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch (ex : Exception) {

                }
            }
        }
        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }
        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }

    data class LineToSquare(var i : Int, private val state : State = State()) {
        fun draw(canvas : Canvas, paint : Paint) {
            val w : Float = canvas.width.toFloat()
            val h : Float = canvas.height.toFloat()
            val size : Float = Math.min(w,h)/3
            paint.strokeWidth = Math.min(w, h)/50
            paint.strokeCap = Paint.Cap.ROUND
            paint.color = Color.parseColor("#009688")
            canvas.save()
            canvas.translate(w/2, h/2)
            for (i in 0..1) {
                canvas.save()
                canvas.translate(size * (1 - 2 * i)*this.state.scales[1], 0f)
                for (j in 0..1) {
                    canvas.save()
                    canvas.translate(0f, size * (1 - 2 * i))
                    canvas.rotate(-90f * this.state.scales[2])
                    val updated_size : Float = (size) * this.state.scales[0]
                    canvas.drawLine(0f, -updated_size, 0f, updated_size, paint)
                    canvas.restore()
                }
                canvas.restore()
            }
            canvas.restore()
        }

        fun update(stopcb : (Float) -> Unit) {
            state.update(stopcb)
        }
        fun startUpdating(startcb : () -> Unit) {
            state.startUpdating(startcb)
        }
    }

    data class Renderer(var view : LineToSquareView) {

        private val lineToSquare : LineToSquare = LineToSquare(0)

        private val animator : Animator = Animator(view)

        fun render(canvas : Canvas, paint : Paint) {
            canvas.drawColor(Color.parseColor("#212121"))
            lineToSquare.draw(canvas, paint)
            animator.animate {
                lineToSquare.update {
                    animator.stop()
                }
            }
        }

        fun handleTap() {
            lineToSquare.startUpdating {
                animator.start()
            }
        }
    }
}