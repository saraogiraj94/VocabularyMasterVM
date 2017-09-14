package raj.saraogi.vocabularymastervm;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import raj.saraogi.vocabularymastervm.Holder.Word;


/**
 * A simple {@link Fragment} subclass.
 */
public class SlidePageFragment extends Fragment {

    String[] url={"http://myandroiddevelopment.esy.es/melodrama.png","http://myandroiddevelopment.esy.es/farce.jpg","http://myandroiddevelopment.esy.es/melodrama.png"};
    CardView cardView;
    List<Word> list =new ArrayList<>();
    int ran;
    TextView t1;
    public SlidePageFragment() {
        // Required empty public constructor
    }

    //static TextView tv;
    static ImageView imageView;
        static int page_pos=0;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_slide_page, container, false);
       //tv=(TextView)rootView.findViewById(R.id.txtview);
        imageView=(ImageView)rootView.findViewById(R.id.main);
        Bundle b=getArguments();
        page_pos=b.getInt("pos");

        t1=(TextView)rootView.findViewById(R.id.word_name);
        list= (List<Word>) b.getSerializable("list");
        //tv.setText(MainActivity.str[page_pos]);
    //    imageView.setImageDrawable(getResources().getDrawable(image[page_pos]));
        Random r =new Random();
        ran=r.nextInt(list.size()-0);
        Log.d("ran", String.valueOf(ran));
        Glide.with(this.getContext()).load(list.get(ran).getUrl())
               // .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
        cardView=(CardView)rootView.findViewById(R.id.c);
     //   Picasso.with(rootView.getContext()).load(url[page_pos]).into(imageView);
        t1.setText(list.get(ran).getWname());
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("list",(Serializable)list);
                bundle.putInt("pos",ran);
                startActivity(new Intent(getContext(),Main2Activity.class).putExtras(bundle));
            }
        });
        return rootView;


    }

}
