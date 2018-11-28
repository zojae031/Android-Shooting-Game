package framework.Shooting_Game;

import framework.gameframework.AppManager;
import framework.gameframework.R;

public class Enemy_3 extends Enemy {
    public Enemy_3() {
        super(AppManager.getInstance().getBitmap(R.drawable.enemy3));
        hp=50;
        speed=2.5f;

    }
    @Override
    public void Update(long gameTime) {
        super.Update(gameTime);

    }
}
