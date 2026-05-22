# 📱 Android Application Documentation: TiltMaze

________________________________________
## 🧾 General Information
**Project Name:**
TiltMaze
**Author(s):**
Zeev Fraiman
**Date:**
May 2024
**Language:**
Java
**Development Environment:**
Android Studio
**Android Version (minSdk / targetSdk):**
28 / 36
________________________________________
## 🎯 Project Goal
•	**What problem does the app solve:** It allows users to play a classic maze game using the device's accelerometer to control the ball's movement.

•	**Why is this task important:** It demonstrates how to work with hardware sensors and create custom graphical components (Custom View).

•	**Target Audience:** Fans of simple arcade games and puzzles.
________________________________________
## 📌 Application Requirements
**Functional Requirements**
•	Generate a random maze upon every launch.
•	Control the ball by tilting the device.
•	Detect collisions with walls.
•	Win condition when reaching the finish (green zone).
**Non-functional Requirements**
•	**Performance:** Smooth rendering at 60 FPS.
•	**Usability:** Intuitive tilt-based controls.
•	**Reliability:** Proper handling of orientation changes and Activity lifecycle.
________________________________________
## 🧠 General Architecture
•	**Selected Approach:**
–	Activity and Custom View interaction (simplified MVC pattern).

•	**Why it was chosen:** For a small game project, this ensures minimal latency between sensor data acquisition and screen updates.

•	**Main System Components:**
–	`MainActivity`: Manages sensors and lifecycle events.
–	`MazeView`: Handles maze logic, rendering, and ball physics.
________________________________________
## 🧩 UML Diagram
`[MainActivity] –- (SensorEvent) -–> [MazeView]`
`[MazeView] –- (onGameWon) -–> [MainActivity]`

**Package Structure:**
`zeev.fraiman.tiltmaze` — contains all core classes. A compact structure suitable for a small-scale project.
________________________________________
## 🧩 Detailed Class Description
### 📌 Class: MainActivity
**Role:**
The main controller of the application.
**Responsibility:**
Initializing the SensorManager, subscribing to accelerometer data, and handling system events.
**Main Methods:**
- `onCreate()` — Sets up the UI and initializes sensors.
- `onSensorChanged()` — Receives tilt data and passes it to MazeView.
- `onGameWon()` — Displays the victory dialog.
**Interaction with other classes:**
Sends tilt coordinates to `MazeView` and listens for the game-win event.
________________________________________
### 📌 Class: MazeView
**Role:**
The graphical core and game engine.
**Why it is used:**
Required to implement custom rendering for the maze and the ball, which is not possible with standard widgets.
**Responsibility:**
- Maze generation (Recursive Backtracking algorithm).
- Collision detection.
- Drawing elements on the Canvas.
________________________________________
## 🔄 Application Workflow Diagram
1. App Launch -> `MainActivity` requests accelerometer data.
2. `MazeView` generates the maze and draws the ball at the starting point.
3. User tilts the device -> `MainActivity` catches the event.
4. `MazeView` updates the ball position and checks against walls.
5. Ball touches the green zone -> `MainActivity` shows "You Won!".
________________________________________
## 🎨 UI/UX Analysis
•	**Why the interface is designed this way:** Maximum focus on the game field. Black walls on a white background provide high contrast.

•	**Principles used:**
–	**Simplicity:** No unnecessary menus or buttons.
–	**Logicity:** The ball rolls in the direction the screen is tilted.
–	**Accessibility:** Clear color indicators (Red for player, Green for exit).
________________________________________
## ⚙️ Threading
•	**Tools used:**
–	Main Thread (UI Thread)
•	**Why this method was chosen:** For this project's complexity, the standard UI thread is sufficient for handling sensor events and Canvas redrawing.
•	**Prevention methods:**
–	**ANR:** Sensor processing code is optimized and non-blocking.
–	**Memory Leaks:** Sensor listener is unregistered in `onPause()`.
________________________________________
## 💾 Data Management
•	**Data storage:** In-memory (array `maze[][]`).
•	**Why this method was chosen:** The maze is generated fresh for each session; persistent storage is not required.
________________________________________
## 🔐 Security (Basic Level)
•	The app does not collect or store any sensitive user data.
________________________________________
## 🧪 Testing
•	Manual testing of movement physics and wall collision logic.
•	Verification of maze generation correctness (no unreachable areas).
________________________________________
## 🐞 Error Handling
•	Checks for accelerometer availability on the system.
•	Boundary checks for ball movement within the maze array.
________________________________________
## ⚡ Performance
•	**Optimizations:** Using `invalidate()` only when position changes.
•	**Bottlenecks:** Extremely large mazes might slow down drawing, but the current 10x15 size is optimal.
________________________________________
## 🚀 Extension Possibilities
•	Adding multiple difficulty levels.
•	Timer and high-score table.
•	Collecting bonuses inside the maze.
________________________________________
## 📊 Project Self-Assessment
| Criterion | Rating (1–10) |
| :--- | :--- |
| Architecture | 8 |
| Code | 9 |
| UI/UX | 7 |
| Reliability | 10 |
| **Overall Level** | **8.5** |
________________________________________
## 🏁 Conclusion
•	**What went best:** Implementing smooth ball movement and the generation algorithm.
•	**What was challenging:** Tuning the accelerometer sensitivity for comfortable control.
•	**Skills Acquired:** Working with `SensorManager`, custom drawing in Android, graph generation algorithms.
