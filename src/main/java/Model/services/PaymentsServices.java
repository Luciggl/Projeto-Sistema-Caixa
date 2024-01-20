package Model.services;

import Model.repositories.PaymentsRepository;
import Model.utils.TaxPayments;

import javax.swing.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentsServices implements PaymentsRepository {
    String formaPagamento;

    public String pagamentoPix(BigDecimal valor, String nome) {
        return buildPayments(nome, 1, valor.subtract(CalcularTaxPix(valor)), valor);
    }

    public String pagamentoCredito(BigDecimal valor, String nome) {
        return buildPayments(nome, 2, valor.add(CalcularTaxCredito(valor)), valor);
    }

    public String pagamentoDebito(BigDecimal valor, String nome) {
        return buildPayments(nome, 3, valor, valor);
    }

    public String pagamentoDinheiro(BigDecimal valor, BigDecimal valorRecebido, String nome) {
        return buildPayments(nome, 4, valorRecebido, valor);
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

    // O ValidarCartão ira ver se o cartão é valido pra poder liberar a compra;
    public static class ValidarCartao {
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


    /*
    O buildPagamento vai receber o String com a data da compra, o nome do caixa, a forma de pagamento e retorna A forma de pagamento com os calculos necessarios
    forma de pagamento em int (1 - Pix | 2 - credito | 3 - debito | 4 - dinheiro);
    */
    public String buildPayments(String nomeFuncionario, int forma, BigDecimal valorRecebido, BigDecimal valor) {

        JOptionPane.showMessageDialog(null, "Transação Aprovada!");

        SimpleDateFormat formato = new SimpleDateFormat("EEE dd/MM/yyyy HH:mm:ss");
        Date dateFinal = new Date();
        String DataFimCompra = formato.format(dateFinal);

        BigDecimal valorFinal, desconto, taxaCartao;
        valorFinal = BigDecimal.ZERO;
        desconto = BigDecimal.ZERO;
        taxaCartao = BigDecimal.ZERO;

        String formaPag = switch (forma) {
            case 1 -> {
                desconto = CalcularTaxPix(valor);
                yield "Pix";
            }
            case 2 -> {
                taxaCartao = CalcularTaxCredito(valor);
                yield "Credito";
            }
            case 3 -> "Debito";
            case 4 -> "Dinheiro";
            default -> "";
        };

        formaPagamento = "-------------------------------------------" + "\n" +
                DataFimCompra + "\nCaixa: " + nomeFuncionario + "\n-------------------------------------------\n" +
                "Forma de pagamento:" + formaPag + "\n" +
                "Valor recebido R$:" + formatarValor(valorRecebido) + "\n" +
                "Desconto R$: " + formatarValor(desconto) + "\nTaxa Cartão R$:" + formatarValor(taxaCartao) +
                "\nValor final R$:"
                + formatarValor(valorFinal.add(valor.add(taxaCartao).subtract(desconto))) + "\nTroco R$:" + formatarValor(CalcularTroco(valor, valorRecebido));

        return formaPagamento;
    }
}
