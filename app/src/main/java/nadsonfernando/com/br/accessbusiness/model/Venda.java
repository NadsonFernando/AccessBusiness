package nadsonfernando.com.br.accessbusiness.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by nadsonfernando on 21/10/15.
 */

@DatabaseTable(tableName = "venda")
public class Venda {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField
    private int pago;

    @DatabaseField
    private int prazo;

    @DatabaseField
    private Date dataVenda;

    @DatabaseField
    private String descricao;

    @DatabaseField(foreign = true)
    private Cliente cliente;

    public Venda(){}

    public Venda(Cliente cliente, Date dataVenda, Integer id, int pago, int prazo) {
        this.cliente = cliente;
        this.dataVenda = dataVenda;
        this.id = id;
        this.pago = pago;
        this.prazo = prazo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPago() {
        return pago;
    }

    public void setPago(int pago) {
        this.pago = pago;
    }

    public int getPrazo() {
        return prazo;
    }

    public void setPrazo(int prazo) {
        this.prazo = prazo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Venda{" +
                "cliente=" + cliente +
                ", id=" + id +
                ", pago=" + pago +
                ", prazo=" + prazo +
                ", dataVenda=" + dataVenda +
                '}';
    }
}
