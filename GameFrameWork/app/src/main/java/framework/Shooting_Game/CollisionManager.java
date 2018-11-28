package framework.Shooting_Game;

import android.graphics.Rect;
import android.util.Log;
import android.widget.Toast;

import framework.gameframework.AppManager;

public class CollisionManager {
    public static boolean CheckBoxToBox(Rect _rt1,Rect _rt2){
        if(_rt1.intersect(_rt2)){
            return true;
        }
        return false;
    }
}
