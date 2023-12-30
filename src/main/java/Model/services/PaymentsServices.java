package Model.services;

import Model.repositories.TaxPayments;

public class PaymentsServices {

    private Double valorTotalPorMeioDePagamento;
    public PaymentsServices(){

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

    public double pagamentoPix(Double valor){
        valorTotalPorMeioDePagamento = (valor += (valor * TaxPayments.taxPix));
        return valorTotalPorMeioDePagamento;
    }

    public double pagamentoCredito(Double valor){
        valorTotalPorMeioDePagamento = (valor += (valor * TaxPayments.taxCredito));
        return valorTotalPorMeioDePagamento;
    }

    public double CalcularTroco(Double valorTotal, Double valorRecebido){
        double troco = valorRecebido -= valorTotal;
        return troco;
    }



}
