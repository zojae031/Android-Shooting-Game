package framework.Shooting_Game;

import android.graphics.Bitmap;
import android.graphics.Rect;

import framework.gameframework.AppManager;
import framework.gameframework.R;
import framework.gameframework.SpriteAnimation;



//스킬 클래스
class Skill extends SpriteAnimation {
    public static final int STATE_NOMAL = 0;
    public static final int STATE_OUT = 1;
    protected float speed=5f;
    public int state = STATE_NOMAL;
    public long LastShoot = System.currentTimeMillis();

    //스킬이 닿는 범위를 나타내는 박스 생성
    Rect m_BoundBox = new Rect();


    public Skill(Bitmap bitmap) {
        super(null);
        m_bitmap = AppManager.getInstance().getBitmap(R.drawable.skill);
        m_bitmap = Bitmap.createScaledBitmap(m_bitmap, AppManager.getInstance().getDeviceSize().x, m_bitmap.getHeight(), true);
        this.initSpriteData(getBitmapWidth()/6,getBitmapHeight(),10,6);
    }

    @Override //스킬의 위치 정보를 계속 업데이트
    public void Update(long GameTime) {
        super.Update(GameTime);
        m_BoundBox.set(getX(), getY(), getX()+getBitmapWidth()/6, getY()+getBitmapHeight());
        move();
    }

    void move() { //0을 기준으로 만약 y의 좌표가 받아온 그림의 높이보다  높아지는 경우 -> 맵밖으로 완전히 나가는 경우 스테이트 아웃을 시킨다
        if (getY()== -getBitmapHeight()) {
            state = STATE_OUT;
        }
        setY(getY()-(int)speed);
    }

}