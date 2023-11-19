package Model.services;

import Model.entities.Products;
import Model.enums.Categoria;
import Model.exceptions.ProdutoNaoExisteException;

import javax.swing.*;
import java.util.ArrayList;

import static Model.repositories.Mesas.taxaService;

public class EstoqueMesa extends Estoque {
    private ArrayList<Products> produtosMesa;

    public EstoqueMesa() {
        this.produtosMesa = new ArrayList<>();
    }

    public void addProdutoMesa(Products produto, int quant) {
        produtosMesa.add(produto);
        try {
            super.removeQuant(produto, quant);
        } catch (ProdutoNaoExisteException e) {
            System.out.println("Produto não existe no estoque: " + e.getMessage());
        }
    }

    public double calcularTotalProdutosConsumidos() {
        double valorTotal = 0;

        for (Products product : produtosMesa) {
            int quantidadeNaMesa = product.getQuanti();
            if (product.getCategoria() == Categoria.Drinks) {
                valorTotal += (product.getValue() * quantidadeNaMesa) + ((product.getValue() * quantidadeNaMesa) * taxaService);
            } else {
                valorTotal += product.getValue() * quantidadeNaMesa;
            }
        }
        return valorTotal;
    }

    public void removeProdutoMesa(Products produto, int quant) throws ProdutoNaoExisteException {
        if (produtosMesa.contains(produto)) {
            produtosMesa.remove(produto);
            super.AddQuant(produto, quant);
        } else {
            JOptionPane.showMessageDialog(null,"Produto não encontrado na mesa.");
        }
    }

    public ArrayList<Products> getProdutosMesa() {
        return produtosMesa;
    }
}
