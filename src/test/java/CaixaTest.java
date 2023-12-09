import Model.entities.Products;
import Model.enums.Category;
import Model.exceptions.ProdutoNaoExisteException;
import Model.services.Caixa;
import Model.services.Estoque;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CaixaTest {

    @Test
    public void testAdicionarProduto() throws ProdutoNaoExisteException {
        Estoque estoque = new Estoque();
        Caixa caixa = new Caixa(estoque);

        estoque.addEstoque(new Products(1, "Produto1", "Fabricante1", Category.ALIMENTO, 10.0, 20));
        estoque.addEstoque(new Products(2, "Produto2", "Fabricante2", Category.HIGIENE, 5.0, 15));

        caixa.adicionarProduto(estoque.getProductById(1), 5);
        assertEquals(1, caixa.getProdutosCompra().size());
        assertEquals(50.0, caixa.calcularValorTotalCompra());

        assertThrows(ProdutoNaoExisteException.class, () -> caixa.adicionarProduto(estoque.getProductById(2), 20));

        assertThrows(ProdutoNaoExisteException.class, () -> caixa.adicionarProduto(new Products(3, "Produto3", "Fabricante3", Category.LIMPEZA, 15.0, 10), 2));
    }

    @Test
    public void testFinalizarCompra() throws ProdutoNaoExisteException {
        Estoque estoque = new Estoque();
        Caixa caixa = new Caixa(estoque);

        estoque.addEstoque(new Products(1, "Produto1", "Fabricante1", Category.ALIMENTO, 10.0, 20));
        estoque.addEstoque(new Products(2, "Produto2", "Fabricante2", Category.HIGIENE, 5.0, 15));

        caixa.adicionarProduto(estoque.getProductById(1), 5);
        caixa.adicionarProduto(estoque.getProductById(2), 3);

        caixa.finalizarCompra();
        assertEquals(0, caixa.getProdutosCompra().size());
        assertEquals(0.0, caixa.calcularValorTotalCompra());
        assertEquals(15, estoque.getProductById(1).getQuanti());
        assertEquals(12, estoque.getProductById(2).getQuanti());
    }
}
