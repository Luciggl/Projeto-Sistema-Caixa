import Model.services.CaixaService;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
        CaixaService caixaService = new CaixaService();

        // Simulando transações
        BigDecimal valorPix = BigDecimal.valueOf(50.0);
        BigDecimal valorCredito = BigDecimal.valueOf(100.0);
        BigDecimal valorDebito = BigDecimal.valueOf(30.0);
        BigDecimal valorDinheiro = BigDecimal.valueOf(70.0);

        // Adicionando os valores ao caixa
        caixaService.adicionarValorDiarioPix(valorPix);
        caixaService.adicionarValorDiarioCredito(valorCredito);
        caixaService.adicionarValorDiarioDebito(valorDebito);
        caixaService.adicionarValorDiarioDinheiro(valorDinheiro);

        // Exibindo o balanço diário
        caixaService.BalancoCaixaDiario();
    }
}
