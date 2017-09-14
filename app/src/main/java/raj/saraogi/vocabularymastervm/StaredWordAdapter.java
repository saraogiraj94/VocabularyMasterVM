package raj.saraogi.vocabularymastervm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import raj.saraogi.vocabularymastervm.Holder.Word;

/**
 * Created by Raj Saraogi on 10-05-2016.
 */
public class StaredWordAdapter extends RecyclerView.Adapter<StaredWordAdapter.Holder> {
    Context context;
    List<Word> list = new ArrayList<>();

    public StaredWordAdapter(Context context, List<Word> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        Holder mainHolder = new Holder(view);
        return mainHolder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Word word = list.get(position);
        holder.t1.setText(word.getWname());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView t1;

        public Holder(View itemView) {
            super(itemView);
            t1 = (TextView) itemView.findViewById(R.id.text_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos=getPosition();
            Bundle bundle =new Bundle();
            bundle.putInt("pos",pos);
            bundle.putInt("size",list.size());
            context.startActivity(new Intent(context,StarWordDetail.class).putExtras(bundle));

        }
    }
}
