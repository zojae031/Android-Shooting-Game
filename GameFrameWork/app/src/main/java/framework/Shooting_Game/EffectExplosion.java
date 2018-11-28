package framework.Shooting_Game;

import android.graphics.Bitmap;

import framework.gameframework.AppManager;
import framework.gameframework.R;
import framework.gameframework.SpriteAnimation;

public class EffectExplosion extends SpriteAnimation {
    public EffectExplosion(int x, int y) {
        super(AppManager.getInstance().getBitmap(R.drawable.explosion));
        this.initSpriteData(getBitmapWidth()/6,getBitmapHeight(),10,6);
        setX(x);
        setY(y);
        replay=false;
    }
}
