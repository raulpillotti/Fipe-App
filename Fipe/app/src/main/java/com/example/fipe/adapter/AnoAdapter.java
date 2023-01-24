package com.example.fipe.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fipe.R;
import com.example.fipe.activities.PrecoActivity;
import com.example.fipe.model.Ano;
import com.example.fipe.model.Dados;

import java.util.List;

public class AnoAdapter extends RecyclerView.Adapter<AnoAdapter.ViewHolder> {

    private List<Ano> anos;

    public AnoAdapter(List<Ano> anos) {
        this.anos = anos;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.linha, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(anos.get(position));
    }

    public int getItemCount() {
        return anos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout marca_layout;
        TextView nomeMarca;
        TextView idMarca;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            marca_layout = (LinearLayout) itemView.findViewById(R.id.marca_layout);
            nomeMarca = (TextView) itemView.findViewById(R.id.nomeMarca);
            idMarca = (TextView) itemView.findViewById(R.id.idMarca);
        }

        private void setData(Ano ano) {
            nomeMarca.setText(ano.getName());
            idMarca.setText("Cód. " + ano.getCode());
        }

        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent = new Intent(context, PrecoActivity.class);
            Dados.getInstance().setIdAno(anos.get(getLayoutPosition()).getCode());
            intent.putExtra("num_ano", anos.get(getLayoutPosition()).getCode());
            context.startActivity(intent);
        }
    }

}
