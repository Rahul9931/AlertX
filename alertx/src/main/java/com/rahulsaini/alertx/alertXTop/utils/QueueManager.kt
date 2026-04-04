package com.rahulsaini.alertx.alertXTop.utils

import com.rahulsaini.alertx.alertXTop.message.TopAlertMessage
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

object QueueManager {
    val alertQueue: ArrayDeque<TopAlertMessage> = ArrayDeque()
    var isShowing: Boolean = false
    private var lock = ReentrantLock()
    private var currentAlert: TopAlertMessage? = null

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
            currentAlert = next
            isShowing = true
            next.showWithCallback(
                {
                    lock.withLock {
                        currentAlert = null
                        isShowing = false
                        nextShow()
                    }
            })
        }
    }

    fun dismissCurrentAlert(){
        lock.withLock {
            currentAlert?.forceDismiss()
        }
    }
}