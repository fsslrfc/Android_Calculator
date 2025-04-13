package com.example.myapplication3.Adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication3.R;
import com.example.myapplication3.beans.ItemBean;

import java.util.List;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.MyHolder> {

    private final List<ItemBean> Data;
    private OnItemClickListener Listener;

    public ListViewAdapter(List<ItemBean> data) {
        this.Data = data;
    }

    @NonNull
    @Override
    public ListViewAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_list_view, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewAdapter.MyHolder holder, int position) {
        holder.setData(Data.get(position), position);
    }

    @Override
    public int getItemCount() {
        if (Data == null) {
            return 0;
        }
        else {
            return Data.size();
        }
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.Listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView viewCount;
        private TextView viewNum1;
        private TextView viewSymbol;
        private TextView viewNum2;
        private TextView viewRes;
        private Button buttonDel;
        private int position;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            viewCount = itemView.findViewById(R.id.item_count);
            viewNum1 = itemView.findViewById(R.id.item_num1);
            viewSymbol = itemView.findViewById(R.id.item_sym);
            viewNum2 = itemView.findViewById(R.id.item_num2);
            viewRes = itemView.findViewById(R.id.item_res);
            buttonDel = itemView.findViewById(R.id.item_del);

            buttonDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Listener != null) {
                        Listener.onItemClick(position);
                    }
                }
            });

        }

        public void setData(ItemBean itemBean, int position) {
            this.position = position;
            viewCount.setText(String.valueOf(" # "));

            String str = itemBean.text;

            int index1 = str.indexOf(" ");
            viewNum1.setText(str.substring(0, index1));

            int index2 = str.indexOf(" ", index1 + 1);
            viewSymbol.setText(str.substring(index2 - 1, index2));

            int index3 = str.indexOf(" ", index2 + 1);
            viewNum2.setText(str.substring(index2 + 1, index3));

            viewRes.setText(str.substring(index3 + 2));
        }
    }
}
