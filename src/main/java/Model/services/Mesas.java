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

    public void addProdutoMesa(Products produto, int quant) {
        ProdutosMesa.add(produto);
        try {
            estoque.removeQuant(produto, quant);
        } catch (ProdutoNaoExisteException e) {
            System.out.println("Produto não existe no estoque: " + e.getMessage());
        }
    }

    public void removerProdutoMesa(Products produto, int quant) {
        ProdutosMesa.remove(produto);
        try {
            estoque.AddQuant(produto, quant);
        } catch (ProdutoNaoExisteException e) {
            System.out.println("Produto não existe no estoque: " + e.getMessage());
        }
    }

    public double calcularTotalProdutosConsumidos() {
        double total = 0;
        for (Products produto : ProdutosMesa) {
            if (produto.getCategoria() == Categoria.Juice) {
                total = (total += produto.getValue() * Model.repositories.Mesas.taxaService / 100);
            } else {
                total += produto.getValue();
            }
        }
        return total;
    }

}
