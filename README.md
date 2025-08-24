# GhibliApp

GhibliApp is a simple Android application built with **Kotlin**, **Jetpack Compose**, and **MVVM architecture**.  
The app consumes the [Studio Ghibli API](https://ghibliapi.vercel.app/) to display a list of movies and allows users to view detailed information.

---

## Features

- Display a list of Studio Ghibli movies
- Search movies with a search bar
- Navigate to detail pages
- Fetch additional details dynamically via API
- Offline and error handling
- Reactive data flow using **Room**, **Retrofit**, and **Coroutines**

---

## Tech Stack

- **Language**: Kotlin  
- **UI**: Jetpack Compose, Material3  
- **Architecture**: MVVM (Model-View-ViewModel)  
- **Networking**: Retrofit + Coroutines  
- **Database**: Room  
- **Navigation**: Jetpack Navigation Component  

---

## Installation

1. Clone this repository  
   ```bash
   git clone https://github.com/iccangji/ghibli-app.git
   cd ghibli-app
   ```

2. Open the project in **Android Studio (Hedgehog or newer)**.

3. Sync Gradle and build the project.

4. Run the app on a physical device or emulator (Android 8.0+).

---

## Screenshots

| Home Screen | Detail Screen |
|-------------|---------------|
| ![List](https://github.com/user-attachments/assets/37fb25b3-20b4-4682-9b74-58d5c20f55c1) | ![Details](https://github.com/user-attachments/assets/31bd5a7a-c7f3-4a05-9d88-76e266cd1119)

---

## Project Structure

```
ghibli-app/
 ├── app/
 │   ├── data/
 │   │   ├── local/        # Room Database
 │   │   ├── remote/       # Retrofit API Service
 │   │   └── repository/   # Repository layer
 │   ├── ui/
 │   │   ├── preview/      # Movie list screen
 │   │   ├── screen/       # Movie detail screen
 │   │   └── theme/        # UI Theme
 │   ├── viewmodel/        # ViewModel
 │   └── MainActivity.kt   # App Activity
 ├── build.gradle
 └── README.md
```
