package raj.saraogi.vocabularymastervm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Raj Saraogi on 05-09-2016.
 */
public class UserWordsAdapter  extends RecyclerView.Adapter<UserWordsAdapter.Holder> {
    Context context ;
    List<String> listwords;

    public UserWordsAdapter(List<String> listwords,Context context){
        this.listwords=listwords;
        this.context=context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent, false);
        Holder mainCardViewHolder = new Holder(view);
        return mainCardViewHolder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.t1.setText(listwords.get(position));
    }

    @Override
    public int getItemCount() {
        return listwords.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView t1;
        public Holder(View itemView) {
            super(itemView);
            t1=(TextView) itemView.findViewById(R.id.text_name);
        }

    }
}
