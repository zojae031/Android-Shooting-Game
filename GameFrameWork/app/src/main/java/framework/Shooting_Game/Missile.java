package framework.Shooting_Game;

import android.graphics.Bitmap;
import android.graphics.Rect;

import framework.gameframework.GraphicObject;

public class Missile extends GraphicObject {
    public static final int STATE_NORMAL=0;
    public static final int STATE_OUT =1;
    public int state =STATE_NORMAL;
    private int Missile_Speed = 20; //미사일 나가는 속도
    private int damage=5;
    Rect m_BoundBox = new Rect();
    public Missile(Bitmap bitmap) {
        super(bitmap);
    }
    public void Update(){
        if(getY()<0){
            state = STATE_OUT;
        }
    }
    public void setSpeed(int missile_Speed){
        this.Missile_Speed = missile_Speed;
    }
    public int getSpeed(){
        return this.Missile_Speed;
    }
    public void setDamage(int damage){this.damage=damage;}
    public int getDamage(){return damage;}
}
