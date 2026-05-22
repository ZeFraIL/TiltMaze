# Class Description: MainActivity

## 1. General Information
*   **Class Name:** `MainActivity`
*   **Type:** `Activity`
    *   *Terminology:* An **Activity** is a single screen in an Android app where the user can interact with the system.
*   **Purpose:** This class is the "brain" and the entry point of the application. It is responsible for setting up the game screen, managing the device's sensors (specifically the accelerometer), and reacting to the game outcome (winning).
*   **Interactions:** 
    *   It contains and controls `MazeView`.
    *   It listens to the hardware **Accelerometer** (sensor that detects tilt).
    *   It implements `MazeView.OnMazeEventListener` to know when the player finishes the maze.

---

## 2. Variables (Class Fields)

| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `mazeView` | `MazeView` | Holds a reference to the custom game view. | `onCreate`, `onSensorChanged`, `onGameWon` |
| `sensorManager` | `SensorManager` | A system service that lets us access the device's sensors. | `onCreate`, `onResume`, `onPause`, `onGameWon` |
| `accelerometer` | `Sensor` | Represents the physical accelerometer sensor. | `onCreate`, `onResume` |

*   **mazeView:** This is the container where the maze is drawn. We need it to tell the ball where to move.
*   **sensorManager:** Think of this as the "manager" of all hardware sensors. Without it, we can't "ask" the phone about its tilt.
*   **accelerometer:** This is the specific "tool" (sensor) we are using from the manager.

---

## 3. Class Methods

### Method: `onCreate(Bundle savedInstanceState)`
*   **Type:** `protected`
*   **Return value:** `void` (returns nothing)
*   **Parameters:** 
    | Name | Type | Description |
    | :--- | :--- | :--- |
    | `savedInstanceState` | `Bundle` | Data from a previous state (if the app was restarted). |
*   **What it does:** 
    1.  Sets up the visual layout (`activity_main.xml`).
    2.  Forces the screen to stay in **Portrait** mode (vertical).
    3.  Enables **EdgeToEdge** (full-screen experience).
    4.  Connects the `mazeView` variable to the actual view in the layout using `findViewById`.
    5.  Initializes the `sensorManager` and the `accelerometer`.
*   **When called:** Automatically by Android when the screen is first created.
*   **Important:** This is where everything begins. If we forget to initialize something here, the app will crash later.

### Method: `onSensorChanged(SensorEvent event)`
*   **Type:** `public`
*   **Return value:** `void`
*   **Parameters:** 
    | Name | Type | Description |
    | :--- | :--- | :--- |
    | `event` | `SensorEvent` | Contains information about the current tilt values. |
*   **What it does:** 
    1.  Checks if the data is coming from the **Accelerometer**.
    2.  Extracts the `x` and `y` tilt values.
    3.  Scales the values (divides by 100) to make the ball movement smooth.
    4.  Calls `mazeView.updateBallPosition` to move the ball on the screen.
*   **When called:** Every time the device is tilted or moved.
*   **Important:** This method is called very frequently (many times per second). It must be fast to avoid lag.

### Method: `onGameWon()`
*   **Type:** `public`
*   **Return value:** `void`
*   **What it does:** 
    1.  Stops listening to the sensor (pauses movement).
    2.  Displays an **AlertDialog** (a pop-up window) saying "You Won!".
    3.  Provides a "Play Again" button that resets the game and restarts the sensor.
*   **When called:** Triggered by `MazeView` when the ball reaches the exit.

---

## 4. Lifecycle

*   **onCreate():** Called when the activity starts. Used for initialization.
*   **onResume():** Called when the activity becomes visible. Here, we "register" the sensor listener so the game starts reacting to tilt.
*   **onPause():** Called when the user leaves the app or a call comes in. We "unregister" the sensor here to **save battery**.

---

## 5. Interface Interaction (UI)
*   **Elements:** `MazeView` (id: `maze_view`).
*   **Connection:** Uses `findViewById(R.id.maze_view)` to link the Java code to the XML layout.
*   **Events:** Handles the "Win" event via a callback interface.

---

## 6. Interaction with other components
*   **MazeView:** Direct communication. `MainActivity` sends tilt data to `MazeView`, and `MazeView` notifies `MainActivity` about the victory.
*   **AlertDialog:** Creates a system pop-up to interact with the user at the end of the game.

---

## 7. General logic of the class
The class acts as a bridge. It takes raw data from the phone's hardware (tilt) and translates it into game actions (moving the ball). When the ball hits the goal, the class stops the "translation" and celebrates with the user.

---

## 8. Simplified explanation
Imagine `MainActivity` is the **Driver** of a car. 
- The **Accelerometer** is the **Steering Wheel**.
- The **MazeView** is the **Car** itself moving on the road.
- The `onCreate` method is the driver getting into the car and starting the engine.
- `onSensorChanged` is the driver turning the wheel to move the car.
- `onGameWon` is the driver reaching the destination and putting the car in park.
