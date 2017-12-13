package test.com.question.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import test.com.question.R;

/**
 * Created by ksk648 on 11/23/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private List<String> values;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewFirst;

        public TextView textViewSecond;

        public View layout;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            this.textViewFirst = itemView.findViewById(R.id.firstLine);
            this.textViewSecond = itemView.findViewById(R.id.secondLine);;
        }
    }

    public void add(int position, String item){
        values.add(position, item);
        notifyItemInserted(position);
    }


    public void delete(int position){
        values.remove(position);
        notifyItemRemoved(position);
    }


    public RecyclerAdapter(List<String> input){
        this.values = input;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = values.get(position);
        holder.textViewFirst.setText("First ->"+name);
        holder.textViewSecond.setText("Description->"+name);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}
