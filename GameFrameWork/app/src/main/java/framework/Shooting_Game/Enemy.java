package framework.Shooting_Game;

import android.graphics.Bitmap;
import android.graphics.Rect;

import framework.gameframework.AppManager;
import framework.gameframework.R;
import framework.gameframework.SpriteAnimation;

public class Enemy extends SpriteAnimation {
    public static final int MOVE_PATTERN_1 =0;
    public static final int MOVE_PATTERN_2 =1;
    public static final int MOVE_PATTERN_3 =2;

    public static final int STATE_NORMAL=0;
    public static final int STATE_OUT=1;
    public int state = STATE_NORMAL;
    protected int moveType;
    protected int hp;
    protected float speed;

    long LastShoot = System.currentTimeMillis();

    Rect m_BoundBox = new Rect();
    @Override
    public void Update(long gameTime) {
        super.Update(gameTime);
        m_BoundBox.set(getX(),getY(),getX()+getBitmapWidth()/6,getY()+getBitmapHeight());
        Attack();
        Move();
    }

    public Enemy(Bitmap bitmap) {
        super(bitmap);
        this.initSpriteData(getBitmapWidth()/6,getBitmapHeight(),10,6);

    }
    void Move(){
        if(getY()>AppManager.getInstance().getDeviceSize().y)state = STATE_OUT;
        switch (moveType){

            case MOVE_PATTERN_1 :

                if(getY()<=AppManager.getInstance().getDeviceSize().y/5){
                    setY(getY()+(int)speed);
                }
                else{
                    setY(getY()+(int)speed*2);
                }
                break;
            case MOVE_PATTERN_2 :
                if(getY()<=AppManager.getInstance().getDeviceSize().y/5){
                    setY(getY()+(int)speed);
                }
                else{
                    setX(getX()+(int)speed);
                    setY(getY()+(int)speed);
                }
                break;
            case MOVE_PATTERN_3 :
                if(getY()<=AppManager.getInstance().getDeviceSize().y/5){
                    setY(getY()+(int)speed);
                }
                else{
                    setX(getX()-(int)speed);
                    setY(getY()+(int)speed);
                }
                break;
        }
    }
    void Attack(){
        if(System.currentTimeMillis()-LastShoot>=1000){
            LastShoot = System.currentTimeMillis();
            AppManager.getInstance().gameState.missile_Enemys.add(new Missile_Enemy(getX()+40,getY()+m_bitmap.getHeight()));        }
    }
    public void setHp(int hp){this.hp=hp;}
    public int getHp (){return hp;}
}
