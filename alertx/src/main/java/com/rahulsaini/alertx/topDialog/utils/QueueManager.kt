package com.rahulsaini.alertx.topDialog.utils

import com.rahulsaini.alertx.topDialog.message.TopAlertMessage
import kotlin.collections.removeFirstOrNull

object QueueManager {
    val alertQueue: ArrayDeque<TopAlertMessage> = ArrayDeque()
    var isShowing: Boolean = false

    fun enqueue(alert: TopAlertMessage){
        alertQueue.add(alert)
        if (!isShowing){
            nextShow()
        }
    }

    private fun nextShow() {
        val next = alertQueue.removeFirstOrNull() ?: return
        isShowing = true
        next.showInterval {
            isShowing = false
            nextShow()
        }
    }
}