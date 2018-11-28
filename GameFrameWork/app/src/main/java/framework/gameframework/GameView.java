package framework.gameframework;

import android.content.Context;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import framework.Shooting_Game.GameState;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
    private GameViewThread m_thread;
    private IState m_State;

    public GameView(Context context) {
        super(context);
        setFocusable(true);

        AppManager.getInstance().setGameView(this);
        AppManager.getInstance().setResources(getResources());
        AppManager.getInstance().setContext(context);
        changeGameState(new GameState());
        getHolder().addCallback(this);//SurfaceHolder에 있는 CallBack함수를 View에 등록
        m_thread = new GameViewThread(getHolder(),this);

    }
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        //m_State.Init();
        m_State.Render(canvas);
        Update();
    }
    public void Update(){

        m_State.Update();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        m_State.onKeyDown(keyCode,event);
        return true;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        m_State.onTouchEvent(event);

        return true;
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        m_thread.setRunning(true);
        //Running Thread
        m_thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        m_thread.setRunning(false);

        while(retry){
            try{
                m_thread.join();
                retry=false;
            }
            catch (InterruptedException e){

            }
        }
    }
    public void changeGameState(IState state){
        if(m_State!=null){
            m_State.Destroy();
        }
        state.Init();
        m_State=state;
    }

}
