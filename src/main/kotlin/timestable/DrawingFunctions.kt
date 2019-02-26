package timestable

import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Paint


fun GraphicsContext.strokeCircle(x: Double, y: Double, r: Double) {
    val R = 2 * r
    strokeOval(x - r, y - r, R, R)
}

fun GraphicsContext.fillCircle(x: Double, y: Double, r: Double) {
    fillOval(x, y, r, r)
}

fun GraphicsContext.clear(color: Paint) {
    val b = fill
    fill = color
    fillRect(0.0, 0.0, canvas.width, canvas.height)
    fill = b
}

fun GraphicsContext.clear() {
    clearRect(0.0, 0.0, canvas.width, canvas.height)
}

var GraphicsContext.color: Paint?
    get() = if(fill != stroke) null else fill
    set(value) {
        fill = value
        stroke = value
    }