package xyz.nietongxue.mindkit.view

import javafx.scene.control.Label
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.box
import tornadofx.px
import tornadofx.style

fun Label.markerStyle(gray:Boolean = false):Unit{
    this.style{
        fontSize = 10.px
        fontWeight = FontWeight.EXTRA_BOLD
        textFill = Color.WHITE
        backgroundColor += Color.gray(if(gray) 0.8 else 0.5)
        backgroundRadius += box(2.px)
        padding = box(0.px,2.px)
    }
}
fun Label.bigMarkerStyle(gray:Boolean = false):Unit{
    this.style{
        fontSize = 16.px
        fontWeight = FontWeight.EXTRA_BOLD
        textFill = Color.WHITE
        backgroundColor += Color.gray(if(gray) 0.8 else 0.5)
        backgroundRadius += box(4.px)
        padding = box(0.px,4.px)
    }
}