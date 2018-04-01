package com.davtyan.tetris;


import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Figure {
    private List<Coordinate> coordinates;
    private GameSurfaceView view;


    public Figure(GameSurfaceView view) {
        this.view = view;
        coordinates = new ArrayList<>();
    }


    private int randomNumber() {
        Random rand = new Random();
        return rand.nextInt(5);
    }


    public List<Coordinate> updateCoordinate() {
        int newCoordinate = randomNumber();
        coordinates = new ArrayList<>();
        switch (newCoordinate) {
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
        }
        return coordinates;
    }

    public void left(int count) {
        if (checkX(count)) {
            for (int i = count; i < view.coordinates.size(); i++) {
                view.coordinates.get(i).setX(view.coordinates.get(i).getX() - 100);
            }
        }
        view.isMoveLeft = false;
    }

    public void right(int count) {
        if (checkX(count)) {
            for (int i = count; i < view.coordinates.size(); i++) {
                view.coordinates.get(i).setX(view.coordinates.get(i).getX() + 100);
            }
        }
        view.isMoveRight = false;
    }

    public void fall(int dY,int count) {
      //  if (checkY(count)) {
            for (int i = count; i < view.coordinates.size(); i++) {
                view.coordinates.get(i).setY(view.coordinates.get(i).getY() + dY);
            }
      //  }
    }

    private boolean checkX(int count) {
        for (int i = count; i < view.coordinates.size(); i++) {
            if (view.coordinates.get(i).getX() > 0 && view.coordinates.get(i).getX() + 95 < view.getWidth()) {
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


    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }
}
