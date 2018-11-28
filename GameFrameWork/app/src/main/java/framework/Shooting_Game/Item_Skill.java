package framework.Shooting_Game;

import framework.gameframework.AppManager;
import framework.gameframework.R;

public class Item_Skill extends Item {


    public Item_Skill(){//★★★★
        super(AppManager.getInstance().getBitmap(R.drawable.item3));
        this.initSpriteData(m_bitmap.getWidth()/4,m_bitmap.getHeight(),2,4);
        speed= 15f;
    }
    @Override
    public void Update(long GameTime){
        super.Update(GameTime);
    }

    @Override
    public void useItem(Player p) {
        super.useItem(p);
        p.setSkill(p.getSkill()+1);
    }
}