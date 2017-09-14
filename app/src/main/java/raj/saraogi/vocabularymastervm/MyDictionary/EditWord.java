package raj.saraogi.vocabularymastervm.MyDictionary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import raj.saraogi.vocabularymastervm.Database.DatabaseHelper;
import raj.saraogi.vocabularymastervm.Holder.Word;
import raj.saraogi.vocabularymastervm.R;

public class EditWord extends AppCompatActivity {
    EditText mean, syn, example;
    TextView w;
    Button s,c;
    int pos;
    List<Word> list =new ArrayList<>();
    private ProgressDialog pDialog;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_word);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar7);
        //  toolbar.setTitle("Words List");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        w=(TextView)findViewById(R.id.aword2);
        mean = (EditText) findViewById(R.id.amean);
        syn = (EditText) findViewById(R.id.asyn);
        example = (EditText) findViewById(R.id.aexample);
        s=(Button)findViewById(R.id.saveButton);
        c=(Button)findViewById(R.id.cancelButton);
        Bundle b=getIntent().getExtras();
        pos=b.getInt("pos");
        w.setText(b.getString("word"));
        mean.setText(b.getString("desc"));
        syn.setText(b.getString("syn"));
        example.setText(b.getString("ex"));
        databaseHelper=new DatabaseHelper(this);
    }

    public void save(View view){
        Boolean result=databaseHelper.updateMyD(w.getText().toString(),mean.getText().toString(),syn.getText().toString(),example.getText().toString());
            if(result){
                Toast.makeText(this,"Successfully Edited the word ",Toast.LENGTH_SHORT).show();
                list=databaseHelper.getMyDList();
                Bundle bundle = new Bundle();
                bundle.putSerializable("list",(Serializable) list);
                bundle.putInt("pos",pos);
                startActivity(new Intent(this,MyDWordDetail.class).putExtras(bundle));
                finish();
            }
            else{
                Toast.makeText(this,"Error in updating",Toast.LENGTH_SHORT).show();
            }
        }

    public void cancel(View view){
        super.onBackPressed();
    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
