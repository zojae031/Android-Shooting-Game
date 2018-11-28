package framework.gameframework;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameViewThread extends Thread{
    //접근을 위한 멤버 변수
    private SurfaceHolder m_surfaceHolder;
    private GameView m_gameView;
    //스레드 실행 상태 멤버 변수
    private boolean m_run = false;

    public GameViewThread(SurfaceHolder surfaceHolder,GameView gameView) {
        m_surfaceHolder = surfaceHolder;
        m_gameView = gameView;
    }
    public void setRunning(boolean run){
        m_run = run;
    }

    @Override
    public void run() {
        Canvas _canvas;
        while (m_run){
            _canvas=null;

            try{
                m_gameView.Update();
                _canvas = m_surfaceHolder.lockCanvas(null);
                synchronized (m_surfaceHolder){
                    m_gameView.onDraw(_canvas);
                }
            }catch (Exception e){

            }finally {
                if(_canvas!=null) //캔버스가 널이 아니면
                    m_surfaceHolder.unlockCanvasAndPost(_canvas);
                //Canvas를 계속 포스팅을 하여라
                //깨진 화면이라도 그려라
            }
        }
    }
}
