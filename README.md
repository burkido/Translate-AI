# Translate AI

**Description:**

Translate AI is a Kotlin Multiplatform Mobile (KMM) project that embraces Clean Architecture principles and follows the Model-View-Intent (MVI) pattern. This project structure is designed to provide a scalable and maintainable foundation for Android development and IOS (IOS part is missing. Must be continued). Also, Material 3 theming is applied to the Android side

General structure of project is described below. It is a sample from shared module. You can see the whole project for better understanding.

## Packages

### 1. Core

- **Domain:** Contains the core business logic and domain entities.
- **Presentation:** Handles UI-related logic for the core module.

### 2. Translate (Feature)

- **Data:**
  - **Remote:** Manages data retrieval from remote sources (e.g., API).
  - **Local:** Handles local data storage and retrieval.
- **Domain:**
  - **History:** Contains data sources and item-related logic for translation history.
  - **Translate:** Includes use cases and possible errors related to translation.
- **Presentation:** Manages the UI logic for the translation feature.

### 3. VoiceToText (Feature)

- **Domain:** Handles business logic specific to the voice-to-text feature.
- **Presentation:** Manages the UI logic for the voice-to-text feature.

### 4. Util

- Includes utility classes and functions that can be shared across different parts of the project.