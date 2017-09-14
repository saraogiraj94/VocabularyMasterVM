package raj.saraogi.vocabularymastervm;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import raj.saraogi.vocabularymastervm.Database.DatabaseHelper;
import raj.saraogi.vocabularymastervm.Holder.Word;

public class StarWordDetail extends AppCompatActivity {
    ViewPager viewPager;
    List<Word> arrayList=new ArrayList<>();
    int pos;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_word_detail);
        databaseHelper=new DatabaseHelper(this);
        arrayList=databaseHelper.getStarWordsList();
        Bundle bundle= getIntent().getExtras();
        int size= bundle.getInt("size");
        int pos= bundle.getInt("pos");
        //onBackPressed();
        //    arrayList = new ArrayList<>();
        //      requestQueue = Volley.newRequestQueue(this);
//        getData();


        viewPager = (ViewPager) findViewById(R.id.pager2);

        //Creating our pager adapter
        StarPager adapter = new StarPager(getSupportFragmentManager(),size, pos,arrayList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(pos);
    }

}
