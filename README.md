# AlertX 🚀

AlertX is a versatile Android library for displaying sleek Toasts and Alerts with smooth animations. It supports multiple entry effects and allows you to position messages at both the **TOP** and **BOTTOM** of the screen.

## 📺 Demo

https://github.com/user-attachments/assets/8eefa0c4-4213-499c-9222-09bf8507ce15

https://github.com/user-attachments/assets/31007db7-bbf0-4308-ab7e-f65e75f57433

## ✨ Features

- **Flexible Alerts:** Choose between non-intrusive Toasts or interactive Alerts.
- **Top & Bottom Support:** All alerts can be shown at the **TOP** or **BOTTOM** of the screen.
- **Animations:** 
  - Morph Animation
  - Slide Vertical
  - Vertical Bounce
  - Slide Left/Right
  - Zoom & Fade
- **Standard Types:** Built-in styles for Success, Info, Warning, and Error.
- **Customizable:** Full control over colors, icons, and duration.

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
AlertXSheet.showInfoSheet(this, "Info: Alert appearing at the TOP")
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
