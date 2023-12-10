package Model.services;

public class PaymentsServices {
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
