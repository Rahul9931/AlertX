package com.rahulsaini.alertx.topDialog.utils

import com.rahulsaini.alertx.topDialog.message.TopAlertMessage
import java.util.concurrent.locks.ReentrantLock
import kotlin.collections.removeFirstOrNull
import kotlin.concurrent.withLock

object QueueManager {
    val alertQueue: ArrayDeque<TopAlertMessage> = ArrayDeque()
    var isShowing: Boolean = false
    private var lock = ReentrantLock()

    fun enqueue(alert: TopAlertMessage){
        lock.withLock {
            alertQueue.add(alert)
            if (!isShowing){
                nextShow()
            }
        }
    }

    private fun nextShow() {
        lock.withLock {
            val next = alertQueue.removeFirstOrNull() ?: return
            isShowing = true
            next.showInterval {
                isShowing = false
                nextShow()
            }
        }
    }
}