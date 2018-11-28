package framework.gameframework;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class GraphicObject {
    protected Bitmap m_bitmap;
    private int m_x;
    private int m_y;
    public GraphicObject(Bitmap bitmap) {
        m_bitmap=bitmap;
        m_x=0;
        m_y =0;
    }
    public void setPosition(int x,int y){
        m_x= x;
        m_y=y;
    }
    public void Draw(Canvas canvas){
        canvas.drawBitmap(m_bitmap,m_x,m_y,null);
    }
    public int getX(){return m_x;}
    public int getY(){return m_y;}
    public void setX(int x){this.m_x=x;}
    public void setY(int y){this.m_y=y;}

}
