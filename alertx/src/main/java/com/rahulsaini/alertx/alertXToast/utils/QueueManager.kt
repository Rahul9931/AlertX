package com.rahulsaini.alertx.alertXToast.utils

import com.rahulsaini.alertx.alertXToast.message.ToastAlertMessage
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

object QueueManager {
    val alertQueue: ArrayDeque<ToastAlertMessage> = ArrayDeque()
    var isShowing: Boolean = false
    private var lock = ReentrantLock()
    private var currentAlert: ToastAlertMessage? = null

    fun enqueue(alert: ToastAlertMessage){
        lock.withLock {
            alertQueue.add(alert)
            if (!isShowing){
                nextShow()
            }
        }
    }

    fun nextShow() {
        lock.withLock {
            val next = alertQueue.removeFirstOrNull() ?: return
            currentAlert = next
            isShowing = true
            next.showWithCallback {
                lock.withLock {
                    currentAlert = null
                    isShowing = false
                    nextShow()
                }
            }
        }
    }
}