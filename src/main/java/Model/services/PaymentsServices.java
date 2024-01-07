package Model.services;

import Model.repositories.TaxPayments;

import javax.swing.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentsServices {
    Date aberturaCaixa = new Date();
    Date dateFinal = new Date();

    SimpleDateFormat formato = new SimpleDateFormat("EEE dd/MM/yyyy HH:mm:ss");
    String DataFimCompra = formato.format(dateFinal);
    String formaPagamento;

    public String pagamentoPix(BigDecimal valor, String nome) {
        JOptionPane.showMessageDialog(null, "Transação Aprovada!");
        formaPagamento = "-------------------------------------------" + "\n" + DataFimCompra + "\nCaixa: " + nome + "\n-------------------------------------------\nForma de pagamento: PIX\nValor Total R$:" + formatarValor(valor) + "\nDesconto R$: " + formatarValor(CalcularTaxPix(valor)) + "\nTaxa Cartão:" + BigDecimal.ZERO + "\nValor final R$:" + formatarValor(valor.subtract(CalcularTaxPix(valor))) + "\nTroco R$:" + BigDecimal.ZERO;
        return formaPagamento;
    }

    public String pagamentoCredito(BigDecimal valor, String nome) {
        JOptionPane.showMessageDialog(null, "Transação Aprovada!");
        BigDecimal desconto = BigDecimal.ZERO;
        BigDecimal taxaCartao = CalcularTaxCredito(valor);
        BigDecimal valorFinal = valor.add(taxaCartao);

        formaPagamento = "-------------------------------------------" + "\n" + DataFimCompra + "\nCaixa: " + nome + "\n-------------------------------------------\nForma de pagamento: CREDITO\nValor Total R$:" + formatarValor(valor) + "\nDesconto R$: " + formatarValor(desconto) + "\nTaxa Cartão:" + formatarValor(taxaCartao) + "\nValor final R$:" + formatarValor(valorFinal) + "\nTroco R$:" + BigDecimal.ZERO;
        return formaPagamento;
    }

    public String pagamentoDebito(BigDecimal valor, String nome) {
        JOptionPane.showMessageDialog(null, "Transação Aprovada!");
        formaPagamento = "-------------------------------------------" + "\n" + DataFimCompra + "\nCaixa: " + nome + "\n-------------------------------------------\nForma de pagamento: DEBITO\nValor Total R$:" + formatarValor(valor) + "\nDesconto R$: " + BigDecimal.ZERO + "\nTaxa Cartão R$:" + BigDecimal.ZERO + "\nValor final R$:" + formatarValor(valor) + "\nTroco R$:" + BigDecimal.ZERO;
        return formaPagamento;
    }

    public String pagamentoDinheiro(BigDecimal valor, BigDecimal valorRecebido, String nome) {
        JOptionPane.showMessageDialog(null, "Transação Aprovada!");
        formaPagamento = "-------------------------------------------" + "\n" + DataFimCompra + "\nCaixa: " + nome + "\n-------------------------------------------\nForma de pagamento: DINHEIRO\nValor recebido R$:" + formatarValor(valorRecebido) + "\nDesconto R$: " + BigDecimal.ZERO + "\nTaxa Cartão R$:" + BigDecimal.ZERO + "\nValor final R$:" + formatarValor(valor) + "\nTroco R$:" + formatarValor(CalcularTroco(valor, valorRecebido));
        return formaPagamento;
    }

    private String formatarValor(BigDecimal valor) {
        DecimalFormat formato = new DecimalFormat("#,##0.00");
        return formato.format(valor);
    }

    public BigDecimal CalcularTaxPix(BigDecimal valor) {
        return valor.multiply(TaxPayments.taxPix);
    }

    public BigDecimal CalcularTaxCredito(BigDecimal valor) {
        return valor.multiply(TaxPayments.taxCredito);
    }

    public BigDecimal CalcularTroco(BigDecimal valorTotal, BigDecimal valorRecebido) {
        if (valorRecebido.compareTo(valorTotal) >= 0) {
            return valorRecebido.subtract(valorTotal);
        } else {
            return BigDecimal.ZERO;
        }
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
}
