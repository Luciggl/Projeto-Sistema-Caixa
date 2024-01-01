package Model.repositories;

import Model.entities.Products;
import Model.enums.Category;
import Model.exceptions.ProdutoException;

import java.util.ArrayList;

public interface Estoque {
    Products getProductById(double id);

    boolean produtoExiste(Products product);

    void addEstoque(Products product) throws ProdutoException;

    void removeEstoque(Products product, int id) throws ProdutoException;

    void AddQuant(Products product, int quant) throws ProdutoException;

    void removeQuant(Products product, int quant) throws ProdutoException;

    ArrayList<Products> getProductsByCategory(Category category);

    String toString();

    ArrayList<Products> getProductByManufacturer(String manufacturer);
}
