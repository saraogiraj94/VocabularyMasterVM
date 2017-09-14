package raj.saraogi.vocabularymastervm;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import raj.saraogi.vocabularymastervm.Database.DatabaseHelper;
import raj.saraogi.vocabularymastervm.Holder.Word;


/**
 * A simple {@link Fragment} subclass.
 */
public class StarWordDetailsFragment extends Fragment {
    List<Word> list =new ArrayList<>();
    int pos;
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    TextView wname, desc, use, syn, exammple;
    CardView card_use_next;
    ImageView imageView;
    ImageView star,speaker;
    ImageLoader imageLoader;
    DatabaseHelper databaseHelper;
    TextToSpeech t1;


    public StarWordDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_star_word_details, container, false);
        list=(List<Word>)getArguments().getSerializable("list");
        pos=getArguments().getInt("pos");

        databaseHelper = new DatabaseHelper(this.getContext());
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar2);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsingToolbar2);

        wname = (TextView) view.findViewById(R.id.text_word_name2);
        desc = (TextView) view.findViewById(R.id.text_word_description2);
        use = (TextView) view.findViewById(R.id.text_use_next_time2);
        syn = (TextView) view.findViewById(R.id.text_word_synonyms2);
        exammple = (TextView) view.findViewById(R.id.text_word_example2);
        imageView = (ImageView) view.findViewById(R.id.mainImage2);
        card_use_next = (CardView) view.findViewById(R.id.card_use_next_time2);
        speaker=(ImageView)view.findViewById(R.id.speaker_star);
        final Word word = list.get(pos);
        // Toast.makeText(view.getContext(), pos, Toast.LENGTH_LONG).show();

        collapsingToolbarLayout.setTitle(word.getWname());
        wname.setText(word.getWname());
        desc.setText(word.getDesc());
        if (word.getUse().isEmpty()) {
            card_use_next.setVisibility(View.GONE);
        } else {
            use.setText(word.getUse());
        }
        syn.setText(word.getSynonyms());
        exammple.setText(word.getExample());
        String url = word.getUrl();
        Log.d("url", word.getUrl());
        Bitmap wordimage = BitmapFactory.decodeFile(url);
        imageView.setImageBitmap(wordimage);
        dynamicToolbarColor();
        toolbarTextAppernce();
        t1=new TextToSpeech(view.getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
        speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = wname.getText().toString();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        return view;
    }
    private void dynamicToolbarColor() {

        Bitmap bitmap1 = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        Palette.from(bitmap1).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(R.attr.colorPrimary));
                collapsingToolbarLayout.setStatusBarScrimColor(palette.getMutedColor(R.attr.colorPrimaryDark));
            }
        });
    }


    private void toolbarTextAppernce() {
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);


    }

}
