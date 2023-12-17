package Model.entities;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BalancoCaixa implements Serializable {
    private Products produtos;
    private int quant;

    private String tipo;

    private Date date;

    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

    public BalancoCaixa() {
    }

    public Products getProdutos() {
        return produtos;
    }

    public int getQuant() {
        return quant;
    }

    public String getTipo() {
        return tipo;
    }

    public Date getDate() {
        return date;
    }

    public BalancoCaixa(Products produtos, int quant, String tipo, Date date) {
        this.produtos = produtos;
        this.quant = quant;
        this.tipo = tipo;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Balanco Caixa:\n" +
                produtos.getName() + " " + quant +
                "\ntipo: " + tipo + "\n"+
                dateFormat.format(date);
    }
}
