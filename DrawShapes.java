package com.example.drawshapes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class DrawShapes extends Application {

    private double prevX, prevY;
    private double x, y;
    private boolean dragging;
    private GraphicsContext g;
    private Canvas canvas;


    public void start(Stage stage) {
        double width = 500;
        double height = 350;
        canvas = new Canvas(width,height);
        draw();

        canvas.setOnMousePressed(evt -> {
            if (dragging)
                return;
            x = evt.getX();
            y = evt.getY();
            if (evt.isPrimaryButtonDown()) {
               drawShape(evt);
                if (x - 30 < 3)
                    x = 3 + 30;
                if (x + 30 > width - 3)
                    x = width - 33;
                if (y < 3)
                    y = 3;
                if (y > height - 3)
                    y = height - 3;

                prevX = x;
                prevY = y;
                dragging = true;
            }
            else {
                draw();
            }
        });

        canvas.setOnMouseDragged(this::mouseDragged);
        canvas.setOnMouseReleased(this::mouseReleased);

        Pane root = new Pane(canvas);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Draw Shapes!");
        stage.setResizable(false);
        stage.show();
    }

    public void draw() {
        g = canvas.getGraphicsContext2D();
        int width = 500;
        int height = 350;
        g.setFill(Color.rgb(255,253,208));
        g.fillRect(0,0,width,height);
        g.setStroke(Color.GRAY);
        g.setLineWidth(3);
        g.strokeRect(1.5,1.5,width-3,height-3);
    }

    public void mouseDragged(MouseEvent evt) {
        if(evt.isSecondaryButtonDown() || !dragging) {
            return;
        }
        x = evt.getX();
        y = evt.getY();

        if(Math.abs(x - prevX) < 5 && Math.abs(y - prevY) < 5) {
            return;                                               //this 'if' statement was once mixed up with no return statement.
        }                                                         //Corrections have been taken.

        drawShape(evt);
        prevX = x;
        prevY = y;

    }

    public void mouseReleased(MouseEvent evt) {
        dragging = false;
    }

    public void drawShape(MouseEvent evt) {
        g.setLineWidth(2);
        g.setStroke(Color.BLACK);

        if(evt.isShiftDown()) {
            g.strokeOval(x-30, y-15,60,30);
            g.setFill(Color.BLUE);
            g.fillOval(x-30, y-15,60,30);

        }
        else {
            g.strokeRect(x-30, y-15,60,30);
            g.setFill(Color.RED);
            g.fillRect(x-30, y-15,60,30);
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

}