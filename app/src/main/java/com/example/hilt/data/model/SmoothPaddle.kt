package com.example.hilt.data.model

import android.util.Log
import android.view.MotionEvent
import com.example.hilt.ui.main.interfaces.SmoothPaddleOperation

class SmoothPaddle(side: Side, x: Float, y: Float, difficulty: Difficulty) : PaddleAbstract(side, x, y, difficulty) ,SmoothPaddleOperation{
    private var movementState: MovementState = MovementState.MOVING_NOT
    private val speed = 30f

    override fun movePaddle(event: MotionEvent, index: Int, height: Int) {
        movementState = when {
            event.getY(index) > height / 2f -> {
                MovementState.MOVING_UP
            }
            else -> {
                MovementState.MOVING_DOWN
            }
        }

        when (event.actionMasked) {
            MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_UP -> {
                movementState = MovementState.MOVING_NOT
            }
        }
    }


    override fun update(height: Int) {
        when (movementState) {
            MovementState.MOVING_DOWN -> {
                if (paddleY >= 0)
                    paddleY -= speed
            }
            MovementState.MOVING_UP -> {
                if (paddleY <= height - this.height)
                    paddleY += speed
            }
            MovementState.MOVING_NOT -> {
            }
        }
        Log.i(TAG, "changeMovementState: "+paddleY)

    }
    private  val TAG = "SmoothPaddle"
    override fun changeMovementState(direction: String, height: Int) {
        movementState = when (direction){
            "up" -> {
                MovementState.MOVING_UP
            }
            else -> {
                MovementState.MOVING_DOWN
            }
        }
        update(height)
    }


}
public enum class MovementState {
    MOVING_UP,
    MOVING_DOWN,
    MOVING_NOT
}
