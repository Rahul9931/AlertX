package com.rahulsaini.alertx.alertXSheet.utils

import com.rahulsaini.alertx.alertXSheet.message.SheetAlertMessage
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

object QueueManager {
    val alertQueue: ArrayDeque<SheetAlertMessage> = ArrayDeque()
    var isShowing: Boolean = false
    private var lock = ReentrantLock()
    private var currentAlert: SheetAlertMessage? = null

    fun enqueue(alert: SheetAlertMessage){
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