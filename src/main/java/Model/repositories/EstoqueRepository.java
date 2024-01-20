package Model.repositories;

import Model.entities.Products;
import Model.enums.Category;
import Model.exceptions.ProdutoException;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface EstoqueRepository {
    ArrayList<Products> getEstoque();


    public boolean produtoExiste(Products product);

    public boolean Idexiste(double id);

    public void addEstoque(Products product) throws ProdutoException;

    void removeEstoque(Products product, int id) throws ProdutoException;

    void MudarValorProduto(int id, BigDecimal novoValor) throws ProdutoException;

    public void AddQuant(Products product, int quant) throws ProdutoException;

    public void removeQuant(Products product, int quant) throws ProdutoException;

    Products getProductById(int id);

    ArrayList<Products> getProductsByCategory(Category category);

    String toString();

    ArrayList<Products> getProductByManufacturer(String manufacturer);

    void salvarEstoque(String filePath);

    void carregarEstoque(String filePath);

    void salvarTransacao(String path);

    void carregarTransacao(String path);

    void transacoes();

    void PesquisarTransacaoTipo(String tipo);
}

