package com.example.hilt.data.model

import java.io.Serializable

data class Settings(
    var pvp: Mode = Mode.ONE_PLAYER,
    var difficulty: Difficulty = Difficulty.EASY
) : Serializable

enum class Mode {
    ONE_PLAYER,
    TWO_PLAYERS
}

enum class Difficulty {
    EASY,
    MEDIUM,
    HARD
}