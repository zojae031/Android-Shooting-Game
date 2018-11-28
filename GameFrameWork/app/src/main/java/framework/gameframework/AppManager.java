package framework.gameframework;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Vibrator;
import android.view.Display;
import android.view.WindowManager;

import java.util.HashMap;

import framework.Shooting_Game.GameState;

public class AppManager {

    private static AppManager s_instance;
    private GameView m_gameView;
    private Resources m_resources;
    private Context context;
    private Display display;
    private Point size;
    public GameState gameState;
    private Vibrator vibrator;
    private int score;
    private static Activity mainAct;
    //SingleTon Pattern
    private AppManager() {

    }



    public static AppManager getInstance() {
        if (s_instance == null){
            s_instance = new AppManager();

        }
        return s_instance;
    }

    //Get, Set Method

    public void setGameView(GameView _gameView) {
        m_gameView = _gameView;
    }

    public void setResources(Resources _resources) {
        m_resources = _resources;
    }

    public GameView getGameView() {
        return m_gameView;
    }

    public Resources getResources() {
        return m_resources;
    }

    public Bitmap getBitmap(int r) {
        return BitmapFactory.decodeResource(m_resources, r);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public Point getDeviceSize() {
        if (size == null) { //첫실행 한번만 디바이스 사이즈를 구해오기 위함
            display = ((WindowManager) AppManager.getInstance().getContext().getSystemService(AppManager.getInstance().getContext().WINDOW_SERVICE)).getDefaultDisplay();
            size = new Point();
            display.getSize(size);
        }

        return size;
    }


    public void vibrate(long milliseconds) {
        if (vibrator == null)
            vibrator = (Vibrator) AppManager.getInstance().getContext().getSystemService(Context.VIBRATOR_SERVICE);

        vibrator.vibrate(milliseconds);
    }

    public int getScore(){
        return score;
    }
    public void setScore(int score){
        this.score = score;
    }
    public void setMainAct(Activity act){mainAct = act;}
    public Activity getMainAct(){return mainAct;}
}
