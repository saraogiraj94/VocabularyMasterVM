package raj.saraogi.vocabularymastervm;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.SimpleResource;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import raj.saraogi.vocabularymastervm.Database.DatabaseHelper;
import raj.saraogi.vocabularymastervm.Holder.Word;


/**
 * A simple {@link Fragment} subclass.
 * <p/>
 * Start with the place where it left
 */
public class BlankFragment extends Fragment {
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    TextView wname, desc, use, syn, exammple;
    CardView card_use_next;
    NetworkImageView imageView;
    ImageView star,speaker;
    List<Word> arrayList = new ArrayList<Word>();
    DatabaseHelper databaseHelper;
    int pos;
    String[] name = {"Raj", "Amit", "sharma"};
    static Boolean result;
    ImageLoader imageLoader;
    TextToSpeech t1;

    Bundle bundle = new Bundle();

    public BlankFragment() {


        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        //Bundle bundle =getArguments();
        //pos=bundle.getInt("pos");
        Bundle bundle = getArguments();
        arrayList= (List<Word>) bundle.getSerializable("list");
        pos=bundle.getInt("pos");
        databaseHelper = new DatabaseHelper(this.getContext());
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsingToolbar);

        wname = (TextView) view.findViewById(R.id.text_word_name);
        desc = (TextView) view.findViewById(R.id.text_word_description);
        use = (TextView) view.findViewById(R.id.text_use_next_time);
        syn = (TextView) view.findViewById(R.id.text_word_synonyms);
        exammple = (TextView) view.findViewById(R.id.text_word_example);
        imageView = (NetworkImageView) view.findViewById(R.id.mainImage);
        card_use_next = (CardView) view.findViewById(R.id.card_use_next_time);
        star = (ImageView) view.findViewById(R.id.starButton);
        speaker = (ImageView) view.findViewById(R.id.speaker);
        //  wname.setText(name[pos]);
            final Word word = arrayList.get(pos);
            // Toast.makeText(view.getContext(), pos, Toast.LENGTH_LONG).show();

            collapsingToolbarLayout.setTitle(word.getWname());
            wname.setText(word.getWname());
            desc.setText(word.getDesc());
            if (word.getUse().isEmpty()) {
                card_use_next.setVisibility(View.GONE);
            } else {
                use.setText(word.getUse());
            }
            if (databaseHelper.check(word.getWname()) != 0) {

                star.setImageDrawable(getResources().getDrawable(R.drawable.starfilled));
            } else {
                star.setImageDrawable(getResources().getDrawable(R.drawable.star));
            }
            syn.setText(word.getSynonyms());
            exammple.setText(word.getExample());
            String url = word.getUrl();
           // Log.d("url", word.getUrl());
            imageLoader = Singleton.getInstance(view.getContext()).getImageLoader();
            imageLoader.get(url, ImageLoader.getImageListener(imageView, R.drawable.loading, R.drawable.loading));
            imageView.setImageUrl(url, imageLoader);
            star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (databaseHelper.check(word.getWname()) != 0) {
                        delete(word.getWname());
                    } else {
                        Stared(word.getId(), word.getWname(), word.getDesc(), word.getUse(), word.getSynonyms(), word.getExample(), word.getUrl());
                    }

                }
            });
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
     //   Log.d("msg", Config.arrayList.toString());
        dynamicToolbarColor();
        toolbarTextAppernce();
        return view;
    }

    private void dynamicToolbarColor() {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.loading);
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

    public void Stared(String id, String wname, String w_desc, String w_syn, String w_use, String w_example, String w_img_path) {
        star.setImageDrawable(getResources().getDrawable(R.drawable.starfilled));
        Bitmap bitmap1 = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        String path=updateReportPicture(wname,bitmap1);
        result = databaseHelper.addStarWord(id, wname, w_desc, w_syn, w_use, w_example,path);
    }

    public String updateReportPicture(String word, Bitmap picture) {
        // Saves the new picture to the internal storage with the unique identifier of the report as
        // the name. That way, there will never be two report pictures with the same name.
        String picturePath = "";
        File internalStorage = getContext().getDir("ReportPictures", Context.MODE_PRIVATE);
        File reportFilePath = new File(internalStorage, word + ".png");
        picturePath = reportFilePath.toString();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(reportFilePath);
            picture.compress(Bitmap.CompressFormat.PNG, 100 /*quality*/, fos);
            fos.close();
        } catch (Exception ex) {
            Log.i("DATABASE", "Problem updating picture", ex);
            picturePath = "";
        }
        return picturePath;
    }

    public void delete(String w) {
        String s = databaseHelper.deleteAStarWord(w);
        if (s != null) {
            File reportFilePath = new File(s);
            reportFilePath.delete();
            star.setImageDrawable(getResources().getDrawable(R.drawable.star));
        }
    }
}

