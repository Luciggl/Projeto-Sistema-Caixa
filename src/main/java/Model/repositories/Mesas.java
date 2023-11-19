package Model.repositories;

import Model.entities.Products;
import Model.enums.StatusMesa;

import java.util.ArrayList;

public interface Mesas {
    Double taxaService = 0.1;
    int getNumMesa();
    StatusMesa getStatusMesa();
    void setStatusMesa(StatusMesa statusMesa);
    ArrayList<Products> getProdutosMesa();
    void addProdutoMesa(Products produto, int quant, int NumMes);
    void removerProdutoMesa(Products produto, int quant, int NumMes);
    double calcularTotalProdutosConsumidos(int NumMesa);
    public Products getProductById(int id);
}
