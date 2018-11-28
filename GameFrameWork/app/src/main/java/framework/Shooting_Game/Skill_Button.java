package framework.Shooting_Game;

import android.graphics.Bitmap;

import framework.gameframework.AppManager;
import framework.gameframework.R;
import framework.gameframework.SpriteAnimation;

public class Skill_Button extends SpriteAnimation {
    public Skill_Button() {
        super(AppManager.getInstance().getBitmap(R.drawable.item4)); // 스위치 사진을 받아온다.
        this.initSpriteData(getBitmapWidth()/4,getBitmapHeight(),10,1);
        setPosition(AppManager.getInstance().getDeviceSize().x-getBitmapWidth(),0); //특정 위치에 스위치 설정
        replay=false;
    }
    public void setReplay(boolean flag){
        replay = flag;
    } //스킬이 실행되고 있는지 아닌지 확인하는 플레그
    public boolean getReplay(){return replay;}  //반복하는지에 대한 플레그.
}