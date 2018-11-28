package framework.Shooting_Game;

import android.graphics.Bitmap;

import framework.gameframework.AppManager;
import framework.gameframework.R;

public class Missile_Enemy extends Missile {
    public Missile_Enemy(int x, int y) {
        super(AppManager.getInstance().getBitmap(R.drawable.missile_2));
        this.setPosition(x,y);
        this.setSpeed(10);
    }

    @Override
    public void Update() {

        setY(getY()+getSpeed());
        if (getY()>AppManager.getInstance().getDeviceSize().y)state = STATE_OUT;
        m_BoundBox.set(getX()+m_bitmap.getWidth()/5,getY(),getX()+m_bitmap.getWidth()/8,getY()+m_bitmap.getHeight()/5);
    }
}
