package xyz.nietongxue.mindkit.view

import javafx.scene.control.Hyperlink
import javafx.scene.control.Label
import javafx.scene.control.Labeled
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.box
import tornadofx.px
import tornadofx.style


fun Labeled.markerStyle(gray:Boolean = false){
    this.style{
        fontSize = 10.px
        fontWeight = FontWeight.EXTRA_BOLD
        textFill = Color.WHITE
        backgroundColor += Color.web("#3973ac",if(gray) 0.5 else 0.9)
        backgroundRadius += box(2.px)
        padding = box(0.px,2.px)
    }
}

//fun Label.markerStyle(gray:Boolean = false){
//    this.style{
//        fontSize = 10.px
//        fontWeight = FontWeight.EXTRA_BOLD
//        textFill = Color.WHITE
//        backgroundColor += Color.web("#3973ac",if(gray) 0.5 else 0.9)
//        backgroundRadius += box(2.px)
//        padding = box(0.px,2.px)
//    }
//}
fun Labeled.bigMarkerStyle(gray:Boolean = false){
    this.style{
        fontSize = 16.px
        fontWeight = FontWeight.EXTRA_BOLD
        textFill = Color.WHITE
        backgroundColor += Color.web("#3973ac",if(gray) 0.5 else 0.9)
        backgroundRadius += box(4.px)
        padding = box(0.px,4.px)
    }
}