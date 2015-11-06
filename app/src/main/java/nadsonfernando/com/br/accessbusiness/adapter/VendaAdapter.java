package nadsonfernando.com.br.accessbusiness.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import nadsonfernando.com.br.accessbusiness.R;
import nadsonfernando.com.br.accessbusiness.model.Venda;

/**
 * Created by nadsonfernando on 30/10/15.
 */
public class VendaAdapter extends RecyclerView.Adapter<VendaAdapter.ViewHolder> {

    private ArrayList<Venda> vendas = new ArrayList<Venda>();
    private Venda item;


    public VendaAdapter() {}

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView descricao, cliente, valor, diaMes;

        public ViewHolder(View itemView) {
            super(itemView);

            descricao = (TextView) itemView.findViewById(R.id.descricao_item_venda);
            cliente = (TextView) itemView.findViewById(R.id.cliente_item_venda);
            valor = (TextView) itemView.findViewById(R.id.valor_item_venda);
            diaMes = (TextView) itemView.findViewById(R.id.valor_item_diaMes);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public VendaAdapter(List<Venda> vendas) {
        this.vendas.addAll(vendas);
    }

    @Override
    public VendaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_venda, parent, false);

        ViewHolder vh = new VendaAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Venda venda = this.vendas.get(position);

        holder.descricao.setText(venda.getDescricao().toString());
        holder.cliente.setText(venda.getCliente().getNome().toString());
        holder.valor.setText(String.valueOf(String.format("%.2f", venda.getValor())));

        SimpleDateFormat simpleDateFormatDia = new SimpleDateFormat("DD");
        SimpleDateFormat simpleDateFormatMes = new SimpleDateFormat("MMMM");

        holder.diaMes.setText(simpleDateFormatDia.format(venda.getDataVenda()) + " " + simpleDateFormatMes.format(venda.getDataVenda()).substring(0,3).toUpperCase());
    }

    public void add(Venda venda) {
        vendas.add(venda);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        vendas.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return vendas.size();
    }

    public Venda getItem(int position) {
        return vendas.get(position);
    }
}
