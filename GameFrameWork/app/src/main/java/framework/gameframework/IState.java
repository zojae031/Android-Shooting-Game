package framework.gameframework;

import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

public interface IState {
    public void Init();
    public void Destroy();
    public void Update();
    public void Render(Canvas canves);
    public boolean onKeyDown(int keyCode, KeyEvent event);
    public boolean onTouchEvent(MotionEvent evnet);
}
