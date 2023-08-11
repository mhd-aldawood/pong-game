package com.example.hilt.ui.main.interfaces

import android.graphics.Canvas
import com.example.hilt.data.model.Ball
import com.example.hilt.data.model.GameView

interface BallOperation :General {
    fun resetBall()
    fun setUpGameView(gameView: GameView)
    fun flipDirection(component: Ball.SpeedComponent)
    fun funnyBounce()
    fun checkWallBounce()
    fun move()
    fun kill()
    fun randomNegativity(): Int
    fun ballDirection():String
}