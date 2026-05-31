# AlertX 🚀

A powerful and highly customizable library for Android to display beautiful Toasts and Alerts with smooth animations. AlertX allows you to show messages at both the **TOP** and **BOTTOM** of the screen.

## 📺 Demo

### Alert Toasts
<video src="media/toast_demo.mp4" autoplay loop muted playsinline style="width: 100%; max-width: 400px; border-radius: 10px;"></video>

### Alert Sheets
<video src="media/sheet_demo.mp4" autoplay loop muted playsinline style="width: 100%; max-width: 400px; border-radius: 10px;"></video>

## ✨ Features

- **Flexible Alerts:** Choose between non-intrusive Toasts or interactive Alert Sheets.
- **Top & Bottom Support:** All alerts (including Sheets) can be shown at the **TOP** or **BOTTOM** of the screen.
- **Animations:** 
  - Morph Animation (Transforming ball entry)
  - Slide Vertical
  - Vertical Bounce
  - Slide Left/Right
  - Zoom & Fade
- **Pre-styled Types:** Built-in styles for Success, Info, Warning, and Error.
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

### AlertX Toast
```kotlin
AlertXToast.showSuccessToast(this, "Success: Data saved!")
    .setAnimation(AlertAnimationType.SLIDE_FROM_VERTICAL)
    .setPosition(AlertPosition.TOP)
    .show()
```

### AlertX Sheet
```kotlin
AlertXSheet.showInfoSheet(this, "Info: Sheet appearing at the TOP")
    .setAnimation(AlertAnimationType.MORPH_FROM_BALL)
    .setPosition(AlertPosition.TOP)
    .show()
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
