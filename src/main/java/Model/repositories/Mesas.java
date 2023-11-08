package Model.repositories;

import Model.entities.Products;
import Model.enums.StatusMesa;

import java.util.ArrayList;

public interface Mesas {
    int getNumMesa();
    StatusMesa getStatusMesa();
    void setStatusMesa(StatusMesa statusMesa);
    ArrayList<Products> getProdutosMesa();
    void addProdutoMesa(Products produto, int quant);
    void removerProdutoMesa(Products produto, int quant);
    double calcularTotalProdutosConsumidos();
}
