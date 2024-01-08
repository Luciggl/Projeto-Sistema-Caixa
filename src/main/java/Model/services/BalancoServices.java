package Model.services;

import Model.entities.BalancoCaixa;
import Model.entities.Products;

import javax.swing.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BalancoServices {
    private final ArrayList<BalancoCaixa> transacao;

    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

    public BalancoServices(ArrayList<BalancoCaixa> balanco) {
        this.transacao = balanco;
    }

    public BalancoServices() {
        this.transacao = new ArrayList<>();
    }

    public ArrayList<BalancoCaixa> getBalanco() {
        return transacao;
    }


    public void registrarEntrada(Products products, int Quant) {
        String tipo = "Entrada";
        transacao.add(new BalancoCaixa(products, Quant, tipo, new Date()));
    }

    public void registrarSaida(Products products, int Quant) {
        String tipo = "Saida";
        transacao.add(new BalancoCaixa(products, Quant, tipo, new Date()));
    }

    public void exibirTransacoes() {
        JTextArea textArea = new JTextArea(15, 30);
        textArea.setText(transacao.toString() + "\n");
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(null, scrollPane, "TRANSAÇÔES", JOptionPane.PLAIN_MESSAGE);

    }

    public void salvarTransacoes(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (BalancoCaixa balanco : transacao) {
                writer.write(balanco.getTipo() + "," + balanco.getProdutos().getName() + "," + balanco.getQuant() + "," + dateFormat.format(balanco.getDate()));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void recuperarTransacoes(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String tipo = parts[0];
                    String nomeProduto = parts[1];
                    int quantidade = Integer.parseInt(parts[2]);
                    String dataString = parts[3];

                    try {
                        Date data = dateFormat.parse(dataString);

                        BalancoCaixa balanco = new BalancoCaixa(new Products(nomeProduto), quantidade, tipo, data);

                        transacao.add(balanco);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Erro ao converter data: " + dataString);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void PesquisarTipo(String tipo) {
        ArrayList<BalancoCaixa> TipoConta = new ArrayList<>();
        for (BalancoCaixa ContasTipo : transacao) {
            if (ContasTipo.getTipo().equals(tipo)) {
                TipoConta.add(ContasTipo);
            }
        }
        JTextArea textArea = new JTextArea(15, 30);
        textArea.setText(String.valueOf(TipoConta));
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(null, scrollPane, "TRANSAÇÔES ENCONTRADAS", JOptionPane.PLAIN_MESSAGE);
    }


    @Override
    public String toString() {
        return "\n" + transacao;
    }
}