package framework.Shooting_Game;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import framework.gameframework.AppManager;
import framework.gameframework.IState;
import framework.gameframework.R;
import framework.gameframework.SoundManager;

public class GameState implements IState {

    private Player m_player;
    private BackGround m_background;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    public ArrayList<Missile_Player> missile_players = new ArrayList<>();
    public ArrayList<Missile_Enemy> missile_Enemys = new ArrayList<>();
    public ArrayList<EffectExplosion> effectExplosions = new ArrayList<>();
    public Skill_Button skill_button = new Skill_Button();
    //Item
    ArrayList<Item> m_itemlist = new ArrayList<Item>( );            //★★★★
    //skill
    ArrayList<Skill_1> m_skilist = new ArrayList<Skill_1>(3);

    long LastRegenEnemy = System.currentTimeMillis();
    long LastShootMissile = System.currentTimeMillis();
    long LastRegenitem= System.currentTimeMillis( );            //★★★★

    private int score = 0;

    private SoundManager soundManager;
    private MediaPlayer mediaPlayer;

    Random randEnem = new Random();
    Random randItem= new Random( );

    public GameState() {
        AppManager.getInstance().gameState = this;

    }

    @Override
    public void Init() {
        m_background = new BackGround(0);
        m_player = new Player(AppManager.getInstance().getBitmap(R.drawable.player));


        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(AppManager.getInstance().getContext(), R.raw.background);
        mediaPlayer.start();

        soundManager = SoundManager.getInstance();
        soundManager.Init(AppManager.getInstance().getContext());
        soundManager.addSound(0, R.raw.effect1);
        soundManager.addSound(1, R.raw.effect2);
        soundManager.addSound(2, R.raw.effect3);


    }

    @Override
    public void Destroy() {
        long gameTime = System.currentTimeMillis();
        // 필살기
        for (int i = m_skilist.size() - 1; i >= 0; i--) {
            Skill_1 sk = m_skilist.get(i);
            sk.Update(gameTime);
            if (sk.state == Skill.STATE_OUT) {
                m_skilist.remove(i);
                skill_button.setReplay(false);
            }
        }
        //플레이어의 상태가 DEAD로 바뀌면 호출
        if (m_player.state == Player.DEAD) {
            mediaPlayer.stop();
            AppManager.getInstance().setScore(score);
            AppManager.getInstance().getMainAct().finish();

        }

    }

    @Override
    public void Update() {
        long gameTime = System.currentTimeMillis();
        m_background.Update(gameTime);
        m_player.Update(gameTime);

        //적군 그리기
        for (Enemy enemy : enemies) {
            enemy.Update(System.currentTimeMillis());
        }
        //필살기 업데이트
        for (Skill_1 skill : m_skilist) {
            skill.Update(gameTime);
        }
        //새로운 플레이어 미사일 그리기 (시간설정)
        if (System.currentTimeMillis() - LastShootMissile >= m_player.getShootSpeed()) {
            LastShootMissile = System.currentTimeMillis();
            missile_players.add(new Missile_Player(m_player.getX() + m_player.getBitmapWidth() / 6 / 6, m_player.getY()));
        }

        //기존 플레이어 미사일 그리기 (상태체크)
        for (int i = missile_players.size() - 1; i >= 0; i--) {
            Missile_Player pms = missile_players.get(i);
            pms.Update();
            if (pms.state == Missile.STATE_OUT) missile_players.remove(i);
        }

        //적군 그리기 (상태체크)
        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemy enemy = enemies.get(i);
            enemy.Update(gameTime);
            if (enemy.state == Enemy.STATE_OUT) enemies.remove(i);
        }
        //적군 미사일
        for (int i = missile_Enemys.size() - 1; i >= 0; i--) {
            Missile_Enemy enemms = missile_Enemys.get(i);
            enemms.Update();
            if (enemms.state == Missile.STATE_OUT) missile_Enemys.remove(i);
        }
        //폭발 효과
        for (int i = effectExplosions.size() - 1; i >= 0; i--) {
            EffectExplosion exp = effectExplosions.get(i);
            exp.Update(gameTime);
            if (exp.getAnimationEnd()) effectExplosions.remove(i);
        }

        //아이템
        for( int i=m_itemlist.size( )-1; i>= 0; i--) {                      //★★★★
            Item item= m_itemlist.get(i);
            item.Update(gameTime);
            if(item.state== item. STATE_OUT) m_itemlist.remove( i);
        }

        //스킬 버튼
        if(skill_button.getReplay()) {//replay가 true면 실행
            skill_button.Update(gameTime);
        }
        MakeEnemy();
        MakeItem();
        Destroy();
        CheckCollision();
    }

    @Override
    public void Render(Canvas canvas) {
        //배경그리기
        m_background.Draw(canvas);
        //스킬 그리기
        for (Skill_1 skill : m_skilist) {
            skill.Draw(canvas);
        }
        //플레이어 미사일 그리기
        for (Missile_Player pms : missile_players) pms.Draw(canvas);

        //적군 미사일 그리기
        for (Missile_Enemy enemms : missile_Enemys) enemms.Draw(canvas);

        //적군 그리기
        for (Enemy enemy : enemies) enemy.Draw(canvas);

        //미사일 이펙트 그리기
        for (EffectExplosion exp : effectExplosions) exp.Draw(canvas);

        //아이템 그리기
        for(Item            item: m_itemlist)item.Draw(canvas);             //★★★★

        //플레이어 그리기
        m_player.Draw(canvas);


        //버튼 그리기
        if(!skill_button.getReplay()) { //replay가 false면 그냥 그리기
            skill_button.Draw(canvas);
        }
        //현재 가진 궁극기 개수 표시
        Paint p1 = new Paint();
        p1.setTextSize(50);
        p1.setColor(Color.BLACK);
        canvas.drawText("궁극기 개수 : " + String.valueOf(m_player.getSkill()), 0, AppManager.getInstance().getDeviceSize().y, p1);

        //생명력 표시
        Paint p = new Paint();
        p.setTextSize(50);
        p.setColor(Color.BLACK);
        canvas.drawText("남은 목숨 : " + String.valueOf(m_player.getLife()), 10, 50, p);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //궁극기를 그려줄 RECT정보를 담은 변수
        Rect r = new Rect(AppManager.getInstance().getDeviceSize().x - skill_button.getBitmapWidth(), 0, AppManager.getInstance().getDeviceSize().y, skill_button.getBitmapHeight());

        if((event.getAction()==MotionEvent.ACTION_DOWN)) { //이거 없으면 한번에 스킬 3번 다써짐
            if (r.contains((int) event.getX(), (int) event.getY())) {
                if (m_player.getSkill() > 0 && skill_button.getReplay()==false) {
                    soundManager.play(SoundManager.SOUND_EFFECT_3);
                    m_player.setSkill(m_player.getSkill() - 1);
                    for(int i=0 ; i<7 ; i++) {
                        m_skilist.add(new Skill_1(i*m_player.getBitmapWidth()/6, AppManager.getInstance().getDeviceSize().y));
                    }
                    skill_button.setReplay(true);
                }
            }
        }
        if (event.getX() > m_player.getX())
            m_player.setPosition(m_player.getX() + 20, m_player.getY());
        if (event.getX() < m_player.getX())
            m_player.setPosition(m_player.getX() - 20, m_player.getY());

        return true;
    }

    public void MakeEnemy() {
        if (System.currentTimeMillis() - LastRegenEnemy >= 1000) {
            LastRegenEnemy = System.currentTimeMillis();
            int enemyType = randEnem.nextInt(3);
            Enemy enemy = null;
            switch (enemyType) {
                case 0:
                    enemy = new Enemy_1();
                    break;
                case 1:
                    enemy = new Enemy_2();
                    break;
                case 2:
                    enemy = new Enemy_3();
                    break;
            }
            //아래 두개는 랜덤생성 필요
            enemy.setPosition(randEnem.nextInt(AppManager.getInstance().getDeviceSize().x), -60);
            enemy.moveType = randEnem.nextInt(3);
            enemies.add(enemy);
        }
    }

    public void CheckCollision() {
        //미사일과 적의 충돌 체크
        for (int i = missile_players.size() - 1; i >= 0; i--) {
            for (int j = enemies.size() - 1; j >= 0; j--) {
                if (CollisionManager.CheckBoxToBox(missile_players.get(i).m_BoundBox, enemies.get(j).m_BoundBox)) {
                    soundManager.play(randEnem.nextInt(2));
                    missile_players.remove(i);
                    if (enemies.get(j).getHp() > 0) {
                        enemies.get(j).setHp(enemies.get(j).getHp() - missile_players.get(i).getDamage());

                    } else {
                        effectExplosions.add(new EffectExplosion(enemies.get(j).getX(), enemies.get(j).getY()));
                        enemies.remove(j);
                        score++;
                        return;
                    }

                }
            }
        }
        //플레이어와 적의 충돌 체크
        for (int i = enemies.size() - 1; i >= 0; i--) {
            if (CollisionManager.CheckBoxToBox(enemies.get(i).m_BoundBox, m_player.m_BoundBox)) {
                effectExplosions.add(new EffectExplosion(enemies.get(i).getX(), enemies.get(i).getY()));
                m_player.destroyPlayer();
                enemies.remove(i);

                if (m_player.getLife() <= 0) {
                    m_player.state = Player.DEAD;
                }
                return;
            }
        }
        //플레이어와 미사일 충돌 체크
        for (int i = missile_Enemys.size() - 1; i >= 0; i--) {
            if (CollisionManager.CheckBoxToBox(m_player.m_BoundBox, missile_Enemys.get(i).m_BoundBox)) {
                missile_Enemys.remove(i);
                m_player.destroyPlayer();

                if (m_player.getLife() <= 0) {
                    m_player.state = Player.DEAD;
                }
            }
        }

        //아이템 충돌 체크
        for( int i = m_itemlist.size( )-1; i >= 0; i--) { //플레이어가 아이템 획득 //★★★★
            if(CollisionManager.CheckBoxToBox(

                    m_player.m_BoundBox,
                    m_itemlist.get(i). m_BoundBox)) {
                m_player.SetItem(m_itemlist.get(i));//아이템배열안의 i-th 멤버하나를 Player객체에 전달한다.
                m_itemlist.remove(i);               //아이템을 배열에서 제거한다.

                return; // 일단루프에서빠져나옴
            }
        }


        //필살기 충돌체크
        for (int a = m_skilist.size() - 1; a >= 0; a--) {
            for (int b = missile_Enemys.size() - 1; b >= 0; b--) {
                if (CollisionManager.CheckBoxToBox(m_skilist.get(a).m_BoundBox, missile_Enemys.get(b).m_BoundBox)) {
                    missile_Enemys.remove(b);
                }
            }
            for (int c = enemies.size() - 1; c >= 0; c--) {
                if (CollisionManager.CheckBoxToBox(m_skilist.get(a).m_BoundBox, enemies.get(c).m_BoundBox)) {
                    effectExplosions.add(new EffectExplosion(enemies.get(c).getX(), enemies.get(c).getY()));
                    enemies.remove(c);
                    score++;
                }
            }
        }
    }
    public void MakeItem( ) {                                                                       //★★★★
        if(System.currentTimeMillis( )-LastRegenitem>= 5000 ) { //5초간격으로 생성하는 알고리즘: LastRegenEnemy는 시간을 담는 그릇일 뿐...
            //LastRegenEnemy는 밑에 코드를 실행하지 않는 이상 변하지 않고 위의 System.currentTimeMills는 계속 증가한다.
            LastRegenitem= System.currentTimeMillis( );

            Item item= null; //1개의 itme의 정보를 담을 참조변수(그릇)생성
            int itemtype= randItem.nextInt(3); //종류를 결정
            switch (itemtype){
                case Item.ITEM_TYPE1: item= new Item_Speed(); break;
                case Item.ITEM_TYPE2: item = new Item_Hp();break;
                case Item.ITEM_TYPE3: item = new Item_Skill();break;
            }


            item.moveType= randItem.nextInt(3);//item의 행동패턴을 결정
            item.setPosition(randItem.nextInt(AppManager.getInstance().getDeviceSize().x), -60);//적군의 초기 위치를 결정 &&  <x축의 싸이즈 수정하기>

            m_itemlist.add(item); // 개성이 부여된 적군을 적군무리리스트에 등록
        }
    }
}
