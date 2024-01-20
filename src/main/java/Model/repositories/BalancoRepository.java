package Model.repositories;

import Model.entities.BalancoCaixa;
import Model.entities.Products;

import java.util.ArrayList;

public interface BalancoRepository {

    ArrayList<BalancoCaixa> getBalanco();

    void registrarEntrada(Products products, int Quant);

    void registrarSaida(Products products, int Quant);

    void exibirTransacoes();

    void salvarTransacoes(String filePath);

    void recuperarTransacoes(String filePath);

    void PesquisarTipo(String tipo);

    String toString();

}
