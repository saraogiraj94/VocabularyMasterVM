package raj.saraogi.vocabularymastervm.MyDictionary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import raj.saraogi.vocabularymastervm.Database.DatabaseHelper;
import raj.saraogi.vocabularymastervm.Holder.Word;
import raj.saraogi.vocabularymastervm.R;
import raj.saraogi.vocabularymastervm.WordNameAdapter;

public class MyDList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    List<Word> list = new ArrayList<>();
    DatabaseHelper da;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dlist);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view4);
        recyclerView.setHasFixedSize(true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar4);
        da = new DatabaseHelper(this);
        //  toolbar.setTitle("Words List");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        list = da.getMyDList();
        Log.d("list",list.toString());
//        getActionBar().setDisplayShowHomeEnabled(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyDAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }

    public void add(View v){
        startActivity(new Intent(this, AddWord.class));
    }
}
