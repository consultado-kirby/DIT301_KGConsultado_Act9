APP DESCRIPTION >>

A modern Android chat application built with Kotlin and Jetpack Compose, featuring real-time messaging capabilities powered by Firebase.

FEATURES >>

1. Firebase Authentication: Secure user login and registration
2. Real-time Messaging: Send and receive messages instantly using Firestore
3. Modern UI: Built with Jetpack Compose for a responsive and intuitive user interface
4. User Management: Track current user sessions and manage logout functionality
5. Material Design 3: Follows the latest Material Design guidelines

SETUP >>

1. Clone the repository
2. Create your Firebase project at https://console.firebase.google.com/
3. Download your `google-services.json` file from Firebase Console
4. Place it in the `app/` directory (this file is gitignored for security)
5. Enable Firebase Authentication and Firestore in your Firebase project
6. Build and run the app

Note: Never commit `google-services.json` to version control as it contains sensitive API keys.

SCREENSHOTS >>

<div style="display: flex; flex-direction: row; justify-content: center; align-items: flex-start; gap: 16px;">
  <img src="screenshots/login_screen.jpg" alt="Login Screen" width="250" />
  <img src="screenshots/chat_screen.jpg" alt="Chat Screen" width="250" />
</div>
<img src="screenshots/firestore_console.png" alt="Firestore Console" width="1000" />