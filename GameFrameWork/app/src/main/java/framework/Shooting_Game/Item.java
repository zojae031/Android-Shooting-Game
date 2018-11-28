package framework.Shooting_Game;

import android.graphics.Bitmap;
import android.graphics.Rect;

import framework.gameframework.AppManager;
import framework.gameframework.SpriteAnimation;
public class Item extends SpriteAnimation{
    public static final int STATE_NORMAL= 0;//★★★★
    public static final int STATE_OUT= 1;//★★★★
    public int state= STATE_NORMAL;//★★★★

    public static final int ITEM_TYPE1=0;
    public static final int ITEM_TYPE2=1;
    public static final int ITEM_TYPE3=2;

    protected int moveType;
    protected float speed;//★★★★
    Rect m_BoundBox = new Rect();

    public Item(Bitmap bitmap)
    {
        super(bitmap);
    }

    void Move(){
        if(getY()>AppManager.getInstance().getDeviceSize().y)state = STATE_OUT;
        setY(getY()+(int)speed);
    }



    @Override
    public void Update(long gameTime) {//★★★★
        super.Update(gameTime);
        Move(); //Move를 실행시키므로써 위치m_x,m_y값을 업뎃.
        m_BoundBox.set(getX(),getY(),getX()+getBitmapWidth()/4,getY()+getBitmapHeight());
    }
    public void useItem(Player p){};
}