package Model.services;

import Model.entities.Products;
import Model.exceptions.ProdutoNãoExisteException;

import java.util.ArrayList;

public class Estoque implements Model.repositories.Estoque{
    private ArrayList<Products> productsEstoque;
    private int QuantInstoque;

    public int getQuantInstoque() {
        return QuantInstoque;
    }

    public void setQuantInstoque(int quantInstoque) {
        QuantInstoque = quantInstoque;
    }

    public Products getProductById(int id) {
        for (Products product : productsEstoque) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public Estoque(Products products, int quantInstoque) {
        this.productsEstoque = new ArrayList<>();
        quantInstoque = 0;
    }
    public Estoque(){}

    public void addEstoque(Products product, int qntEstoque){
        if(productsEstoque == null) {
            productsEstoque = new ArrayList<>();
            this.QuantInstoque = qntEstoque;
        }
        if(produtoExiste(product) == false ){
            productsEstoque.add(product);
        }
    }

    @Override
    public void removeEstoque(Products product, int id) throws ProdutoNãoExisteException {
        if(produtoExiste(product) == true ){
            productsEstoque.remove(product);
        }
    }

    public boolean produtoExiste(Products product) {
        return productsEstoque.contains(product);
    }
    @Override
    public void AddQuant(int quant) throws ProdutoNãoExisteException {
        setQuantInstoque(QuantInstoque += quant);;
    }

    @Override
    public void removeQuant(int quant) throws ProdutoNãoExisteException {
        setQuantInstoque(QuantInstoque -= quant);;
    }

    @Override
    public String toString() {
        return productsEstoque + "\n" + "QuantInstoque=" + getQuantInstoque();
    }
}
