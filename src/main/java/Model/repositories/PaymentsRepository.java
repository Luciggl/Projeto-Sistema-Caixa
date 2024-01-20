package Model.repositories;

import java.math.BigDecimal;

public interface PaymentsRepository {
    String pagamentoPix(BigDecimal valor, String nome);
    String pagamentoCredito(BigDecimal valor, String nome);
    String pagamentoDebito(BigDecimal valor, String nome);
    String pagamentoDinheiro(BigDecimal valor, BigDecimal valorRecebido, String nome);
    BigDecimal CalcularTaxPix(BigDecimal valor);
    BigDecimal CalcularTaxCredito(BigDecimal valor);
    BigDecimal CalcularTroco(BigDecimal valorTotal, BigDecimal valorRecebido);
    String buildPagamento(String nomeFuncionario, int forma, BigDecimal valorRecebido, BigDecimal valor);
}
