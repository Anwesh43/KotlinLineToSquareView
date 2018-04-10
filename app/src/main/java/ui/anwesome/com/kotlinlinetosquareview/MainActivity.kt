package ui.anwesome.com.kotlinlinetosquareview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.linetosquareview.LineToSquareView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LineToSquareView.create(this)
    }
}
