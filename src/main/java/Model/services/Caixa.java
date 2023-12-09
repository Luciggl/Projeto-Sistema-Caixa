package Model.services;

import Model.entities.Products;
import Model.exceptions.ProdutoNaoExisteException;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class Caixa {
    private Estoque estoque;
    private Map<Products, Integer> produtosCompra;
    private double valorTotalCompra;

    private static final String PRODUTO_ADICIONADO_MSG = "Produto adicionado à lista de compra: %s | Quantidade: %d";

    public Caixa(Estoque estoque) {
        this.estoque = estoque;
        this.produtosCompra = new HashMap<>();
        this.valorTotalCompra = 0;
    }

    public Estoque getEstoque() {
        return estoque;
    }

    public Map<Products, Integer> getProdutosCompra() {
        return produtosCompra;
    }

    public void adicionarProduto(Products produto, int quantidade) throws ProdutoNaoExisteException {
        if (produtoExisteNoEstoque(produto, quantidade)) {
            produtosCompra.put(produto, quantidade);
            JOptionPane.showMessageDialog(null, String.format(PRODUTO_ADICIONADO_MSG, produto.getName(), quantidade));
        }
    }

    private boolean produtoExisteNoEstoque(Products produto, int quantidade) throws ProdutoNaoExisteException {
        if (estoque.produtoExiste(produto) && estoque.getProductById(produto.getId()) != null) {
            if (estoque.getProductById(produto.getId()).getQuanti() >= quantidade) {
                return true;
            } else {
                throw new ProdutoNaoExisteException("Error: Quantidade indisponível em estoque para o produto " + produto.getName());
            }
        } else {
            throw new ProdutoNaoExisteException("Error: Produto não encontrado no estoque");
        }
    }

    public double calcularValorTotalCompra() {
        valorTotalCompra = 0;

        for (Map.Entry<Products, Integer> entry : produtosCompra.entrySet()) {
            Products produto = entry.getKey();
            int quantidade = entry.getValue();
            double valorProduto = produto.getValue() * quantidade;
            valorTotalCompra += valorProduto;
        }

        return valorTotalCompra;
    }

    public void finalizarCompra() {
        for (Map.Entry<Products, Integer> entry : produtosCompra.entrySet()) {
            Products produto = entry.getKey();
            int quantidade = entry.getValue();
            try {
                estoque.removeQuant(produto, quantidade);
            } catch (ProdutoNaoExisteException e) {
                JOptionPane.showMessageDialog(null, "Erro ao finalizar a compra: " + e.getMessage());
            }
        }
        produtosCompra.clear();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Produtos Comprados:\n");

        for (Map.Entry<Products, Integer> entry : produtosCompra.entrySet()) {
            Products produto = entry.getKey();
            int quantidade = entry.getValue();
            double valorProduto = produto.getValue() * quantidade;
            result.append(produto.getName())
                    .append(" | Quantidade: ")
                    .append(quantidade)
                    .append(" | Valor Unitário: R$ ")
                    .append(produto.getValue())
                    .append(" | Valor Total: R$ ")
                    .append(valorProduto)
                    .append("\n");
        }

        result.append("\nTotal: R$ ").append(valorTotalCompra);

        return result.toString();
    }
}
