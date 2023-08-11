package com.example.hilt.data.model

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import androidx.core.content.ContextCompat
import com.example.hilt.R
import com.example.hilt.ui.main.interfaces.BallOperation
import kotlin.random.Random

class Ball(var initX: Float, private var initY: Float, difficulty: Difficulty):BallOperation {
    var ballX = initX
        private set
    var ballY = initY
        private set
    val size = 50f

    private var defdx = 15f
    private var defdy = 15f

    var dx = 15f
        private set
    private var dy = 15f

    private lateinit var gameView: GameView

    init {
        initX -= size / 2
        initY -= size / 2

        dx = when (difficulty) {
            Difficulty.EASY -> 15f
            Difficulty.MEDIUM -> 20f
            Difficulty.HARD -> 20f
        }
        dy = dx
        defdx = dx
        defdy = dy

        resetBall()
    }

    override fun draw(canvas: Canvas) {
        val paint = Paint()
        paint.color = ContextCompat.getColor(gameView.context, R.color.colorAccent)

        canvas.drawOval(RectF(ballX, ballY, ballX + size, ballY + size), paint)
    }

    override fun resetBall() {
        ballX = initX
        ballY = initY
        dx = (defdx + defdx * Random.nextFloat()) * randomNegativity()
        dy = (defdy + defdy * Random.nextFloat()) * randomNegativity()
        flipDirection(SpeedComponent.X)
    }

    override fun setUpGameView(gameView: GameView) {
        this.gameView = gameView
    }

    override  fun flipDirection(component: SpeedComponent) {
        when (component) {
            SpeedComponent.X -> dx *= -1
            SpeedComponent.Y -> dy *= -1
        }
    }

    override  fun funnyBounce() {
        dy *= Random.nextDouble(1.0, 1.05).toFloat()
        dx *= Random.nextDouble(1.0, 1.05).toFloat()
    }
    override fun checkWallBounce() {
        if (ballY <= 0f || ballY + size >= gameView.height.toFloat()) {
            playWallBounceSound()
            flipDirection(SpeedComponent.Y)
            funnyBounce()
        }
    }

    override fun playWallBounceSound() {
        gameView.playSound(R.raw.hit)
    }

    override fun playPaddleBounceSound() {
        gameView.playSound(R.raw.hit2)
    }

    override fun move() {
        ballX += dx
        ballY += dy
        checkWallBounce()
    }

    override fun kill() {
        dx = 0f
        dy = 0f
        ballX = initX
        ballY = initY
    }

    override fun randomNegativity(): Int {
        return Math.pow((-1).toDouble(), Random.nextInt(2).toDouble()).toInt()
    }

    enum class SpeedComponent {
        X,
        Y
    }
}