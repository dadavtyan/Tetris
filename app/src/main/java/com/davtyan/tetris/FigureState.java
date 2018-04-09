package com.davtyan.tetris;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FigureState {
    private GameSurfaceView view;
    private int idFigure;
    private int rotate = 100;

    public FigureState(GameSurfaceView view) {
        this.view = view;
    }


    private int randomNumber() {
        Random rand = new Random();
        return rand.nextInt(6);
    }

    /**
     * create new figure
     * @return
     */

    public List<Coordinate> updateCoordinate() {
        List<Coordinate> coordinates = new ArrayList<>();
        idFigure = randomNumber();
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
                coordinates.add(new Coordinate(400, 0));
                coordinates.add(new Coordinate(500, -100));

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


    /**
     * move figure left
     *
     * @param coordinates
     */

    public void left(List<Coordinate> coordinates) {
        if (checkXView(coordinates)) {
            for (int i = 0; i < coordinates.size(); i++) {
                view.coordinates.get(i).setX(coordinates.get(i).getX() - 100);
            }
        }
        view.isMoveLeft = false;
    }

    /**
     * move figure right
     *
     * @param coordinates
     */

    public void right(List<Coordinate> coordinates) {
        if (checkWidthView(coordinates)) {
            for (int i = 0; i < coordinates.size(); i++) {
                coordinates.get(i).setX(coordinates.get(i).getX() + 100);
            }
        }
        view.isMoveRight = false;
    }

    /**
     * rotate figure
     *
     * @param coordinates
     */

    public void rotate(List<Coordinate> coordinates) {
       if (checkWidthView(coordinates) && checkXView(coordinates)){
           switch (idFigure) {
               case 2:
                   rotateI(coordinates);
                   break;
               case 3:
               case 4:
               case 5:
               case 6:
                   rotateL(coordinates);
                   break;
           }
           rotate *= -1;
       }
        view.isRotate = false;

    }


    private void rotateI(List<Coordinate> coordinates) {
        for (int i = -1; i < 3; i++) {
            coordinates.get(i + 1).setX(coordinates.get(i + 1).getX() + (i * rotate));
            coordinates.get(i + 1).setY(coordinates.get(i + 1).getY() - (i * rotate));
        }
    }


    private void rotateL(List<Coordinate> coordinates) {
        int temp2X = coordinates.get(2).getX();
        int temp2Y = coordinates.get(2).getY();
        for (int i = 0; i < view.coordinates.size(); i++) {
            int tempX = coordinates.get(i).getX();
            int tempY = coordinates.get(i).getY();
            coordinates.get(i).setX(tempY - temp2Y + temp2X);
            coordinates.get(i).setY(temp2X - tempX + temp2Y);
        }
    }

    public void move(int dY, List<Coordinate> coordinates) {
        for (int i = 0; i < coordinates.size(); i++) {
            coordinates.get(i).setY(coordinates.get(i).getY() + dY);
        }
    }

    /**
     * fill in the deleted line
     * @param size
     * @param dY
     */

    public void fillDeletedLine(int size, int dY) {
        for (int i = 0; i < view.coordinatesN.size(); i++) {
            if (view.coordinatesN.get(i).getY() < dY) {
                view.coordinatesN.get(i).setY(view.coordinatesN.get(i).getY() + size);
            }
        }
    }

    private boolean checkXView(List<Coordinate> coordinates) {
        boolean check = false;
        for (int i = 0; i < coordinates.size(); i++) {
            if (coordinates.get(i).getX() > 10) {
                check = true;
            }else return false;
        }
        return check;
    }

    private boolean checkWidthView(List<Coordinate> coordinates) {
        boolean check = false;
       for (int i = 0; i < coordinates.size(); i++) {
            if (coordinates.get(i).getX() + 100 < view.getWidth()) {
                check = true;
            }else  return false;
        }
        return check;
    }


    public void setRotate(int rotate) {
        this.rotate = rotate;
    }
}
