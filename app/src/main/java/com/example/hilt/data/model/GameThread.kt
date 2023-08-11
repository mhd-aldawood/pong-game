package com.example.hilt.data.model
import android.os.Build
import android.view.SurfaceHolder
import androidx.annotation.RequiresApi

class GameThread(
private val surfaceHolder: SurfaceHolder,
private val gameView: GameView
) : Thread() {

    private val targetFPS = 60
    var running: Boolean = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun run() {
        var startTime: Long
        val targetTime = (1000 / targetFPS).toLong()

        while (running) {
            startTime = System.nanoTime()

            surfaceHolder.lockCanvas()?.also {
                synchronized(surfaceHolder) {
                    gameView.update()
                    gameView.draw(it)
                }
                surfaceHolder.unlockCanvasAndPost(it)
            }

            try {
                sleep(targetTime - (System.nanoTime() - startTime) / 1000000)
            } catch (ignored: Exception) {
            }
        }
    }
}