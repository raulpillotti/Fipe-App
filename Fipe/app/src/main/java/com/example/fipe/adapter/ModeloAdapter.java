package com.example.fipe.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fipe.R;
import com.example.fipe.activities.AnoActivity;
import com.example.fipe.model.Dados;
import com.example.fipe.model.Modelo;

import java.util.ArrayList;
import java.util.List;

public class ModeloAdapter extends RecyclerView.Adapter<ModeloAdapter.ViewHolder> implements Filterable {

    private List<Modelo> modelos;
    private List<Modelo> modelosAll;

    public ModeloAdapter(List<Modelo> modelos) {
        this.modelos = modelos;
        modelosAll = new ArrayList<>(modelos);
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.linha, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        holder.setData(modelos.get(position));
    }

    public int getItemCount(){
        return modelos.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Modelo> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(modelosAll);

            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(Modelo item : modelosAll){
                    if(item.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }

                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            modelos.clear();
            modelos.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

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

        private void setData(Modelo modelo) {
            nomeMarca.setText(modelo.getName());
            idMarca.setText("Cód. " + modelo.getCode());;
        }

        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent = new Intent(context, AnoActivity.class);
            Dados.getInstance().setIdModelo(Integer.parseInt(modelos.get(getLayoutPosition()).getCode()));
            intent.putExtra("id_modelo", Integer.parseInt(modelos.get(getLayoutPosition()).getCode()));
            context.startActivity(intent);
        }
    }

}