# Class Description: MazeView

## 1. General Information
*   **Class Name:** `MazeView`
*   **Type:** `View` (Custom View)
    *   *Terminology:* A **View** is a rectangular area on the screen. A **Custom View** is one where the programmer manually draws everything (lines, circles, etc.) instead of using pre-made buttons.
*   **Purpose:** This class handles all the game logic. it generates the maze, draws the walls and the ball, handles movement physics, and detects when the ball hits a wall or the exit.
*   **Interactions:** 
    *   Controlled by `MainActivity` (which tells it how much the phone is tilting).
    *   Communicates back to `MainActivity` via an **Interface** (`OnMazeEventListener`) when the player wins.

---

## 2. Variables (Class Fields)

| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `maze` | `int[][]` | A 2D grid (matrix) representing the maze layout. | `generateMaze`, `onDraw`, `updateBallPosition` |
| `ballPosition` | `PointF` | Stores the `x` and `y` coordinates of the ball. | `updateBallPosition`, `onDraw` |
| `wallPaint` | `Paint` | Style settings (color, thickness) for the walls. | `init`, `onDraw` |
| `ballPaint` | `Paint` | Style settings for the ball. | `init`, `onDraw` |
| `cellSize` | `float` | The size of one single block in the maze. | `onSizeChanged`, `onDraw` |

*   **maze:** A value of `1` means a wall, `0` is a path, and `2` is the exit.
*   **Paint:** In Android, you don't just "draw a red circle". You use a `Paint` object to define that it should be red and filled.
*   **PointF:** A simple object that holds two decimal numbers (floating point) for X and Y.

---

## 3. Class Methods

### Method: `generateMaze()`
*   **Type:** `private`
*   **Return value:** `void`
*   **What it does:** 
    1.  Creates an empty grid filled with "walls" (value 1).
    2.  Uses an algorithm called **Recursive Backtracking** (like a smart mole digging tunnels) to "carve" paths (value 0).
    3.  Sets a specific entrance and exit (value 2).
*   **When called:** When a `newGame()` starts.
*   **Important:** This ensures every game is different.

### Method: `onDraw(Canvas canvas)`
*   **Type:** `protected`
*   **Return value:** `void`
*   **Parameters:** 
    | Name | Type | Description |
    | :--- | :--- | :--- |
    | `canvas` | `Canvas` | The virtual paper we draw on. |
*   **What it does:** 
    1.  Loops through the `maze` array.
    2.  If it finds a `1`, it draws a black square (wall).
    3.  If it finds a `2`, it draws a green square (exit).
    4.  Finally, it draws a red circle (the ball) at its current position.
*   **When called:** Every time `invalidate()` is called or the screen needs updating.

### Method: `updateBallPosition(float ax, float ay)`
*   **Type:** `public`
*   **Return value:** `void`
*   **Parameters:** `ax` (horizontal tilt), `ay` (vertical tilt).
*   **What it does:** 
    1.  Calculates the "potential" new position of the ball.
    2.  Checks the `maze` array at that position.
    3.  **Collision Detection:** If the new position is a wall (`1`), it cancels the move.
    4.  If it's a path or exit, it moves the ball and calls `invalidate()` (which triggers `onDraw`).
    5.  If it hits the exit (`2`), it notifies the listener.
*   **When called:** Manually by `MainActivity` when the phone tilts.

---

## 4. Interface Interaction (UI)
This class **IS** the UI. It doesn't use buttons; it uses the `onDraw` method to paint the entire game state directly onto the screen.

---

## 5. General logic of the class
`MazeView` is like a static map that knows where everything is. It waits for the "Driver" (`MainActivity`) to tell it where the ball wants to go. It then checks its "Map" (`maze` array) to see if that move is legal (no walls). If legal, it moves the ball and repaints the screen instantly.

---

## 6. Simplified explanation
Think of `MazeView` as a **Board Game**. 
- The `maze` array is the **Game Board** with fixed walls.
- The `ballPosition` is the **Pawn** on the board.
- `onDraw` is the act of **Looking at the board** to see where everything is.
- `updateBallPosition` is like **Trying to move the pawn**. If you try to move it through a wall, the board "pushes back" and you stay where you are.

---

## 7. Improvements & Bugs
*   **Bug/Issue:** The collision detection is "discrete". It checks the center of the ball against the grid. If the ball moves too fast or is too large, it might look like it's overlapping walls slightly. 
*   **Improvement:** For a smoother experience, adding "velocity" and "friction" to the ball movement would make it feel more realistic.
