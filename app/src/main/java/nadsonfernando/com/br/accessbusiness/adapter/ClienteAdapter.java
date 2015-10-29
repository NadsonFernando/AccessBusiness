package nadsonfernando.com.br.accessbusiness.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nadsonfernando.com.br.accessbusiness.R;
import nadsonfernando.com.br.accessbusiness.interfaces.RecyclerViewOnClickListenerHack;
import nadsonfernando.com.br.accessbusiness.model.Cliente;

/**
 * Created by nadsonfernando on 19/10/15.
 */
public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ViewHolder>{

    private List<Cliente> clientes = new ArrayList<>();
    private RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack;
    private Cliente item;


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView nome;
        public TextView telefone;
        public ViewHolder(View itemView) {
            super(itemView);
            nome = (TextView) itemView.findViewById(R.id.nome_item_cliente);
            telefone = (TextView) itemView.findViewById(R.id.telefone_item_cliente);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(recyclerViewOnClickListenerHack != null) {
                recyclerViewOnClickListenerHack.onClickListener(v, getPosition());
            }
        }
    }


    public ClienteAdapter(List<Cliente> clientes) {
        this.clientes.addAll(clientes);
    }


    public ClienteAdapter() {

    }

    @Override
    public ClienteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_view_cliente, parent, false);

        ViewHolder vh = new ClienteAdapter.ViewHolder(view);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Cliente cliente = this.clientes.get(position);

        holder.nome.setText(cliente.getNome());
        holder.telefone.setText(cliente.getTelefone());
    }

    public void add(Cliente cliente) {
        clientes.add(cliente);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        clientes.remove(position);
        notifyDataSetChanged();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r) {
        this.recyclerViewOnClickListenerHack = r;
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }

    public Cliente getItem(int position) {
        return clientes.get(position);
    }
}
