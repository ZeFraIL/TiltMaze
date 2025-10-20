package zeev.fraiman.tiltmaze;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class MazeView extends View {

    public interface OnMazeEventListener {
        void onGameWon();
    }

    private Paint wallPaint;
    private Paint ballPaint;
    private Paint exitPaint;

    private PointF ballPosition;
    private OnMazeEventListener listener;

    private int[][] maze;
    private final int mazeWidth = 10; // Number of cells horizontally
    private final int mazeHeight = 15; // Number of cells vertically

    private float cellSize;
    private float ballRadius;
    private Random random = new Random();

    public MazeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setOnMazeEventListener(OnMazeEventListener listener) {
        this.listener = listener;
    }

    private void init() {
        wallPaint = new Paint();
        wallPaint.setColor(Color.BLACK);
        wallPaint.setStyle(Paint.Style.FILL);

        ballPaint = new Paint();
        ballPaint.setColor(Color.RED);
        ballPaint.setStyle(Paint.Style.FILL);

        exitPaint = new Paint();
        exitPaint.setColor(Color.GREEN);
        exitPaint.setStyle(Paint.Style.FILL);

        newGame();
    }

    public void newGame() {
        generateMaze();
        // Start position for the ball
        ballPosition = new PointF(1.5f, 1.5f);
        invalidate();
    }

    private void generateMaze() {
        maze = new int[mazeHeight * 2 + 1][mazeWidth * 2 + 1];
        // Initialize with walls
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                maze[i][j] = 1;
            }
        }

        carvePassages(1, 1);

        maze[1][0] = 0; // Start entrance
        maze[maze.length - 2][maze[0].length - 1] = 2; // Exit
    }

    private void carvePassages(int cx, int cy) {
        maze[cy][cx] = 0;
        Integer[] directions = {0, 1, 2, 3}; // N, S, E, W
        Collections.shuffle(Arrays.asList(directions), random);

        for (int direction : directions) {
            int nx = cx;
            int ny = cy;

            switch (direction) {
                case 0: // North
                    ny -= 2;
                    break;
                case 1: // South
                    ny += 2;
                    break;
                case 2: // East
                    nx += 2;
                    break;
                case 3: // West
                    nx -= 2;
                    break;
            }

            if (nx > 0 && nx < maze[0].length -1 && ny > 0 && ny < maze.length - 1 && maze[ny][nx] == 1) {
                maze[cy + (ny - cy) / 2][cx + (nx - cx) / 2] = 0;
                carvePassages(nx, ny);
            }
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float width = getWidth();
        float height = getHeight();
        int mazeGridWidth = maze[0].length;
        int mazeGridHeight = maze.length;

        cellSize = Math.min(width / mazeGridWidth, height / mazeGridHeight);
        ballRadius = cellSize / 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();
        int mazeGridWidth = maze[0].length;
        int mazeGridHeight = maze.length;

        float marginX = (width - mazeGridWidth * cellSize) / 2;
        float marginY = (height - mazeGridHeight * cellSize) / 2;

        canvas.translate(marginX, marginY);

        for (int i = 0; i < mazeGridHeight; i++) {
            for (int j = 0; j < mazeGridWidth; j++) {
                float x = j * cellSize;
                float y = i * cellSize;
                if (maze[i][j] == 1) { // Wall
                    canvas.drawRect(x, y, x + cellSize, y + cellSize, wallPaint);
                } else if (maze[i][j] == 2) { // Exit
                    canvas.drawRect(x, y, x + cellSize, y + cellSize, exitPaint);
                }
            }
        }

        // Draw the ball
        canvas.drawCircle(
                ballPosition.x * cellSize,
                ballPosition.y * cellSize,
                ballRadius,
                ballPaint);
    }

    public void updateBallPosition(float ax, float ay) {
        float newX = ballPosition.x + ax;
        float newY = ballPosition.y + ay;

        int gridX = (int) (newX);
        int gridY = (int) (newY);

        if (gridX >= 0 && gridX < maze[0].length && gridY >= 0 && gridY < maze.length) {
            if (maze[gridY][gridX] != 1) {
                ballPosition.x = newX;
                ballPosition.y = newY;
                invalidate(); // Redraw the view

                if (maze[gridY][gridX] == 2 && listener != null) {
                    listener.onGameWon();
                }
            }
        }
    }
}