package raj.saraogi.vocabularymastervm.MyDictionary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import raj.saraogi.vocabularymastervm.Database.DatabaseHelper;
import raj.saraogi.vocabularymastervm.Holder.Word;
import raj.saraogi.vocabularymastervm.R;

public class MyDWordDetail extends AppCompatActivity {
    TextView w,d,s,e;
    ImageButton edit;
    int pos;
    List<Word> list =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dword_detail);
        w=(TextView)findViewById(R.id.text_w);
        d=(TextView)findViewById(R.id.text_d);
        s=(TextView)findViewById(R.id.text_s);
        e=(TextView)findViewById(R.id.text_e);
        edit=(ImageButton)findViewById(R.id.edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar5);
        //  toolbar.setTitle("Words List");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle bundle= getIntent().getExtras();
        pos= bundle.getInt("pos");
        list= (List<Word>) bundle.getSerializable("list");

        w.setText(list.get(pos).getWname());
        d.setText(list.get(pos).getDesc());
        e.setText(list.get(pos).getExample());
        s.setText(list.get(pos).getSynonyms());
    }

    public void update(View v){
        Bundle bundle = new Bundle();
        bundle.putString("word",w.getText().toString());
        bundle.putString("desc",d.getText().toString());
        bundle.putString("syn",s.getText().toString());
        bundle.putString("ex",e.getText().toString());
        bundle.putInt("pos",pos);
        startActivity(new Intent(this,EditWord.class).putExtras(bundle));
        finish();

    }
}
