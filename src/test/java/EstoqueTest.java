import Model.entities.Products;
import Model.enums.Category;
import Model.exceptions.ProdutoJaExisteException;
import Model.exceptions.ProdutoNaoExisteException;
import Model.services.Estoque;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EstoqueTest {

    private Estoque estoque;


    @BeforeEach
    void setUp() {
        estoque = new Estoque();
    }


    @Test
    void testAdicionarProduto() {
        Products product = new Products(1, "Product", "Manufacturer", Category.ALIMENTO, 10.0, 5);

        try {
            estoque.addEstoque(product);
            assertTrue(estoque.produtoExiste(product));
        } catch (ProdutoJaExisteException e) {
            fail("Exception not expected: " + e.getMessage());
        }
    }

    @Test
    void testAdicionarProdutoDuplicado() {
        Products product = new Products(1, "Product", "Manufacturer", Category.ALIMENTO, 10.0, 5);

        try {
            estoque.addEstoque(product);
            assertThrows(ProdutoJaExisteException.class, () -> estoque.addEstoque(product));
        } catch (ProdutoJaExisteException e) {
            assertEquals("Error: Produto já existe no estoque", e.getMessage());
        }
    }

    @Test
    void testRemoverProduto() {
        Products product = new Products(1, "Product", "Manufacturer", Category.ALIMENTO, 10.0, 5);

        try {
            estoque.addEstoque(product);
            estoque.removeEstoque(product, 1);
            assertFalse(estoque.produtoExiste(product));
        } catch (ProdutoNaoExisteException | ProdutoJaExisteException e) {
            fail("Exception not expected: " + e.getMessage());
        }
    }

    @Test
    void testRemoverProdutoNaoExistente() {
        Products product = new Products(1, "Product", "Manufacturer", Category.ALIMENTO, 10.0, 5);

        assertThrows(ProdutoNaoExisteException.class, () -> estoque.removeEstoque(product, 1));
    }

    @Test
    void testAddQuant() {
        Products product = new Products(1, "Product", "Manufacturer", Category.ALIMENTO, 10.0, 5);

        try {
            estoque.addEstoque(product);
            estoque.AddQuant(product, 3);
            assertEquals(8, product.getQuanti());
        } catch (ProdutoNaoExisteException | ProdutoJaExisteException e) {
            fail("Exception not expected: " + e.getMessage());
        }
    }

    @Test
    void testAddQuantProdutoNaoExistente() {
        Products product = new Products(1, "Product", "Manufacturer", Category.ALIMENTO, 10.0, 5);

        assertThrows(ProdutoNaoExisteException.class, () -> estoque.AddQuant(product, 3));
    }

    @Test
    void testRemoveQuant() {
        Products product = new Products(1, "Product", "Manufacturer", Category.ALIMENTO, 10.0, 5);

        try {
            estoque.addEstoque(product);
            estoque.removeQuant(product, 2);
            assertEquals(3, product.getQuanti());
        } catch (ProdutoNaoExisteException | ProdutoJaExisteException e) {
            fail("Exception not expected: " + e.getMessage());
        }
    }

    @Test
    void testRemoveQuantProdutoNaoExistente() {
        Products product = new Products(1, "Product", "Manufacturer", Category.ALIMENTO, 10.0, 5);

        assertThrows(ProdutoNaoExisteException.class, () -> estoque.removeQuant(product, 2));
    }

    @Test
    void testGetProductById() {
        Products product = new Products(1, "Product", "Manufacturer", Category.ALIMENTO, 10.0, 5);

        try {
            estoque.addEstoque(product);
            Products retrievedProduct = estoque.getProductById(1);
            assertNotNull(retrievedProduct);
            assertEquals(product, retrievedProduct);
        } catch ( ProdutoJaExisteException e) {
            fail("Exception not expected: " + e.getMessage());
        }
    }

    @Test
    void testGetProductByIdProdutoNaoExistente() {
        assertNull(estoque.getProductById(1));
    }

    @Test
    void testGetProductsByCategory() {
        Products product1 = new Products(1, "Product1", "Manufacturer", Category.ALIMENTO, 10.0, 5);
        Products product2 = new Products(2, "Product2", "Manufacturer", Category.ALIMENTO, 5.0, 10);
        Products product3 = new Products(3, "Product3", "Manufacturer", Category.LIMPEZA, 8.0, 7);

        try {
            estoque.addEstoque(product1);
            estoque.addEstoque(product2);
            estoque.addEstoque(product3);

            ArrayList<Products> productsByCategory = estoque.getProductsByCategory(Category.ALIMENTO);
            assertEquals(2, productsByCategory.size());
            assertTrue(productsByCategory.contains(product1));
            assertTrue(productsByCategory.contains(product2));
        } catch (ProdutoJaExisteException e) {
            fail("Exception not expected: " + e.getMessage());
        }
    }

    @Test
    void testGetProductsByCategoryNenhumProduto() {
        ArrayList<Products> productsByCategory = estoque.getProductsByCategory(Category.ALIMENTO);
        assertTrue(productsByCategory.isEmpty());
    }

    @Test
    void testGetProductsByManufacturer() {
        Products product1 = new Products(1, "Product1", "Manufacturer1", Category.ALIMENTO, 10.0, 5);
        Products product2 = new Products(2, "Product2", "Manufacturer2", Category.ALIMENTO, 5.0, 10);
        Products product3 = new Products(3, "Product3", "Manufacturer1", Category.LIMPEZA, 8.0, 7);

        try {
            estoque.addEstoque(product1);
            estoque.addEstoque(product2);
            estoque.addEstoque(product3);

            ArrayList<Products> productsByManufacturer = estoque.getProductByManufacturer("Manufacturer1");
            assertEquals(2, productsByManufacturer.size());
            assertTrue(productsByManufacturer.contains(product1));
            assertTrue(productsByManufacturer.contains(product3));
        } catch (ProdutoJaExisteException e) {
            fail("Exception not expected: " + e.getMessage());
        }
    }

    @Test
    void testGetProductsByManufacturerNenhumProduto() {
        ArrayList<Products> productsByManufacturer = estoque.getProductByManufacturer("Manufacturer1");
        assertTrue(productsByManufacturer.isEmpty());
    }


    @Test
    void testCalcularValorTotalEstoque() {
        Products product1 = new Products(1, "Product1", "Manufacturer1", Category.ALIMENTO, 10.0, 5);
        Products product2 = new Products(2, "Product2", "Manufacturer2", Category.ALIMENTO, 5.0, 10);

        try {
            estoque.addEstoque(product1);
            estoque.addEstoque(product2);

            double expectedTotal = (10.0 * 5) + (5.0 * 10);
            assertEquals(expectedTotal, estoque.calcularValorTotalEstoque());
        } catch (ProdutoJaExisteException e) {
            fail("Exception not expected: " + e.getMessage());
        }
    }
    @Test
    public void testMudarValorProduto() throws ProdutoNaoExisteException {
        Products product = new Products(1, "Produto1", "Fabricante", Category.LIMPEZA, 100.0, 10);
        try {
            estoque.addEstoque(product);
        } catch (ProdutoJaExisteException e) {
            fail("Não deveria lançar exceção aqui");
        }

        estoque.MudarValorProduto(1, 150.0);

        assertEquals(150.0, product.getValue(), 0.001);
    }

    @Test
    public void testMudarValorProdutoProdutoNaoExistente() {
        ProdutoNaoExisteException exception = assertThrows(ProdutoNaoExisteException.class, () -> {
            estoque.MudarValorProduto(1, 150.0);
        });

        assertEquals("Error: Produto não existe no estoque", exception.getMessage());
    }
}
