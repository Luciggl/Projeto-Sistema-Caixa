package Model.repositories;

import Model.entities.Products;
import Model.exceptions.ProdutoJaExisteException;
import Model.exceptions.ProdutoNãoExisteException;

public interface Estoque {
   void addEstoque(Products product, int qntEstoque) throws ProdutoJaExisteException;
   void removeEstoque(Products products, int id) throws ProdutoNãoExisteException;
   void AddQuant(int quant) throws ProdutoNãoExisteException;
   void removeQuant(int quant) throws ProdutoNãoExisteException;
}
;