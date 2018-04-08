package com.davtyan.tetris;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Figure {
    private List<Coordinate> coordinates;
    private GameSurfaceView view;
    private int idFigure;
    private int rotate = 100;
    private int k;
    private int down;
    private int right = 0;

    private int position = 0;


    public Figure(GameSurfaceView view) {
        this.view = view;
        coordinates = new ArrayList<>();
    }


    private int randomNumber() {
        Random rand = new Random();
        return rand.nextInt(6);
    }


    public List<Coordinate> updateCoordinate() {
        idFigure = 5;
        //randomNumber();
        down = 0;
        coordinates = new ArrayList<>();
        position = 0;
        rotate = 100;


        switch (idFigure) {
            case 1:
                coordinates.add(new Coordinate(300, -100));
                coordinates.add(new Coordinate(400, -100));
                coordinates.add(new Coordinate(300, 0));
                coordinates.add(new Coordinate(400, 0));
                break;
            case 2:
                coordinates.add(new Coordinate(300, -300));
                coordinates.add(new Coordinate(300, -200));
                coordinates.add(new Coordinate(300, -100));
                coordinates.add(new Coordinate(300, 0));
                break;
            case 3:
                coordinates.add(new Coordinate(300, -200));
                coordinates.add(new Coordinate(300, -100));
                coordinates.add(new Coordinate(400, -100));
                coordinates.add(new Coordinate(400, 0));
                break;
            case 4:
                coordinates.add(new Coordinate(300, -200));
                coordinates.add(new Coordinate(400, -200));
                coordinates.add(new Coordinate(400, -100));
                coordinates.add(new Coordinate(400, 0));
                break;
            case 5:
                coordinates.add(new Coordinate(300, -100));
                coordinates.add(new Coordinate(400, -100));
                coordinates.add(new Coordinate(500, -100));
                coordinates.add(new Coordinate(400, 0));
                break;
            case 6:
                coordinates.add(new Coordinate(500, -200));
                coordinates.add(new Coordinate(400, -200));
                coordinates.add(new Coordinate(400, -100));
                coordinates.add(new Coordinate(400, 0));
                break;
        }
        return coordinates;
    }


    public void left() {
        if (checkX()) {
            for (int i = 0; i < view.coordinates.size(); i++) {
                view.coordinates.get(i).setX(view.coordinates.get(i).getX() - 100);
            }
        }
        view.isMoveLeft = false;
        right--;
    }

    public void right() {
        if (checkX()) {
            for (int i = 0; i < view.coordinates.size(); i++) {
                view.coordinates.get(i).setX(view.coordinates.get(i).getX() + 100);
            }
        }
        view.isMoveRight = false;
        right++;
    }

    public void rotate() {
        switch (idFigure) {
            case 2:
                rotateI();
                break;
            case 3:
                rotateS();
                break;
            case 4:
            case 5:
            case 6:
               rotateL();
                break;
        }
        rotate *= -1;
        view.isRotate = false;

    }


    private void rotateI() {
        for (int i = -1; i < 3; i++) {
            view.coordinates.get(i + 1).setX(view.coordinates.get(i + 1).getX() + (i * rotate));
            view.coordinates.get(i + 1).setY(view.coordinates.get(i + 1).getY() - (i * rotate));
        }
    }

    private void rotateS() {
        view.coordinates.get(0).setX(view.coordinates.get(0).getX() + (2 * rotate));
        view.coordinates.get(3).setY(view.coordinates.get(3).getY() - (2 * rotate));
    }

    private void rotateL() {
        int temp2X = view.coordinates.get(2).getX();
        int temp2Y = view.coordinates.get(2).getY();
        for (int i = 0; i < view.coordinates.size(); i++) {

            int tempX = view.coordinates.get(i).getX();
            int tempY = view.coordinates.get(i).getY();
            view.coordinates.get(i).setX(tempY  - temp2Y  + temp2X);
            view.coordinates.get(i).setY(temp2X - tempX  + temp2Y);
        }
    }

    public void fall(int dY) {
        //  if (checkY(count)) {
        for (int i = 0; i < view.coordinates.size(); i++) {
            view.coordinates.get(i).setY(view.coordinates.get(i).getY() + dY);
        }
        //  }
        down++;
    }

    private boolean checkX() {
        for (int i = 0; i < view.coordinates.size(); i++) {
            if (view.coordinates.get(i).getX() > 10 && view.coordinates.get(i).getX() + 90 <= view.getWidth()) {
                return true;
            }
        }
        return false;
    }

    private boolean checkY(int count) {
        for (int i = count; i < coordinates.size(); i++) {
            if (coordinates.get(i).getY() < view.getHeight()) {
                return true;
            }
        }
        return false;
    }

    public void setRotate(int rotate) {
        this.rotate = rotate;
    }
}
