package raj.saraogi.vocabularymastervm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

import raj.saraogi.vocabularymastervm.Holder.Word;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
public class NameList extends AppCompatActivity {
        private RecyclerView recyclerView;
        private RecyclerView.LayoutManager layoutManager;
        private RecyclerView.Adapter adapter;
        List<Word> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_list);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-5093664666925857~6145591528");
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        Bundle bundle = new Bundle();
        bundle= getIntent().getExtras();

        list= (List<Word>) bundle.getSerializable("list");
      //  toolbar.setTitle("Words List");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        getActionBar().setDisplayShowHomeEnabled(true);
        if(list.isEmpty()){
            Toast.makeText(this,"Please connect to internet and reopen app",Toast.LENGTH_LONG).show();
        }
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new WordNameAdapter(this,list);
        recyclerView.setAdapter(adapter);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mAdView.loadAd(request);
    }
}
