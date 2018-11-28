package framework.Shooting_Game;

import framework.gameframework.AppManager;
import framework.gameframework.R;

public class Enemy_2 extends Enemy {
    public Enemy_2() {
        super(AppManager.getInstance().getBitmap(R.drawable.enemy2));
        hp=35;
        speed=2f;

    }
    @Override
    public void Update(long gameTime) {
        super.Update(gameTime);

    }
}
