package Model.services;

import Model.entities.Movimentacao;

import javax.swing.*;
import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MovimentacaoServices {
    private ArrayList<Movimentacao> movimentacaos = new ArrayList<>();

    public ArrayList<Movimentacao> getMovimentacaos() {
        return movimentacaos;
    }

    public void addMovimentacao(Movimentacao movimentacao) {
        if (!movimentacaos.contains(movimentacao)) {
            movimentacaos.add(movimentacao);
        }
    }

    public void SalvarMovimentacao(String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Movimentacao movimentacao : movimentacaos) {
                bw.write(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").format(movimentacao.getAberturaCaixa()) + "," +
                        movimentacao.getValorDiarioPix() + "," + movimentacao.getValorDiarioCredito() + "," +
                        movimentacao.getValorDiarioDebito() + "," + movimentacao.getValorDiarioDinheiro() + "," +
                        new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").format(movimentacao.getFechamentoCaixa()));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void carregarMovimentacao(String filepath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String Abertura = parts[0];
                    BigDecimal Pix = new BigDecimal(parts[1].replace(",", "."));
                    BigDecimal credito = new BigDecimal(parts[2].replace(",", "."));
                    BigDecimal debito = new BigDecimal(parts[3].replace(",", "."));
                    BigDecimal dinheiro = new BigDecimal(parts[4].replace(",", "."));
                    String fechamento = parts[5];

                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", new Locale("pt", "BR"));

                    try {
                        Date dateAbertura = dateFormat.parse(Abertura);
                        Date dataFechamento = dateFormat.parse(fechamento);

                        Movimentacao services = new Movimentacao(dateAbertura, Pix, credito, debito, dinheiro, dataFechamento);
                        movimentacaos.add(services);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Erro ao converter data: " + Abertura + " " + fechamento);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String toString() {
        return
                "movimentacaos=" + movimentacaos + "\n =========================================";

    }
}
