import Model.services.PaymentsServices;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PaymentsServicesTest {

    private PaymentsServices paymentsServices;

    @Before
    public void setUp() {
        paymentsServices = new PaymentsServices();
    }

    @Test
    public void testPagamentoPix() {
        String result = paymentsServices.pagamentoPix(100.0);
        assertTrue(result.contains("PIX"));
        assertTrue(result.contains("Transação Aprovada!"));
    }

    @Test
    public void testPagamentoCredito() {
        String result = paymentsServices.pagamentoCredito(100.0);
        assertTrue(result.contains("CREDITO"));
        assertTrue(result.contains("Transação Aprovada!"));
    }

    @Test
    public void testPagamentoDebito() {
        String result = paymentsServices.pagamentoDebito(100.0);
        assertTrue(result.contains("DEBITO"));
        assertTrue(result.contains("Transação Aprovada!"));
    }

    @Test
    public void testPagamentoDinheiro() {
        String result = paymentsServices.pagamentoDinheiro(100.0, 120.0);
        assertTrue(result.contains("DINHEIRO"));
    }

    @Test
    public void testCalcularTaxPix() {
        double result = paymentsServices.CalcularTaxPix(100.0);
        assertEquals(10.0, result, 0.001);
    }

    @Test
    public void testCalcularTaxCredito() {
        double result = paymentsServices.CalcularTaxCredito(100.0);
        assertEquals(6.0, result, 0.001);
    }

    @Test
    public void testCalcularTroco() {
        double result = paymentsServices.CalcularTroco(100.0, 120.0);
        assertEquals(20.0, result, 0.001);
    }

}
