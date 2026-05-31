# AlertX 🚀

AlertX is a versatile Android library for displaying sleek Toasts and Alerts with smooth animations. It supports multiple entry effects and allows you to position messages at both the **TOP** and **BOTTOM** of the screen.

## 📺 Demo

<table style="width: 100%; border: none;">
  <tr>
    <td align="center" style="border: none;">
      <b>Alert Sheets</b><br>
      <video src="https://github.com/user-attachments/assets/8eefa0c4-4213-499c-9222-09bf8507ce15" width="320" muted autoplay loop playsinline style="border-radius: 10px;"></video>
    </td>
    <td align="center" style="border: none;">
      <b>Alert Toasts</b><br>
      <video src="https://github.com/user-attachments/assets/31007db7-bbf0-4308-ab7e-f65e75f57433" width="320" muted autoplay loop playsinline style="border-radius: 10px;"></video>
    </td>
  </tr>
</table>

## ✨ Features

- **Flexible Alerts:** Choose between non-intrusive Toasts or interactive Alerts.
- **Top & Bottom Support:** All alerts can be shown at the **TOP** or **BOTTOM** of the screen.
- **Animations:** 
  - Morph Animation (Transforming ball entry)
  - Slide Vertical
  - Vertical Bounce
  - Slide Left/Right
  - Zoom & Fade
- **Standard Types:** Built-in styles for Success, Info, Warning, and Error.
- **Fully Customizable:** Full control over colors, icons, and duration.

## 🚀 Installation

Add it to your `settings.gradle` (or root `build.gradle`):

```gradle
dependencyResolutionManagement {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency:

```gradle
dependencies {
    implementation 'com.github.rahulsaini:AlertX:1.0.0'
}
```

## 🛠 Usage

### 1. AlertX Toast
Toasts are used for brief, non-intrusive messages.

```kotlin
// Success Toast
AlertXToast.showSuccessToast(this, "Success: Data saved!")
    .setPosition(AlertPosition.TOP)
    .show()

// Warning Toast
AlertXToast.showWarningToast(this, "Warning: Storage almost full!")
    .setAnimation(AlertAnimationType.SLIDE_FROM_LEFT)
    .show()

// Info Toast
AlertXToast.showInfoToast(this, "Info: New update available.")
    .show()

// Error Toast
AlertXToast.showErrorToast(this, "Error: Connection failed!")
    .setPosition(AlertPosition.BOTTOM)
    .show()

// Custom Toast
val customStyle = MessageStyle(
    containerBackgroundColorRes = R.color.custom_bg,
    textColor = Color.WHITE,
    duration = 5000,
    animationType = AlertAnimationType.MORPH_FROM_BALL
)
AlertXToast.showCustomToast(this, "Custom Message", customStyle)
```

### 2. AlertX Sheet
Sheets behave similarly to toasts but use a different layout style and can be positioned anywhere.

```kotlin
// Success Sheet
AlertXSheet.showSuccessSheet(this, "Success: Process finished!")
    .setPosition(AlertPosition.TOP)
    .show()

// Warning Sheet
AlertXSheet.showWarningSheet(this, "Warning: Check your settings.")
    .setAnimation(AlertAnimationType.SLIDE_FROM_VERTICAL_BOUNCE)
    .show()

// Info Sheet
AlertXSheet.showInfoSheet(this, "Info: Sheet at the bottom.")
    .setPosition(AlertPosition.BOTTOM)
    .show()

// Error Sheet
AlertXSheet.showErrorSheet(this, "Error: Something went wrong!")
    .setAnimation(AlertAnimationType.ZOOM)
    .show()
```

## ⚙️ Global Configuration

You can customize the default styles for all toasts and sheets globally in your `Application` class.

```kotlin
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Global Toast Styles
        AlertXToast.initialize(this) {
            successStyle = successStyle.copy(
                textColor = Color.WHITE,
                duration = 2000
            )
            errorStyle = errorStyle.copy(
                containerBackgroundColorRes = R.color.dark_red
            )
        }

        // Initialize Global Sheet Styles
        AlertXSheet.initialize(this) {
            infoStyle = infoStyle.copy(
                animationType = AlertAnimationType.MORPH_FROM_BALL
            )
        }
    }
}
```

## 🎨 Animation Types
- `MORPH_FROM_BALL`
- `SLIDE_FROM_VERTICAL`
- `SLIDE_FROM_VERTICAL_BOUNCE`
- `SLIDE_FROM_LEFT` / `SLIDE_FROM_RIGHT`
- `ZOOM`
- `FADE`

## 📄 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
