# GrabIt - Your Modern Shopping Experience 🛒

GrabIt is an **E-Commerce Android Application** built with **Jetpack Compose**, following **MVVM + Clean Architecture** principles.  
It uses the **FakeStore API** as a backend and demonstrates modern Android development practices such as **Hilt for Dependency Injection**, **StateFlow** for reactive state management, and **shimmer effects** for smooth loading states.

---

<!-- Banner/Image Placeholder -->
<img src="screenshots/grabIt_banner.png"  alt="cover"  width="100%" height ="50%"/>

## ✨ Features

- **Onboarding & Authentication**  
  - Intro, Login, and Register screens.

- **Bottom Navigation with 4 Sections**  
  - 🏠 **Home** – Top products, categories, search, and notifications.  
  - ❤️ **Favorites** – Manage your favorite products.  
  - 🛒 **Cart** – Add/remove products, adjust quantity, and view total.  
  - ⚙️ **Settings** – Profile card with edit option and app settings.

- **Search** – Find products with real-time filtering.  
- **Notifications** – View a list of all notifications.  
- **Shimmer Loading Effects** – Smooth UI while fetching data.  
- **Light & Dark Theme Support** – Auto adapts to device theme.  
- **Profile Management** – View and edit profile details.  
- **Modern Architecture** – Built with MVVM + Clean Architecture, Hilt, Coroutines, Flow, and Retrofit.

---

## 🏗️ Architecture

GrabIt follows **MVVM + Clean Architecture**, ensuring a scalable and maintainable codebase:

- **Presentation Layer** – Jetpack Compose UI, ViewModels managing **StateFlow** for reactive UI updates.  
- **Domain Layer** – Use Cases encapsulating business logic.  
- **Data Layer** – Repository pattern with **Retrofit** for API calls to the **FakeStore API**.

---

## 🛠️ Tech Stack

- **Jetpack Compose** – Modern declarative UI toolkit.  
- **Hilt (Dagger)** – Dependency Injection for modular, testable code.  
- **Kotlin Coroutines & Flow** – Asynchronous programming and reactive state management.  
- **Retrofit** – Network requests with FakeStore API.  
- **Coil** – Efficient image loading.  
- **Shimmer Effect** – Smooth loading animations for improved UX.
