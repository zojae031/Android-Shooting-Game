package framework.gameframework;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class Ready extends Activity implements View.OnClickListener{
    Button btn;

    Intent i;
    TextView tv;
    @Override
    public void onClick(View v) {

        startActivity(i);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ready);






        i = new Intent(this,GameActivity.class);
            btn = findViewById(R.id.start);
            btn.setOnClickListener(this);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        int score  = AppManager.getInstance().getScore();

        tv= findViewById(R.id.textView);
        tv.setText("점수 : "+score);

    }
}
