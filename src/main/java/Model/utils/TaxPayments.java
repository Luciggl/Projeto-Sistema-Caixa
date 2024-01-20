package Model.utils;

import java.math.BigDecimal;

/**
 * A interface TaxPayments define as variáveis relacionadas aos sistemas de pagamento e suas respectivas taxas.
 * Este pacote fornece informações sobre as taxas de pagamento utilizadas no sistema.
 */
public interface TaxPayments {

    /**
     * Taxa de desconto de 10% aplicada a transações realizadas através do serviço PIX.
     */
    BigDecimal taxPix = BigDecimal.valueOf(0.1);

    /**
     * Taxa de acréscimo de 6% aplicada a transações realizadas com cartão de crédito.
     */
    BigDecimal taxCredito = BigDecimal.valueOf(0.06);

}
