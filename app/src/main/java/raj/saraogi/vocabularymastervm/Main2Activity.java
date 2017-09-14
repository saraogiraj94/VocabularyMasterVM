package raj.saraogi.vocabularymastervm;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import raj.saraogi.vocabularymastervm.Holder.Word;

public class Main2Activity extends AppCompatActivity {
    String [] name={"Raj","Amit","sharma"};
    ViewPager viewPager;
    List<Word> arrayList=new ArrayList<>();
    int pos;
    private PagerAdapter2 p;
    private RequestQueue requestQueue;
 //   ArrayList<Word    > arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Bundle bundle = getIntent().getExtras();
        pos = bundle.getInt("pos");
        arrayList= (List<Word>) bundle.getSerializable("list");
        //onBackPressed();
        //    arrayList = new ArrayList<>();
        //      requestQueue = Volley.newRequestQueue(this);
//        getData();


        viewPager = (ViewPager) findViewById(R.id.pager);
        p=new PagerAdapter2(getSupportFragmentManager());
        viewPager.setAdapter(p);
        viewPager.setCurrentItem(pos);

        //Creating our pager adapter
       /* if (Config.arrayList.isEmpty()) {
            Pager adapter = new Pager(getSupportFragmentManager(), 1, pos);
            viewPager.setAdapter(adapter);
        } else {


            Pager adapter = new Pager(getSupportFragmentManager(), Config.arrayList.size(), pos);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(pos);

        }*/


    }
    private class PagerAdapter2 extends FragmentStatePagerAdapter {
        public PagerAdapter2(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            Bundle bundle =new Bundle();
            bundle.putInt("pos",pos);
            bundle.putSerializable("list",(Serializable) arrayList);
            BlankFragment blankFragment = new BlankFragment();
            blankFragment.setArguments(bundle);
            return blankFragment;
            //return SlidePageFragment.newInstance(pos);
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }
    }
}

