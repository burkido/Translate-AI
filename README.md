# Translate AI

**Description:**

Translate AI is a Kotlin Multiplatform Mobile (KMM) project that embraces Clean Architecture principles and follows the Model-View-Intent (MVI) pattern. This project structure is designed to provide a scalable and maintainable foundation for Android development and IOS (IOS part is missing. Must be continued). Also, Material 3 theming is applied to the Android side

https://github.com/burkido/Translate-AI/assets/74553201/9598f5ef-866b-4ccf-8038-635a074038c4

The overall project structure for shared module is described below, featuring a sample from the shared module. For a better understanding, please refer to the entire project. Following this project https://github.com/philipplackner/Translator_KMM (You can follow part by part from branches.), I integrated Material 3 theming, a bottom bar, and the saved feature.

## Shared Packages

### 1. Core

- **Domain:** Contains the core business logic and domain entities.
- **Presentation:** Handles UI-related logic for the core module.

### 2. Saved (Feature)

- **Data:**
  - **Remote:** Manages data retrieval from remote sources (e.g., API) for saved translations.
  - **Local:** Handles local data storage and retrieval for saved translations.
- **Domain:**
  - **Saved:** Contains data sources and item-related logic for saved translations.
- **Presentation:** Manages the UI logic for the saved translations feature.


### 3. Translate (Feature)

- **Data:**
  - **Remote:** Manages data retrieval from remote sources (e.g., API).
  - **Local:** Handles local data storage and retrieval.
- **Domain:**
  - **History:** Contains data sources and item-related logic for translation history.
  - **Translate:** Includes use cases and possible errors related to translation.
- **Presentation:** Manages the UI logic for the translation feature.

### 4. VoiceToText (Feature)

- **Domain:** Handles business logic specific to the voice-to-text feature.
- **Presentation:** Manages the UI logic for the voice-to-text feature.

### 5. Util

- Includes utility classes and functions that can be shared across different parts of the project.
