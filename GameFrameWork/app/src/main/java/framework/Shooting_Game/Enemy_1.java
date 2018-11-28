package framework.Shooting_Game;

import android.graphics.Bitmap;

import framework.gameframework.AppManager;
import framework.gameframework.R;

public class Enemy_1 extends Enemy {

    public Enemy_1() {
        super(AppManager.getInstance().getBitmap(R.drawable.enemy1));
        hp=10;
        speed=1.5f;

    }

    @Override
    public void Update(long gameTime) {
        super.Update(gameTime);


    }
}
