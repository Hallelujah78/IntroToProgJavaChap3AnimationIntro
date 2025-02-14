package com.mycompany.mavenproject2;

import java.awt.Rectangle;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This file can be used to create very simple animations. Just fill in
 * the definition of drawFrame with the code to draw one frame of the
 * animation, and possibly change a few of the values in the rest of
 * the program as noted below.
 */
public class SimpleAnimationStarter extends Application {

    /**
     * Draws one frame of an animation. This subroutine should be called
     * about 60 times per second. It is responsible for redrawing the
     * entire drawing area. The parameter g is used for drawing. The frameNumber
     * starts at zero and increases by 1 each time this subroutine is called.
     * The parameter elapsedSeconds gives the number of seconds since the
     * animation
     * was started. By using frameNumber and/or elapsedSeconds in the drawing
     * code, you can make a picture that changes over time. That's an animation.
     * The parameters width and height give the size of the drawing area, in
     * pixels.
     */
    public void drawFrame(GraphicsContext g, int frameNumber, double elapsedSeconds, int width, int height) {

        // Create a rectangle
        Rectangle rect = new Rectangle(50, 50, 200, 100); // x, y, width, height

        // frame is 800 x 600 (w x h)
        // fill entire context with lightgray.
        g.setFill(Color.BLACK);
        g.fillRect(0, 0, width, height); // First, fill the entire image with a background color!

        /*
        The left edge of the rectangle hits the right side of the screen on frame 800.
        We have access to the updated frame number each time drawFrame is called.
        while rectangle moves to right first time:
        frameNumber % 800 = frameNumber.

         */
        System.out.println("width - frame % 800: " + (width - frameNumber % 800));

        // Set fill to black.
        g.setFill(Color.WHITE);

        // Side length of the square.
        int size = 100;
        // The speed of the animation.
        int speed = 1;

        for (int i = 0; i <= 6; i++) {
            /* Using Math.floor(frameNumber / width) we get 0, 1, 2, 3, 4 ....
        By testing if it is odd or even, we can oscillate the motion.
             */
            // Update speed.
            speed = speed + i;

            if (Math.floor(frameNumber * speed / (width - size)) % 2 == 0) {
                // Move rect across the screen as frame number increases.
                g.fillRect((frameNumber * speed % (width - size)), size * i, size, size);
            } else {
                g.fillRect((width - size - (frameNumber * speed % (width - size))), size * i, size, size);
            }

        }

//        double inset = height / 10;
        // set the stroke
//        for (int i = 0; i < inset; i++) {
//            g.strokeRect(inset / 2 * i, inset / 2 * i, width - inset * i, height - inset * i);
//
//        }
        g.setFill(Color.WHITE);
        g.setStroke(Color.WHITE);
//         g.strokeRect(inset / 2 * 9, inset / 2 * 9, width - inset * 9, height - inset * 9);
        g.fillText("Frame number " + frameNumber, 40, 50);
        g.fillText(String.format("Elapsed Time: %1.1f seconds", elapsedSeconds), 40, 80);
    }

    //------ Implementation details: DO NOT EXPECT TO UNDERSTAND THIS ------
    public void start(Stage stage) {
        int width = 800;   // The width of the image.  You can modify this value!
        int height = 600;  // The height of the image. You can modify this value!
        Canvas canvas = new Canvas(width, height);
        drawFrame(canvas.getGraphicsContext2D(), 0, 0, width, height);
        BorderPane root = new BorderPane(canvas);
        root.setStyle("-fx-border-width: 4px; -fx-border-color: #444");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Simple Animation"); // STRING APPEARS IN WINDOW TITLEBAR!
        stage.show();
        stage.setResizable(false);
        AnimationTimer anim = new AnimationTimer() {
            private int frameNum;
            private long startTime = -1;
            private long previousTime;

            public void handle(long now) {
                if (startTime < 0) {
                    startTime = previousTime = now;
                    drawFrame(canvas.getGraphicsContext2D(), 0, 0, width, height);
                } else if (now - previousTime > 0.95e9 / 60) {
                    // The test in the else-if is to make sure that drawFrame() is
                    // called about once every 1/60 second.  It is required since
                    // handle() can be called by the system more often than that.
                    frameNum++;
                    drawFrame(canvas.getGraphicsContext2D(), frameNum, (now - startTime) / 1e9, width, height);
                    previousTime = now;
                }
            }
        };
        anim.start();
    }

    public static void main(String[] args) {
        launch();
    }

} // end SimpleAnimationStarter
