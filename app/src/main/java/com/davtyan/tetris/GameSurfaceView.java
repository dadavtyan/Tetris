package com.davtyan.tetris;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

public class GameSurfaceView extends SurfaceView implements Runnable {
    private boolean running;
    private Thread thread;
    private Figure figure;
    private SurfaceHolder surfaceHolder;
    private Paint paint;
    public List<Coordinate> coordinates;
    public List<Coordinate> coordinatesN;
    private int size = 95;
    private Paint backgroundPaint;
    public boolean isMoveRight, isMoveLeft,isRotate;
    private int sleepData = 1000;
    private int lineCount;
    private int[] color =  {Color.RED,Color.BLUE,Color.BLACK,Color.CYAN};

    public GameSurfaceView(Context c, AttributeSet attrs) {
        this(c, attrs, 0);
    }

    public GameSurfaceView(Context c, AttributeSet attrs, int defStyle) {
        super(c, attrs, defStyle);
        figure = new Figure(this);
        surfaceHolder = getHolder();
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.WHITE);
        paint = new Paint();
        paint.setColor(Color.RED);
        coordinates = figure.updateCoordinate();
        coordinatesN = new ArrayList<>();

    }

    public void onResumeGameSurfaceView() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void onPauseGameSurfaceView() {
        boolean retry = true;
        running = false;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        while (running) {
            if (surfaceHolder.getSurface().isValid()) {
                Canvas canvas = surfaceHolder.lockCanvas();
                int width = canvas.getWidth();
                int height = canvas.getHeight();
                int dY = size + 5 ;


                float lineY = 15 * dY + size;
                canvas.drawRect(0, 0, width, height, backgroundPaint);
                for (int i = 0; i < 1600; i+=100) {
                    canvas.drawLine(0, lineY- i, width, lineY - i, paint);
                    canvas.drawLine(i,  0, i,  lineY, paint);
                }


                drawRect(coordinates, canvas);
                if (coordinatesN.size() > 0 && coordinatesN != null) {
                    drawRect(coordinatesN, canvas);
                }

                if (coordinates.size() < 1){
                    coordinates = figure.updateCoordinate();
                }

                if (coordinates.size() > 0){
                    for (int i = 0; i < coordinates.size(); i++) {
                        if ((coordinates.get(i).getY()) >= 15 * dY) {
                            createFigure();
                            break;
                        }

                        for (int j = 0; j < coordinatesN.size(); j++) {
                            if (coordinates.get(i).getY() + 100 >= coordinatesN.get(j).getY()
                                    && coordinates.get(i).getX() == coordinatesN.get(j).getX()) {
                                createFigure();
                                break;
                            }
                        }
                    }
                }

                surfaceHolder.unlockCanvasAndPost(canvas);
                try {
                    Thread.sleep(sleepData);
                    if (isMoveLeft) figure.left();
                    if (isMoveRight) figure.right();
                    if (isRotate) figure.rotate();
                    figure.fall(dY);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }
    }

    private void drawRect(List<Coordinate> coordinateList, Canvas canvas) {
        for (int i = 0; i < coordinateList.size(); i++) {
            paint.setColor(color[i]);
            canvas.drawRect(
                    coordinateList.get(i).getX(),
                    coordinateList.get(i).getY(),
                    coordinateList.get(i).getX() + size,
                    coordinateList.get(i).getY() + size,
                    paint);
        }
    }

    private void createFigure() {
        sleepData = 1000;
        coordinatesN.addAll(coordinates);
        coordinates = figure.updateCoordinate();
        figure.setRotate(100);

//        for (int i = 0; i < coordinates.size(); i++) {
//            clearLineList.get(coordinates.get(i).getX()/100)[i]
//
//        }

        for (int i = 15; i > 7; i--) {
            for (int j = 0; j < coordinatesN.size(); j++) {
              //  Log.i("lineCount","lineCount = " + coordinatesN.get(j).getY());
                if (i * 100 == coordinatesN.get(j).getY() ){
                    lineCount++;

                }


                Log.i("lineCount","lineCount = " + lineCount);
                if (lineCount >= 11){
                    break;
                }

            }
            if (lineCount < 11) lineCount = 0;
        }

        if (lineCount >= 11) {
            for (int i = coordinatesN.size() - 11; i < coordinatesN.size(); i++) {
                coordinatesN.remove(i);
            }
            lineCount = 0;
        }
    }


    public void sleepData(int data) {
        sleepData = data;
    }
}
