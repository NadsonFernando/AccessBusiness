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
    private Date dataVenda;

    @DatabaseField
    private float valor;

    @DatabaseField
    private String descricao;

    @DatabaseField(foreign = true)
    private Cliente cliente;

    public Venda(){}

    public Venda(int pago, Date dataVenda, float valor, String descricao, Cliente cliente) {
        this.pago = pago;
        this.dataVenda = dataVenda;
        this.valor = valor;
        this.descricao = descricao;
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
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
                ", dataVenda=" + dataVenda +
                '}';
    }
}
