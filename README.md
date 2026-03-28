# DoubleTape 🎬
DoubleTape is a modern, high-performance Android application built with Jetpack Compose that allows users to explore movie data via the TMDB (The Movie Database) API.
This project serves as a portfolio piece demonstrating best practices in Modern Android Development (MAD), including Clean Architecture, performance optimization in complex lists, and high-quality UI/UX animations.

## 🚀 Key Features
• Comprehensive Movie/Profile Exploration: Deep dives into actor filmographies and movie details.

• Intelligent Filmography View: A high-performance credit system that groups roles by year and merges multiple roles (e.g., Director & Writer) into single, clean entries.

• Search Engine: A fluid search interface with focus-aware TopAppBars and interactive filters.

• Advanced Animations: Synchronized list entry animations, and state-aware transitions.

## 🛠 Tech Stack
	• Language: Kotlin (100%)
	• UI: Jetpack Compose (Material 3)
	• Asynchronous Flow: Kotlin Coroutines & Flow (StateFlow/SharedFlow)
	• Dependency Injection: Hilt
	• Architecture: Clean Architecture (Domain, Data, UI) + MVVM
	• Image Loading: Coil 3 (using optimized request memoization)
	• Navigation: Compose Navigation with type-safety
	• Local UI Components: Custom-built shimmer effects, interactive TopAppBars, and reusable design system tokens.

## 💎 Engineering Highlights

1. High-Performance List Flattening
Unlike traditional nested scrollable components, DoubleTape uses a flattened LazyColumn approach. This ensures that even for actors with hundreds of credits, the UI remains at a locked 60/120 FPS by utilizing Compose's item recycling correctly across headers, tabs, and content.
2. UI/UX Polish

	• Optimized Image Requests: Custom Coil extension functions that utilize remember blocks to prevent redundant image request reconstructions during recomposition.

3. Clean Architecture
The project is strictly modularized:

	• :core:ui: Centralized design system, common composables (TopBars, Cards, Shimmers), and Theme definitions.

    • :domain: Pure Kotlin module containing business logic, repositories, and models (no Android dependencies).

    • :data: Implementation of repositories, API services, and data mappers.

## 🏗 Setup

1. Clone the repository:
    Shell Script

	    git clone https://github.com/vroff/DoubleTape.git

2. Obtain an API key from TMDB.

3. Add your API key to local.properties:
	Properties
	```TMDB_API_KEY="your_api_key_here"```

4. Build and run using Android Studio Ladybug or higher.

## 🤝 Contact
DoubleTape is a project by vroff. I am currently looking for new opportunities to build impactful Android experiences.

• LinkedIn: [https://www.linkedin.com/in/vladyslav-rynchenko/]

• Email: [rynchenko.vlad1408@gmail.com]

## License

This project is licensed under the Creative Commons Attribution-NonCommercial 4.0 International License (CC BY-NC 4.0).

https://creativecommons.org/licenses/by-nc/4.0/
