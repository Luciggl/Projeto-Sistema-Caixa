import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import Model.entities.Products;
import Model.enums.Categoria;
import Model.exceptions.ProdutoJaExisteException;
import Model.exceptions.ProdutoNaoExisteException;
import Model.services.Estoque;
import org.junit.Before;
import org.junit.Test;

public class EstoqueTest {

    private Estoque estoque;

    @Before
    public void setUp() {
        estoque = new Estoque();
    }

    @Test
    public void testAddEstoque() throws ProdutoJaExisteException {
        Products produto = new Products("ProdutoTeste", 1, Categoria.Juice, 10.0, 20);
        estoque.addEstoque(produto);

        assertNotNull(estoque.getProductById(1));
        assertEquals(produto, estoque.getProductById(1));
    }

    @Test(expected = ProdutoJaExisteException.class)
    public void testAddEstoqueProdutoExistente() throws ProdutoJaExisteException {
        Products produto = new Products("ProdutoExistente", 1, Categoria.Juice, 10.0, 20);
        estoque.addEstoque(produto);

        // Tentar adicionar o mesmo produto novamente deve lançar uma exceção
        estoque.addEstoque(produto);
    }

    @Test
    public void testRemoveEstoque() throws ProdutoNaoExisteException {
        Products produto = new Products("ProdutoRemover", 1, Categoria.Juice, 10.0, 20);
        estoque.addEstoque(produto);

        assertNotNull(estoque.getProductById(1));

        estoque.removeEstoque(produto, 1);

        assertNull(estoque.getProductById(1));
    }

    @Test(expected = ProdutoNaoExisteException.class)
    public void testRemoveEstoqueProdutoNaoExistente() throws ProdutoNaoExisteException {
        Products produto = new Products("ProdutoNaoExistente", 1, Categoria.Juice, 10.0, 20);

        // Tentar remover um produto que não está no estoque deve lançar uma exceção
        estoque.removeEstoque(produto, 1);
    }

    @Test
    public void testAddQuant() throws ProdutoNaoExisteException {
        Products produto = new Products("ProdutoQuantidade", 1, Categoria.Juice, 10.0, 20);
        estoque.addEstoque(produto);

        assertEquals(20, estoque.getProductById(1).getQuanti());

        estoque.AddQuant(produto, 5);

        assertEquals(25, estoque.getProductById(1).getQuanti());
    }

    @Test(expected = ProdutoNaoExisteException.class)
    public void testAddQuantProdutoNaoExistente() throws ProdutoNaoExisteException {
        Products produto = new Products("ProdutoNaoExistente", 1, Categoria.Juice, 10.0, 20);

        // Tentar adicionar quantidade a um produto que não está no estoque deve lançar uma exceção
        estoque.AddQuant(produto, 5);
    }

    @Test
    public void testRemoveQuant() throws ProdutoNaoExisteException {
        Products produto = new Products("ProdutoQuantidade", 1, Categoria.Juice, 10.0, 20);
        estoque.addEstoque(produto);

        assertEquals(20, estoque.getProductById(1).getQuanti());

        estoque.removeQuant(produto, 5);

        assertEquals(15, estoque.getProductById(1).getQuanti());
    }

    @Test(expected = ProdutoNaoExisteException.class)
    public void testRemoveQuantProdutoNaoExistente() throws ProdutoNaoExisteException {
        Products produto = new Products("ProdutoNaoExistente", 1, Categoria.Juice, 10.0, 20);

        // Tentar remover quantidade de um produto que não está no estoque deve lançar uma exceção
        estoque.removeQuant(produto, 5);
    }
}
