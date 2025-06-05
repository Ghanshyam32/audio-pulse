# AudioPulse

## Overview

AudioPulse is an Android music streaming application designed to provide a seamless music listening experience. Users can browse music by categories, explore curated sections, and view a "Mostly Played" section featuring the top 10 most-played songs. The app integrates with Firebase Firestore for dynamic data retrieval, uses ExoPlayer for high-quality audio playback, and features a modern, user-friendly interface with smooth scrolling RecyclerViews and visually appealing image loading via Glide.

## Features

- **Category Browsing**: Explore music categories with cover images and navigate to detailed song lists.
- **Song Playback**: Play songs with a dedicated player interface powered by ExoPlayer.
- **Curated Sections**: View sections like "Section 1," "Section 2," and "Mostly Played" with horizontally scrolling song lists.
- **Mostly Played Tracks**: Displays the top 10 songs based on play counts stored in Firestore, updated in real-time.
- **Mini-Player**: A persistent mini-player on the homescreen shows the current song and allows quick access to the full player.
- **Responsive UI**: Smooth scrolling with RecyclerViews and rounded cover images using Glide.
- **Firebase Integration**: Fetches song, category, and section data from Firestore and tracks play counts.

## Screenshots

Below are screenshots showcasing the main features of AudioPulse:

| **Homescreen**                                    | **Category Screen**                                   | **Music Player**                                     |
| ------------------------------------------------- | ----------------------------------------------------- | ---------------------------------------------------- |
| ![Homescreen](assets/screenshots/homescreen.png)  | ![Category Screen](assets/screenshots/categories.png) | ![Music Player](assets/screenshots/music-player.png) |
| Displays categories, sections, and a mini-player. | Shows a list of songs for a selected category.        | Full-screen player with playback controls.           |

_Replace the placeholder URLs above with the actual URLs of your screenshots after uploading them to your GitHub repository or an external hosting service._

## Installation

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/ghanshyam/audiopulse.git
   ```
2. **Open in Android Studio**:
   - Import the project into Android Studio.
   - Ensure you have the Android SDK and Gradle installed.
3. **Set Up Firebase**:
   - Create a Firebase project in the [Firebase Console](https://console.firebase.google.com/).
   - Add your Android app to the Firebase project and download the `google-services.json` file.
   - Place the `google-services.json` file in the `app/` directory.
   - Enable Firestore in the Firebase Console and set up the following collections:
     - `category`: Stores `CategoryModel` documents with `name` (String), `coverUrl` (String), `songs` (List<String>).
     - `songs`: Stores `SongModel` documents with `id` (String), `title` (String), `subtitle` (String), `url` (String), `coverUrl` (String), `count` (Long).
     - `sections`: Stores section documents (e.g., `section_1`, `section_2`, `mostly_played`) with `name` (String), `songs` (List<String>).
4. **Sync and Build**:
   - Sync the project with Gradle in Android Studio.
   - Build and run the app on an emulator or physical device (API 21 or higher recommended).

## Usage

1. Launch the app to access the homescreen, featuring categories, sections, and a "Mostly Played" list.
2. Tap a category or section to view its song list in the `SongsListActivity`.
3. Select a song to start playback and navigate to the `PlayerActivity` for full-screen controls.
4. Use the mini-player on the homescreen to monitor the current song and access the full player.
5. Play counts are automatically updated in Firestore, refreshing the "Mostly Played" section.

## Project Structure

```
audiopulse/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/ghanshyam/audiopulse/
│   │   │   │   ├── Models/
│   │   │   │   │   ├── CategoryModel.kt      # Data class for categories
│   │   │   │   │   └── SongModel.kt          # Data class for songs
│   │   │   │   ├── adapter/
│   │   │   │   │   ├── CategoryAdapter.kt    # Adapter for category RecyclerView
│   │   │   │   │   ├── SectionSongListAdapter.kt  # Adapter for section song lists
│   │   │   │   │   └── SongsListAdapter.kt   # Adapter for song lists
│   │   │   │   ├── MainActivity.kt           # Homescreen with categories and sections
│   │   │   │   ├── MyExoplayer.kt            # ExoPlayer wrapper for audio playback
│   │   │   │   ├── PlayerActivity.kt         # Full-screen player activity
│   │   │   │   └── SongsListActivity.kt      # Activity for category song lists
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   ├── activity_main.xml     # Layout for homescreen
│   │   │   │   │   ├── activity_player.xml   # Layout for player
│   │   │   │   │   ├── activity_songs_list.xml  # Layout for song lists
│   │   │   │   │   ├── category_item_recycler_row.xml  # Layout for category items
│   │   │   │   │   ├── song_item.xml         # Layout for song items
│   │   │   │   │   └── song_section.xml      # Layout for section song items
│   │   │   └── AndroidManifest.xml           # App manifest
│   └── build.gradle                          # App-level Gradle file
└── build.gradle                              # Project-level Gradle file
```

## Technologies Used

- **Kotlin**: Primary programming language for Android development.
- **Android SDK**: Core framework for building the app.
- **Firebase Firestore**: Backend for storing and retrieving song, category, and section data.
- **ExoPlayer**: For efficient and customizable audio playback.
- **Glide**: For loading and displaying song and category cover images with rounded corners.
- **RecyclerView**: For smooth, scrollable lists of categories and songs.
- **View Binding**: For type-safe access to UI components.

## Dependencies

Add the following to your `app/build.gradle`:

```gradle
dependencies {
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'androidx.recyclerview:recyclerview:1.3.2'
    implementation 'com.google.firebase:firebase-firestore:24.10.0'
    implementation 'com.github.bumptech.glide:glide:4.16.0'
    implementation 'androidx.media3:media3-exoplayer:1.4.1'
    implementation 'androidx.media3:media3-ui:1.4.1'
}
```

Apply the Firebase plugin in `app/build.gradle`:

```gradle
apply plugin: 'com.google.gms.google-services'
```

## Firebase Setup

- **Firestore Collections**:
  - `category`: Documents with `name` (String), `coverUrl` (String), `songs` (List<String>).
  - `songs`: Documents with `id` (String), `title` (String), `subtitle` (String), `url` (String), `coverUrl` (String), `count` (Long).
  - `sections`: Documents (e.g., `section_1`, `section_2`, `mostly_played`) with `name` (String), `songs` (List<String>).
- Configure Firestore rules to allow read/write access for authenticated users or adjust for your app’s security needs.

## Contributing

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Make your changes and commit (`git commit -m "Add feature"`).
4. Push to the branch (`git push origin feature-branch`).
5. Create a pull request.

## Notes

- Requires a stable internet connection for Firestore data retrieval.
- Audio files must be accessible via URLs stored in the `songs` collection.
- Test on devices or emulators with Android API 21 or higher.
- The "Mostly Played" section dynamically updates based on play counts tracked in Firestore.
- Ensure proper Firebase setup to avoid data retrieval errors.
- To display screenshots, upload them to the `screenshots/` folder in your repository or an external hosting service and update the URLs in the **Screenshots** section.
