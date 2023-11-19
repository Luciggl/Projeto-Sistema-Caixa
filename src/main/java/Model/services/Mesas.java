package Model.services;

import Model.entities.Products;
import Model.enums.Categoria;
import Model.enums.StatusMesa;
import Model.exceptions.ProdutoJaExisteException;
import Model.exceptions.ProdutoNaoExisteException;

import java.util.ArrayList;

public class Mesas implements Model.repositories.Mesas {
    private int numMesa;
    private StatusMesa statusMesa;
    private ArrayList<Products> produtosMesa;
    private Estoque estoque;

    public Mesas() {
        this.produtosMesa = new ArrayList<>();
    }

    public Mesas(int numMesa, StatusMesa statusMesa, Estoque estoque) {
        this.numMesa = numMesa;
        this.statusMesa = statusMesa;
        this.produtosMesa = new ArrayList<>();
        this.estoque = estoque;
    }

    public int getNumMesa() {
        return numMesa;
    }

    public StatusMesa getStatusMesa() {
        return statusMesa;
    }

    public void setStatusMesa(StatusMesa statusMesa) {
        this.statusMesa = statusMesa;
    }

    public ArrayList<Products> getProdutosMesa() {
        return produtosMesa;
    }

    @Override
    public void addProdutoMesa(Products produto, int quant, int NumMes) {
        produtosMesa.add(produto);
        try {
            estoque.addEstoque(produto);
            estoque.removeQuant(produto, quant);
        } catch (ProdutoNaoExisteException | ProdutoJaExisteException e) {
            System.out.println("Erro ao adicionar produto à mesa: " + e.getMessage());
        }
    }

    @Override
    public void removerProdutoMesa(Products produto, int quant, int NumMes) {
        if (produtosMesa.contains(produto)) {
            produtosMesa.remove(produto);
            try {
                if (quant > 0) {
                    estoque.AddQuant(produto, quant);
                } else {
                    System.out.println("Erro: A quantidade do produto na mesa é inválida.");
                }
            } catch (ProdutoNaoExisteException e) {
                System.out.println("Produto não existe no estoque: " + e.getMessage());
            }
        } else {
            System.out.println("Produto não existe na mesa.");
        }
    }

    @Override
    public double calcularTotalProdutosConsumidos(int NumMesa) {
        double valorTotal = 0;

        for (Products product : produtosMesa) {
            Products estoqueProduct = estoque.getProductById(product.getId());

            if (estoqueProduct != null) {
                int quantidadeNaMesa = estoqueProduct.getQuanti();
                if (estoqueProduct.getCategoria() == Categoria.Drinks) {
                    valorTotal += (estoqueProduct.getValue() * quantidadeNaMesa) + ((estoqueProduct.getValue() * quantidadeNaMesa) * taxaService);
                } else {
                    valorTotal += estoqueProduct.getValue() * quantidadeNaMesa;
                }
            }
        }
        return valorTotal;
    }

    public Products getProductById(int id) {
        for (Products product : produtosMesa) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }
}
