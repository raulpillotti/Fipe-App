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
import com.example.fipe.activities.EditActivity;
import com.example.fipe.activities.ModeloActivity;
import com.example.fipe.model.Dados;
import com.example.fipe.model.Marca;

import java.util.ArrayList;
import java.util.List;

public class MarcasAdapter extends RecyclerView.Adapter<MarcasAdapter.ViewHolder> implements Filterable {

    private List<Marca> marcas;
    private List<Marca> marcasAll;

    public MarcasAdapter(List<Marca> marcas) {
        this.marcas = marcas;
        marcasAll = new ArrayList<>(marcas);
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.linha, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        holder.setData(marcas.get(position));
    }

    public int getItemCount(){
        return marcas.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Marca> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(marcasAll);

            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(Marca item : marcasAll){
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
            marcas.clear();
            marcas.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout marca_layout;
        TextView nomeMarca, idMarca;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            marca_layout = (LinearLayout) itemView.findViewById(R.id.marca_layout);
            nomeMarca = (TextView) itemView.findViewById(R.id.nomeMarca);
            idMarca = (TextView) itemView.findViewById(R.id.idMarca);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, EditActivity.class);
                    intent.putExtra("marca", marcas.get(getLayoutPosition()).getName());
                    intent.putExtra("id_marca", Integer.valueOf(marcas.get(getLayoutPosition()).getCode()));
                    context.startActivity(intent);

                    return true;
                }
            });

        }

        private void setData(Marca marca) {
            nomeMarca.setText(marca.getName());
            idMarca.setText("Cód. " + marca.getCode());
        }

        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent = new Intent(context, ModeloActivity.class);
            Dados.getInstance().setIdMarca(Integer.valueOf(marcas.get(getLayoutPosition()).getCode()));
            intent.putExtra("id_marca", Integer.valueOf(marcas.get(getLayoutPosition()).getCode()));
            context.startActivity(intent);
        }
    }

}