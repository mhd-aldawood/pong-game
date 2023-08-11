package com.example.hilt.data.model

class TwoPlayersGame(
    paddleA: PaddleAbstract,
    paddleB: PaddleAbstract,
    private val ball: Ball,
    difficulty: Difficulty
) : GameAbstract(paddleA, paddleB, ball, difficulty) {

    var pointsA: Int = 0
    var pointsB: Int = 0

    override fun referee(width: Int): Boolean {
       TODO() //implelemt two player
    }

    override fun bounce() {

    }

}