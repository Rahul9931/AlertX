package com.rahulsaini.alertx

import android.content.Context

/**
 * Shows a success message at the top of the screen
 * @param message The text to display
 * @param duration How long to show the message in milliseconds (default: 3000)
 * @param config Custom configuration for the message appearance
 */
public fun Context.showSuccessMessage(
    message: String,
    duration: Long = 3000,
    config: MessageDialog.MessageConfig = MessageDialog.MessageConfig.defaultSuccess
): MessageDialog {
    return MessageDialog.Builder(this)
        .setMessage(message)
        .setType(MessageDialog.MessageType.SUCCESS)
        .setDuration(duration)
        .setConfig(config)
        .show()
}

/**
 * Shows an info message at the top of the screen
 * @param message The text to display
 * @param duration How long to show the message in milliseconds (default: 3000)
 * @param config Custom configuration for the message appearance
 */
public fun Context.showInfoMessage(
    message: String,
    duration: Long = 3000,
    config: MessageDialog.MessageConfig = MessageDialog.MessageConfig.defaultInfo
): MessageDialog {
    return MessageDialog.Builder(this)
        .setMessage(message)
        .setType(MessageDialog.MessageType.INFO)
        .setDuration(duration)
        .setConfig(config)
        .show()
}

/**
 * Shows an error message at the top of the screen
 * @param message The text to display
 * @param duration How long to show the message in milliseconds (default: 3000)
 * @param config Custom configuration for the message appearance
 */
public fun Context.showErrorMessage(
    message: String,
    duration: Long = 3000,
    config: MessageDialog.MessageConfig = MessageDialog.MessageConfig.defaultError
): MessageDialog {
    return MessageDialog.Builder(this)
        .setMessage(message)
        .setType(MessageDialog.MessageType.ERROR)
        .setDuration(duration)
        .setConfig(config)
        .show()
}