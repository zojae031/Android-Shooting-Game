package framework.Shooting_Game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;
import android.view.WindowManager;

import framework.gameframework.AppManager;
import framework.gameframework.GraphicObject;
import framework.gameframework.R;

public class BackGround extends GraphicObject {
    static final float SCROLL_SPEED = 2f;
    private float m_scroll = -2000 + 480;

    Bitmap m_layer2;

    static final float SCROLL_SPEED2 = 1f;
    private float m_scroll2 = -2000 + 480;


    public BackGround(int backtype) {
        super(null);


        switch (backtype) {
            case 0:
                m_bitmap = AppManager.getInstance().getBitmap(R.drawable.background1);
                m_bitmap = Bitmap.createScaledBitmap(m_bitmap, AppManager.getInstance().getDeviceSize().x, m_bitmap.getHeight(), true);
                break;

            case 1:
                m_bitmap = AppManager.getInstance().getBitmap(R.drawable.background2);
                m_bitmap = Bitmap.createScaledBitmap(m_bitmap, AppManager.getInstance().getDeviceSize().x, m_bitmap.getHeight(), true);
                break;
        }

        m_layer2 = AppManager.getInstance().getBitmap(R.drawable.background_2);
        m_layer2 = Bitmap.createScaledBitmap(m_layer2, AppManager.getInstance().getDeviceSize().x, m_bitmap.getHeight(), true);


        setPosition(0, (int) m_scroll);
    }

    void Update(long GameTime) {
        m_scroll = m_scroll + SCROLL_SPEED;
        m_scroll2 = m_scroll2 + SCROLL_SPEED2;

        if (m_scroll >= 0) m_scroll = -2000+480;
        setPosition(0, (int) m_scroll);

        if (m_scroll2 >= 0) m_scroll2 = -2000+480;

    }

    @Override
    public void Draw(Canvas canvas) {
        canvas.drawBitmap(m_bitmap, getX(), getY(), null);
        canvas.drawBitmap(m_layer2, getX(), m_scroll2, null);

    }
}
