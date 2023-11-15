package Model.repositories;

import Model.entities.Products;
import Model.enums.StatusMesa;

import java.util.ArrayList;

public interface Mesas {
    Double taxaService = 10.0;
    int getNumMesa();
    StatusMesa getStatusMesa();
    void setStatusMesa(StatusMesa statusMesa);
    ArrayList<Products> getProdutosMesa();
    void addProdutoMesa(Products produto, int quant);
    void removerProdutoMesa(Products produto, int quant);
    double calcularTotalProdutosConsumidos();
}
