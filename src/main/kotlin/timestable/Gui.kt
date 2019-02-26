package timestable

import javafx.animation.AnimationTimer
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.text.TextAlignment
import javafx.stage.Stage
import javafx.util.Duration
import timestable.Styles.Companion.primaryCanvas
import tornadofx.*
import java.lang.Math.*



fun Duration.toNanos(): Double {
    return toMillis() * 1000000
}

fun round(n: Double, precision: Int): Double {
    val t = Math.pow(10.0, precision.toDouble())
    return Math.round(n * t).toDouble() / t
}
const val π = PI

class MainScreen : View() {
    override val root = hbox {
        primaryStage.width = 800.0
        primaryStage.height = 600.0
        addClass(primaryCanvas)
        c = canvas {
            widthProperty().bind(primaryStage.widthProperty())
            heightProperty().bind(primaryStage.heightProperty())
        }
        setOnMouseClicked {
            primaryStage.isFullScreen = !primaryStage.isFullScreen
        }
    }

    private lateinit var c: Canvas



    init {
        TableDrawer(c).start()
    }
}


class TableDrawer(private val c: Canvas) : AnimationTimer() {

    var animDelay = 10.millis.toNanos()
    var prevTime = 0L
    var pointCount = 500
    var factor = 0.0
    var speed = 0.01
    var colorFactor = 10
    override fun handle(now: Long) {
        if (now > prevTime + animDelay) {
            prevTime = now
            factor += speed
            with(c.graphicsContext2D) {
                save()
                clear()

                val ox = c.width / 2
                val oy = c.height / 2
                val r = c.height * 0.5 * 0.75

                color = c("#99AAB5")

                fillText("n = $pointCount", ox, oy - (r * 1.1))
                fillText("k = ${round(factor, 3)}", ox, oy + (r * 1.1))

                translate(ox, oy)
                scale(r, r)

                color = Color.hsb((factor * 360 / colorFactor) % 360, 1.0, 1.0)
                lineWidth = 1 / r

                strokeCircle(0.0, 0.0, 1.0)
                for (i in 0 until pointCount) {
                    bindPoint(i)
                }

                restore()
            }
        }
    }

    init {
        c.graphicsContext2D.textAlign = TextAlignment.CENTER
    }

    private fun GraphicsContext.bindPoint(a: Int) {
        val θ1 = angleToPoint(a)
        val θ2 = angleToPoint((factor * a).toInt() % pointCount)
        strokeLine(cos(π - θ1), sin(π - θ1), cos(π - θ2), sin(π - θ2))
    }

    private fun angleToPoint(i: Int) = 2 * i * Math.PI / pointCount

}


class Styles : Stylesheet() {
    companion object {
        val primaryCanvas by cssclass()
    }

    init {
        primaryCanvas {
            backgroundColor = multi(c("#2C2F33"))
        }
    }

}

class TimesTable : App(MainScreen::class, Styles::class) {


    override fun start(stage: Stage) {
        super.start(stage)

    }

}