package Model.entities;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BalancoCaixa implements Serializable {
    private Products Produtos;
    private int quant;

    private String tipo;

    private Date date;

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public BalancoCaixa() {
    }

    public BalancoCaixa(Products produtos, int quant, String tipo, Date date) {
        this.Produtos = produtos;
        this.quant = quant;
        this.tipo = tipo;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Balanco Caixa:\n" +
                Produtos.getName() + " " + quant +
                "\ntipo: " + tipo +
                "\n" + dateFormat.format(date)+
                "==============================";
    }
}
