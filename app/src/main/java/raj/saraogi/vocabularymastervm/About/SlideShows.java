package raj.saraogi.vocabularymastervm.About;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import raj.saraogi.vocabularymastervm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SlideShows extends Fragment {

    ImageView imageView;
    int imgsrc;
    public SlideShows() {
        // Required empty public constructor
    }

   /* public SlideShows(int pos){
        imgsrc=pos;
    }
*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_slide_shows, container, false);
        Bundle b = getArguments();
        imgsrc=b.getInt("pos");
        imageView=(ImageView)view.findViewById(R.id.sliderImage);
        if(imgsrc==0)
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.homeabout));
        else if(imgsrc==1)
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.oceanabout));
        else if(imgsrc==2)
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.detailabout));
        else if(imgsrc==3)
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.slideabout));
        else if(imgsrc==4)
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.shareabout));
        else if(imgsrc==5)
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.personalabout));
        else
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.homeabout));

        return view;
    }

}
