package Model.services;

import Model.entities.Products;
import Model.enums.Categoria;
import Model.enums.StatusMesa;
import Model.exceptions.ProdutoJaExisteException;
import Model.exceptions.ProdutoNaoExisteException;

import java.util.ArrayList;

public class Mesas extends Estoque implements Model.repositories.Mesas {
    private int NumMesa;
    private StatusMesa statusMesa;
    private ArrayList<Products> ProdutosMesa;
    private Estoque estoque;

    public Mesas(){}

    public Mesas(int NumMesa, StatusMesa statusMesa, Estoque estoque) {
        this.NumMesa = NumMesa;
        this.statusMesa = statusMesa;
        this.ProdutosMesa = new ArrayList<>();
        this.estoque = estoque;
    }

    public int getNumMesa() {
        return NumMesa;
    }

    public StatusMesa getStatusMesa() {
        return statusMesa;
    }

    public void setStatusMesa(StatusMesa statusMesa) {
        this.statusMesa = statusMesa;
    }

    public ArrayList<Products> getProdutosMesa() {
        return ProdutosMesa;
    }

    public void addProdutoMesa(Products produto, int quant, double NumMes) {
        ProdutosMesa.add(produto);
        try {
            estoque.removeQuant(produto, quant);
        } catch (ProdutoNaoExisteException e) {
            System.out.println("Produto não existe no estoque: " + e.getMessage());
        }
    }

    public void removerProdutoMesa(Products produto, int quant, double NumMes) {
        ProdutosMesa.remove(produto);
        try {
            estoque.AddQuant(produto, quant);
        } catch (ProdutoNaoExisteException e) {
            System.out.println("Produto não existe no estoque: " + e.getMessage());
        }
    }

    public double calcularTotalProdutosConsumidos(int NumMesa) {
        double valorTotal = 0;

        for (Products product : ProdutosMesa) {
            int quantidadeNaMesa = product.getQuanti();
            if (product.getCategoria() == Categoria.Drinks) {
                valorTotal += (product.getValue() * quantidadeNaMesa) + ((product.getValue() * quantidadeNaMesa) * taxaService);
            } else {
                valorTotal += product.getValue() * quantidadeNaMesa;
            }
        }
        return valorTotal;
    }
}
