package framework.Shooting_Game;


import framework.gameframework.AppManager;
import framework.gameframework.R;
public class Skill_1 extends Skill {


    public Skill_1(int x, int y) {
        super(null);
        this.setPosition(x,y);//스킬의 시작 위치 설정
        speed = 1.5f;
    }

    public void Update(long GameTime){
        super.Update(GameTime);
        move(); //움직임에 대한 값을 업데이트.

    }


}