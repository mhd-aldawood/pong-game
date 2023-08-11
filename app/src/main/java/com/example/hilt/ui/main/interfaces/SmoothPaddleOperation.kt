package com.example.hilt.ui.main.interfaces

interface SmoothPaddleOperation {
    fun update(height:Int)
    fun changeMovementState(direction: String, height: Int)
}