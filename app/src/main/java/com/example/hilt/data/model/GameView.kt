package com.example.hilt.data.model


import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.media.MediaPlayer
import android.os.Build
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.annotation.RequiresApi
import com.example.hilt.R
import com.example.hilt.ui.main.view.GameActivity
import java.util.*
import kotlin.concurrent.schedule


class GameView(context: Context, attrs: AttributeSet) : SurfaceView(context, attrs), SurfaceHolder.Callback {

    private val thread: GameThread

    private lateinit var paddleA: PaddleAbstract
    private lateinit var paddleB: PaddleAbstract
    private lateinit var ball: Ball
    private lateinit var game: GameAbstract
    private var settings: Settings = (context as GameActivity).settings
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(settings.difficulty.toString(), Context.MODE_PRIVATE)

    init {
        holder.addCallback(this)
        thread = GameThread(holder, this)
    }



    fun update() {
        game.bounce()
        if (game.referee(width)) {
            if (game is OnePlayerGame) {
                sharedPreferences.edit().putInt("best_score", (game as OnePlayerGame).bestScore).apply()
            }
            ball.kill()
            Timer().schedule(500) {
                ball.resetBall()
            }
        }

        if (paddleA is SmoothPaddle ) {
            (paddleA as SmoothPaddle).update(height)

        }
        if(paddleB is SmoothPaddle)
        (paddleB as SmoothPaddle).changeMovementState(ball.ballDirection(),height)
        ball.move()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas?.also {
            it.drawColor(resources.getColor(R.color.colorPrimaryDark))
            paddleA.draw(it)
            paddleB.draw(it)
            updateScore(it)
            ball.draw(it)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        for (i in 0 until event.pointerCount) {
            if (event.getX(i) < width / 2) {
                paddleA.movePaddle(event, i, height)
            }
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateScore(canvas: Canvas?) {
        canvas?.also { it ->
            val textPaint = TextPaint()
            textPaint.color = Color.GRAY
            textPaint.textSize = 500f
            textPaint.textAlign = Paint.Align.CENTER
            textPaint.typeface = resources.getFont(R.font.bungee)
            val xPos = canvas.width / 2f
            val yPos = (canvas.height / 2f - (textPaint.descent() + textPaint.ascent()) / 2f)

            if (game is OnePlayerGame) {
                it.drawText("${(game as OnePlayerGame).points}", xPos, yPos, textPaint)
                textPaint.textSize = 100f
                textPaint.typeface = resources.getFont(R.font.faster_one)
                it.drawText("BEST: ${(game as OnePlayerGame).bestScore}", xPos, 100f, textPaint)
            }
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        // Set up the paddles.
        paddleA =  SmoothPaddle(Side.A, 0f, height / 2f, settings.difficulty)

        paddleB = SmoothPaddle(Side.B, width.toFloat(), height / 2f, settings.difficulty)


        // Set up the ball.
        ball = Ball(width / 2f, height / 2f, settings.difficulty)

        ball.setUpGameView(this)

        // Set up the game.
                game = OnePlayerGame(
                    paddleA,
                    paddleB,
                    ball,
                    settings.difficulty,
                    sharedPreferences.getInt("best_score", 0)
                )

        thread.running = true
        thread.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        paddleB.paddleX = width.toFloat()
        ball.initX = width / 2f - ball.size / 2
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        thread.running = false
        thread.join()
    }
}