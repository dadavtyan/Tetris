package com.davtyan.tetris;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener {
    private GameSurfaceView gameSurfaceView;
    private FigureState figure;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        gameSurfaceView = findViewById(R.id.game_view);
        figure = new FigureState(gameSurfaceView);
        findViewById(R.id.move_left).setOnClickListener(this);
        findViewById(R.id.move_right).setOnClickListener(this);
        findViewById(R.id.rotate).setOnClickListener(this);
        findViewById(R.id.down).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameSurfaceView.onResumeGameSurfaceView();
    }

    @Override
    public void onPause() {
        super.onPause();
        gameSurfaceView.onPauseGameSurfaceView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.move_left:
                gameSurfaceView.isMoveLeft = true;
                break;
            case R.id.move_right:
                //figure.right();
                gameSurfaceView.isMoveRight = true;
                break;
            case R.id.rotate:
                gameSurfaceView.isRotate = true;
                break;
               case R.id.down:
               gameSurfaceView.sleepData(3);
                break;
        }
    }
}

