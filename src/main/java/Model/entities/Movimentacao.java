package Model.entities;

import javax.swing.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


public class Movimentacao {

    private Date aberturaCaixa;
    private BigDecimal valorDiarioPix;
    private BigDecimal valorDiarioCredito;
    private BigDecimal valorDiarioDebito;
    private BigDecimal valorDiarioDinheiro;
    private Date fechamentoCaixa;

    public Movimentacao() {
        this.aberturaCaixa = new Date();
        this.valorDiarioPix = BigDecimal.ZERO;
        this.valorDiarioCredito = BigDecimal.ZERO;
        this.valorDiarioDebito = BigDecimal.ZERO;
        this.valorDiarioDinheiro = BigDecimal.ZERO;
        this.fechamentoCaixa = new Date();
    }

    public Movimentacao(Date aberturaCaixa, BigDecimal valorDiarioPix, BigDecimal valorDiarioCredito, BigDecimal valorDiarioDebito, BigDecimal valorDiarioDinheiro, Date fechamentoCaixa) {
        this.aberturaCaixa = aberturaCaixa;
        this.valorDiarioPix = valorDiarioPix;
        this.valorDiarioCredito = valorDiarioCredito;
        this.valorDiarioDebito = valorDiarioDebito;
        this.valorDiarioDinheiro = valorDiarioDinheiro;
        this.fechamentoCaixa = fechamentoCaixa;
    }

    public Date getAberturaCaixa() {
        return aberturaCaixa;
    }

    public BigDecimal getValorDiarioPix() {
        return valorDiarioPix;
    }

    public BigDecimal getValorDiarioCredito() {
        return valorDiarioCredito;
    }

    public BigDecimal getValorDiarioDebito() {
        return valorDiarioDebito;
    }

    public BigDecimal getValorDiarioDinheiro() {
        return valorDiarioDinheiro;
    }

    public Date getFechamentoCaixa() {
        return fechamentoCaixa;
    }

    public void adicionarValorDiarioPix(BigDecimal valor) {
        valorDiarioPix = valorDiarioPix.add(valor);
    }

    public void adicionarValorDiarioCredito(BigDecimal valor) {
        valorDiarioCredito = valorDiarioCredito.add(valor);
    }

    public void adicionarValorDiarioDebito(BigDecimal valor) {
        valorDiarioDebito = valorDiarioDebito.add(valor);
    }

    public void adicionarValorDiarioDinheiro(BigDecimal valor) {
        valorDiarioDinheiro = valorDiarioDinheiro.add(valor);
    }

    public void setFechamentoCaixa(Date fechamentoCaixa) {
        this.fechamentoCaixa = fechamentoCaixa;
    }

    private SimpleDateFormat formato = new SimpleDateFormat("EEE dd/MM/yyyy HH:mm:ss");

    public void BalancoCaixaDiario(Date fechamentoCaixa){
        setFechamentoCaixa(fechamentoCaixa);
        JOptionPane.showMessageDialog(null,
                "Abertura do Caixa: " + formato.format(aberturaCaixa) +
                        "\nPagamento via PIX Total R$: " + getValorDiarioPix() +
                        "\nPagamento via Credito Total R$: " + getValorDiarioCredito() +
                        "\nPagamento via Debito Total R$: " + getValorDiarioDebito() +
                        "\nPagamento via Dinheiro Total R$: " + getValorDiarioDinheiro() +
                        "\nFechamento do Caixa: " + formato.format(fechamentoCaixa));
    }


    public boolean VerificarSeOuveMovimentacao(){
        return !Objects.equals(getValorDiarioPix(), BigDecimal.ZERO) || !Objects.equals(getValorDiarioCredito(), BigDecimal.ZERO) || !Objects.equals(getValorDiarioDebito(), BigDecimal.ZERO) || !Objects.equals(getValorDiarioDinheiro(), getValorDiarioDinheiro());
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", new Locale("pt", "BR"));

        return "Movimentacao:" +
                "\n  Abertura do Caixa=" + dateFormat.format(aberturaCaixa) +
                "\n  Valor Diário Pix=" + valorDiarioPix +
                "\n  Valor Diário Crédito=" + valorDiarioCredito +
                "\n  Valor Diário Débito=" + valorDiarioDebito +
                "\n  Valor Diário Dinheiro=" + valorDiarioDinheiro +
                "\n  Fechamento do Caixa=" + dateFormat.format(fechamentoCaixa) +
                "\n ==========================================================";
    }

}
