package framework.Shooting_Game;

import android.graphics.Bitmap;

import framework.gameframework.AppManager;
import framework.gameframework.R;

public class Missile_Player extends Missile {
    public Missile_Player(int x, int y) {
        super(AppManager.getInstance().getBitmap(R.drawable.missile_1));
        this.setPosition(x,y);
        this.setSpeed(15);
    }
    public void Update(){
        this.setY(getY()-getSpeed());
        if(getY()<0)state = STATE_OUT;
        m_BoundBox.left=getX();
        m_BoundBox.top=getY();
        m_BoundBox.right=getX()+m_bitmap.getWidth();
        m_BoundBox.bottom = getY()+m_bitmap.getHeight();
    }
}
