package Model.repositories;

import Model.entities.Products;
import Model.enums.Category;
import Model.exceptions.ProdutoJaExisteException;
import Model.exceptions.ProdutoNaoExisteException;

import java.util.ArrayList;

public interface Estoque {
    Products getProductById(double id);

    boolean produtoExiste(Products product);

    void addEstoque(Products product) throws ProdutoJaExisteException;

    void removeEstoque(Products product, int id) throws ProdutoNaoExisteException;

    void AddQuant(Products product, int quant) throws ProdutoNaoExisteException;

    void removeQuant(Products product, int quant) throws ProdutoNaoExisteException;

    ArrayList<Products> getProductsByCategory(Category category);

    String toString();

    ArrayList<Products> getProductByManufacturer(String manufacturer);
}
