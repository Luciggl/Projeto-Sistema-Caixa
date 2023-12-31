package Model.services;

import Model.repositories.TaxPayments;

import javax.swing.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentsServices {

    private Double valorTotalPorMeioDePagamento;

    Date dateFinal = new Date();
    SimpleDateFormat formato = new SimpleDateFormat("EEE dd/MM/yyyy HH:mm:ss");
    String DataFimCompra = formato.format(dateFinal);
    String formaPagamento;

    Double valorNaoInformado = 0.0;

    public PaymentsServices() {

    }

    public static class ValidadorCartaoCredito {
        public static boolean validarNumeroCartao(String numeroCartao) {

            String numeroLimpo = numeroCartao.replaceAll("\\D", "");

            if (numeroLimpo.length() != 16) {
                return false;
            }

            int soma = 0;
            boolean alternar = false;

            for (int i = numeroLimpo.length() - 1; i >= 0; i--) {
                int digito = Character.getNumericValue(numeroLimpo.charAt(i));

                if (alternar) {
                    digito *= 2;
                    if (digito > 9) {
                        digito -= 9;
                    }
                }

                soma += digito;
                alternar = !alternar;
            }

            return (soma % 10 == 0);
        }

    }


    public String pagamentoPix(Double valor) {
        JOptionPane.showMessageDialog(null, "Transação Aprovada!");
        valorTotalPorMeioDePagamento = valor -= CalcularTaxPix(valor);
        formaPagamento = "-------------------------------------------" + "\n" + DataFimCompra + "\n-------------------------------------------\nForma de pagamento: PIX\nValor recebido R$:" + formatarValor(valor) + "\nDesconto R$: " + formatarValor(CalcularTaxPix(valor)) + "\nTaxa Cartão:" + CalcularTaxCredito(valorNaoInformado) + "\nValor total R$:" + formatarValor(valor) + "\nTroco R$:" + formatarValor(CalcularTroco(valorTotalPorMeioDePagamento, valorNaoInformado));
        return formaPagamento;
    }

    public String pagamentoCredito(Double valor) {
        valorTotalPorMeioDePagamento = valor += CalcularTaxCredito(valor);
        JOptionPane.showMessageDialog(null, "Transação Aprovada!");
        formaPagamento = "-------------------------------------------" + "\n" + DataFimCompra + "\n-------------------------------------------\nForma de pagamento: CREDITO\nValor recebido R$:" + valor + "\nDesconto R$: " + CalcularTaxPix(valorNaoInformado) + "\nTaxa Cartão:" + String.format("%.2f",CalcularTaxCredito(valor)) + "\nValor total R$:" + valor + "\nTroco R$:" + CalcularTroco(valorNaoInformado, valorNaoInformado);
        return formaPagamento;
    }

    public String pagamentoDebito(Double valor) {
        valorTotalPorMeioDePagamento = valor;
        JOptionPane.showMessageDialog(null, "Transação Aprovada!");
        formaPagamento = "-------------------------------------------" + "\n" + DataFimCompra + "\n-------------------------------------------\nForma de pagamento: DEBITO\nValor recebido R$:" + valor + "\nDesconto R$: " + CalcularTaxPix(valorNaoInformado) + "\nTaxa Cartão R$:" + CalcularTaxCredito(valorNaoInformado) + "\nValor total R$:" + valor + "\nTroco R$:" + CalcularTroco(valorNaoInformado, valorNaoInformado);
        return formaPagamento;
    }

    public String pagamentoDinheiro(double valor, double valorRecebido) {
        JOptionPane.showMessageDialog(null, "Transação Aprovada!");
        formaPagamento = "-------------------------------------------" + "\n" + DataFimCompra + "\n-------------------------------------------\nForma de pagamento: DINHEIRO\nValor recebido R$:" + valorRecebido + "\nDesconto R$: " + CalcularTaxPix(valorNaoInformado) + "\nTaxa Cartão R$:" + CalcularTaxCredito(0.0) + "\nValor total R$:" + valor + "\nTroco R$:" + CalcularTroco(valor, valorRecebido);
        return formaPagamento;
    }

    private String formatarValor(double valor) {
        DecimalFormat formato = new DecimalFormat("#,##0.00");
        return formato.format(valor);
    }

    public double CalcularTaxPix(Double valor) {
        return (valor * TaxPayments.taxPix);
    }

    public double CalcularTaxCredito(Double valor) {
        return (valor * TaxPayments.taxCredito);
    }

    public double CalcularTroco(Double valorTotal, Double valorRecebido) {
        if (valorRecebido >= valorTotal){
            return valorRecebido -= valorTotal;
        } else return 0;
    }


}
