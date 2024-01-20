package Model.repositories;

import Model.entities.Products;
import Model.exceptions.ProdutoException;
import Model.services.EstoqueServices;

import java.math.BigDecimal;
import java.util.Map;

public interface CaixaRepository {
    EstoqueServices getEstoque();
    Map<Products, Integer> getProdutosCompra();
    void adicionarProduto(Products produto, int quantidade) throws ProdutoException;
    void removerProduto(Products produto, int quantidade) throws ProdutoException;
    boolean produtoExisteNoEstoque(Products produto, int quantidade) throws ProdutoException;
    BigDecimal calcularValorTotalCompra();
    void finalizarCompra();
    String toString();
}
