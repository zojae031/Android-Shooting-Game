package framework.Shooting_Game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import framework.gameframework.AppManager;
import framework.gameframework.SpriteAnimation;

public class Player extends SpriteAnimation {
    public static final int LIVE=0;
    public static final int DEAD=1;

    Rect m_BoundBox = new Rect();
    int state = LIVE;
    int m_Life =3;
    int skills=3;
    private int PlayerShootSpeed=200; //발사 빈도
    @Override
    public void Draw(Canvas canvas) {
        super.Draw(canvas);
    }

    @Override
    public void Update(long gameTime) {
        super.Update(gameTime);
        m_BoundBox.set(getX(),getY(),getX()+getBitmapWidth()/6-70,getY()+getBitmapHeight()/2);
    }

    public Player(Bitmap bitmap) {
        super(bitmap);
        super.initSpriteData(getBitmapWidth()/6,getBitmapHeight(),10,6);
        super.setPosition(AppManager.getInstance().getDeviceSize().x/2, AppManager.getInstance().getDeviceSize().y-getBitmapHeight());
    }
    public int getLife(){return m_Life;}
    public void addLife(){m_Life++;}
    public  void destroyPlayer(){
        m_Life--;
        AppManager.getInstance().vibrate(1000);
    }
    public void setSkill(int skills){
        this.skills= skills;
    }
    public int getSkill(){
        return skills;
    }
    public void setShootSpeed(int speed){
        PlayerShootSpeed = speed;
    }
    public int getShootSpeed(){
        return PlayerShootSpeed;
    }
    public void SetItem(Item item){//★★★★
        item.useItem(this);
    }
}
