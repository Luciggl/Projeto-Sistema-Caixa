package Model.services;

import Model.entities.Products;
import Model.exceptions.ProdutoException;
import Model.repositories.CaixaRepository;
import Model.utils.Path;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Caixa implements CaixaRepository {
    private final EstoqueRepository estoque;
    private final Map<Products, Integer> produtosCompra;
    private BigDecimal valorTotalCompra = BigDecimal.ZERO;

    private static final String PRODUTO_ADICIONADO_MSG = "Produto adicionado à lista de compra: %s | Quantidade: %d";

    public Caixa(EstoqueRepository estoque) {
        this.estoque = estoque;
        this.produtosCompra = new HashMap<>();
    }

    public EstoqueRepository getEstoque() {
        return estoque;
    }

    public Map<Products, Integer> getProdutosCompra() {
        return produtosCompra;
    }

    public void adicionarProduto(Products produto, int quantidade) throws ProdutoException {
        if (produtoExisteNoEstoque(produto, quantidade)) {
            produtosCompra.put(produto, quantidade);
            JOptionPane.showMessageDialog(null, String.format(PRODUTO_ADICIONADO_MSG, produto.getName(), quantidade));
        } else {
            throw new ProdutoException("O Produto Não foi encontrado");
        }
    }

    public void removerProduto(Products produto, int quantidade) throws ProdutoException{
        if (produtosCompra.containsKey(produto)) {
            int quantidadeAtual = produtosCompra.get(produto);

            if (quantidade <= quantidadeAtual) {
                produtosCompra.put(produto, quantidadeAtual - quantidade);
                JOptionPane.showMessageDialog(null, "Produto removido da lista de compra: " +
                        produto.getName() + " | Quantidade removida: " + quantidade);
            } else {
                throw new ProdutoException("Erro ao remover produto: Quantidade superior à quantidade na lista de compra");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Erro ao remover produto: Produto não encontrado na lista de compra");
        }
    }

    public boolean produtoExisteNoEstoque(Products produto, int quantidade) throws ProdutoException {
        if (estoque.produtoExiste(produto) && estoque.getProductById(produto.getId()) != null) {
            if (estoque.getProductById(produto.getId()).getQuanti() >= quantidade) {
                return true;
            } else {
                throw new ProdutoException("Error: Quantidade indisponível em estoque para o produto " + produto.getName());
            }
        } else {
            throw new ProdutoException("Error: Produto não encontrado no estoque");
        }
    }

    public BigDecimal calcularValorTotalCompra() {

        for (Map.Entry<Products, Integer> entry : produtosCompra.entrySet()) {
            Products produto = entry.getKey();
            int quantidade = entry.getValue();
            BigDecimal valorProduto = produto.getValue().multiply(BigDecimal.valueOf(quantidade));
            valorTotalCompra.add(valorProduto);
        }

        return valorTotalCompra;
    }

    public void finalizarCompra() {
        for (Map.Entry<Products, Integer> entry : produtosCompra.entrySet()) {
            Products produto = entry.getKey();
            int quantidade = entry.getValue();
            try {
                estoque.removeQuant(produto, quantidade);

                estoque.salvarEstoque(Path.pathEstoque);
                estoque.salvarTransacao(Path.pathTransacao);

            } catch (ProdutoException e) {
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
            BigDecimal valorProduto = produto.getValue().add(BigDecimal.valueOf(quantidade));
            result.append(produto.getName())
                    .append(" | Quantidade: ")
                    .append(quantidade)
                    .append(" | Valor Unitário: R$ ")
                    .append(produto.getValue())
                    .append(" | Valor Total: R$ ")
                    .append(valorProduto)
                    .append("\n");
        }

        result.append("\nTotal: R$ ").append(calcularValorTotalCompra());

        return result.toString();
    }
}
