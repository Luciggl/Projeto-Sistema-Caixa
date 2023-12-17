package Model.services;

import Model.entities.BalancoCaixa;
import Model.entities.Products;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;

public class BalancoServices {
    private final ArrayList<BalancoCaixa> transacao;

    public BalancoServices(ArrayList<BalancoCaixa> balanco) {
        this.transacao = balanco;
    }

    public BalancoServices() {
        this.transacao = new ArrayList<>();
    }

    public ArrayList<BalancoCaixa> getBalanco() {
        return transacao;
    }


    public void registrarEntrada(Products products, int Quant){
        String tipo = "Entrada";
        transacao.add(new BalancoCaixa(products,Quant ,tipo, new Date()));
    }

    public void registrarSaida(Products products, int Quant){
        String tipo = "Saida";
        transacao.add(new BalancoCaixa(products,Quant , tipo, new Date()));
        }

    public void exibirTransacoes() {
        for (BalancoCaixa balanco : transacao) {
            JOptionPane.showMessageDialog(null, balanco.toString());
        }
    }

    @Override
    public String toString() {
        return "\n" + transacao;
    }
}
