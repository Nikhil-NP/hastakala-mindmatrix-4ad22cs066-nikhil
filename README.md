# Hasta-Kala Shop

Hasta-Kala Shop is an offline Android app for small craft sellers and rural artisans. It helps the user record quick sales, reduce stock automatically, view sales charts, and check income history.

## What Is Included

- Native Android project in Kotlin
- XML layouts, not Jetpack Compose
- Single `MainActivity` with fragment-based screens
- MVVM structure with Repository + Room database
- Product stock screen with add/edit stock
- Quick bill entry with total calculation
- Dashboard with MPAndroidChart pie and bar charts
- Income history with swipe-to-delete transaction
- Local sample products inserted on first launch

## Project Structure

```text
app/src/main/java/com/hastakala/app
+-- data
|   +-- dao
|   +-- database
|   +-- entity
|   +-- model
+-- repository
+-- viewmodel
+-- ui
|   +-- billing
|   +-- dashboard
|   +-- income
|   +-- stock
+-- adapter
+-- utils
```

## Main Features

1. Products / Stock
   - Add product variant
   - Edit stock and low-stock threshold
   - Color indicator for good, low, and empty stock

2. Quick Bill
   - Select product
   - Enter quantity and price
   - Total amount updates automatically
   - Saving a sale reduces product stock

3. Dashboard
   - Revenue total
   - Items sold
   - Pie chart for product sales distribution
   - Bar chart for daily revenue
   - Filters: Today, This Week, This Month, All Time

4. Income
   - Sale history
   - Total income by filter
   - Swipe a sale left or right to delete it
   - Deleted sale restores stock quantity

## Manual To Run

1. Install Android Studio.

2. Open this folder as a project:

   ```text
   /home/nikhil/Documents/projects/hastakala
   ```

3. Let Android Studio run Gradle Sync.

4. If Android Studio asks for an Android SDK, install:
   - Android SDK Platform 35
   - Android SDK Build-Tools
   - Android Emulator, if you want to use an emulator

5. Create or connect a device:
   - Use Android Emulator from Device Manager, or
   - Connect an Android phone with USB debugging enabled.

6. Click Run in Android Studio.

## Important Dependencies

The app uses these libraries:

```kotlin
androidx.room:room-runtime
androidx.room:room-ktx
androidx.lifecycle:lifecycle-livedata-ktx
androidx.lifecycle:lifecycle-viewmodel-ktx
com.google.android.material:material
com.github.PhilJay:MPAndroidChart
```

MPAndroidChart is downloaded from JitPack, so the first Gradle Sync needs internet access.

