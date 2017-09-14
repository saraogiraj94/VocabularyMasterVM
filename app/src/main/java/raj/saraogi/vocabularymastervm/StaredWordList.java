package raj.saraogi.vocabularymastervm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import raj.saraogi.vocabularymastervm.Database.DatabaseHelper;
import raj.saraogi.vocabularymastervm.Holder.Word;

public class StaredWordList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    List<Word> arrayList =new ArrayList<>();
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stared_word_list);
        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        //  toolbar.setTitle("Words List");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        databaseHelper=new DatabaseHelper(this);
        arrayList=databaseHelper.getStarWordsList();

//        getActionBar().setDisplayShowHomeEnabled(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new StaredWordAdapter(this,arrayList);
        recyclerView.setAdapter(adapter);
    }
}
